import 'package:flutter/material.dart';
import 'package:star_wars_wiki/app/modules/detail/detail_page.dart';
import 'package:star_wars_wiki/shared/models/character_model.dart';

import 'home_bloc.dart';
import 'home_module.dart';

class HomePage extends StatefulWidget {
  final String title;
  const HomePage({Key key, this.title = "Star Wars Wiki"}) : super(key: key);

  @override
  _HomePageState createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  var bloc = HomeModule.to.getBloc<HomeBloc>();
  List<CharacterModel> list = List<CharacterModel>();
  List<CharacterModel> showList = List<CharacterModel>();
  String show = 'all';
  String searchText = '';

  @override
  void initState() {
    for (int i = 1; i < 10; i++) {
      bloc.fetchCharacters(page: i).then((onValue) {
        setState(() {
          list = bloc.list;
          showList = bloc.list;
        });
      });
    }
    bloc.retryFavorites();

    super.initState();
  }

  void _refreshList(String s) {
    setState(() {
      show = s;
    });
  }

  @override
  Widget build(BuildContext context) {
    //Filtro de personagens a exibir
    if (show == 'all') {
      //Mostrar todos os personagens
      list = bloc.list;
      _filterList(searchText); //Filtro de busca
    } else if (show == 'favs') {
      //Mostrar os personagens favoritos
      list = list.where((item) {
        return item.fav;
      }).toList();
      _filterList(searchText); //Filtro de busca
    }

    return Scaffold(
      appBar: AppBar(
        title: Text(
          widget.title,
          style: TextStyle(
            fontFamily: 'Star Jedi',
            fontSize: 25.0,
          ),
        ),
        elevation: 0.0,
        centerTitle: true,
        actions: <Widget>[
          PopupMenuButton<String>(
              icon: Icon(Icons.filter_list),
              onSelected: _refreshList,
              itemBuilder: (context) {
                return [
                  PopupMenuItem(
                    value: 'all',
                    child: Text('Show all'),
                  ),
                  PopupMenuItem(
                    value: 'favs',
                    child: Text('Show favorites'),
                  ),
                ];
              })
        ],
      ),
      body: Container(
        // color: Colors.orange,
        child: Column(
          children: <Widget>[
            _searchBar(),
            Container(
              color: Colors.amber.withOpacity(0.3),
              height: MediaQuery.of(context).size.height -
                  140 -
                  MediaQuery.of(context).viewInsets.bottom,
              child: SingleChildScrollView(
                child: StreamBuilder<List<CharacterModel>>(
                    stream: bloc.responseOut,
                    builder: (context, snapshot) {
                      if (snapshot.hasError) {
                        return Center(
                          child: RaisedButton(
                            child: Text("Reload"),
                            onPressed: () {
                              initState();
                            },
                          ),
                        );
                      }

                      if (snapshot.hasData) {
                        return Column(
                          children: (showList
                                  .map((item) => Container(
                                        child: Builder(
                                            builder: (context) =>
                                                _characterCard(context, item)),
                                      ))
                                  .toList() +
                              [
                                bloc.list.length < 87
                                    ? Center(
                                        child: CircularProgressIndicator(
                                          backgroundColor: Colors.black,
                                        ),
                                      )
                                    : Container()
                              ]),
                        );
                      } else {
                        return Center(child: CircularProgressIndicator());
                      }
                    }),
              ),
            ),
          ],
        ),
      ),
    );
  }

  _filterList(String text) {
    showList = list.where((item) {
      var itemTitle = item.name.toLowerCase();
      return itemTitle.contains(text);
    }).toList();
  }

  Widget _characterCard(BuildContext context, CharacterModel char) {
    return Card(
      shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(5.0)),
      elevation: 3.0,
      color: Colors.white70,
      child: ListTile(
        title: Text(
          char.name,
          style: TextStyle(
            fontFamily: 'Times',
            fontSize: 18.0,
          ),
        ),
        subtitle: Text(
          'Mass:${char.mass}, Height: ${char.height}, Gender: ${char.gender}',
          style: TextStyle(
            color: Colors.black,
            fontFamily: 'Times',
            fontSize: 12.0,
          ),
        ),
        trailing: IconButton(
          tooltip: 'Favorite',
          icon: Icon(
            char.fav ? Icons.star : Icons.star_border,
            color: Colors.orange.withGreen(140),
            size: 35.0,
          ),
          //Ao clicar na estrela é executada a ação de favoritar no Bloc
          onPressed: () {
            bloc.favoriteCharacter(char).then((result) {
              setState(() {
                Scaffold.of(context).showSnackBar(
                  SnackBar(
                    duration: Duration(milliseconds: 3000),
                    backgroundColor: Colors.black38,
                    content: Text(
                      result,
                      textAlign: TextAlign.center,
                    ),
                    elevation: 10.0,
                    behavior: SnackBarBehavior.floating,
                    shape: StadiumBorder(),
                  ),
                );
              });
            });
          },
        ),
        //Ao clicar no item é aberta a página de detalhes
        onTap: () {
          bloc.selectedChar = char;
          Navigator.push(
            context,
            MaterialPageRoute(
              builder: (context) => DetailPage(),
            ),
          );
        },
      ),
    );
  }

  Widget _searchBar() {
    return Container(
        // color: Colors.amber.withOpacity(0.3),
        color: Colors.orange,
        padding: EdgeInsets.symmetric(vertical: 5.0, horizontal: 4.0),
        child: TextField(
          cursorColor: Colors.black,
          decoration: InputDecoration(
            hintText: 'Search character',
            suffixIcon: Icon(Icons.search),
            border: OutlineInputBorder(borderSide: BorderSide()),
            contentPadding: EdgeInsets.symmetric(
              vertical: 0.0,
              horizontal: 10.0,
            ),
          ),
          onChanged: (text) {
            searchText = text.toLowerCase();
            setState(() {
              _filterList(searchText);
            });
          },
        ));
  }
}
