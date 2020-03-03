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
  final bloc = HomeModule.to.getBloc<HomeBloc>();
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
          bloc.retryFavorites();
        });
      });
    }

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
      _filterList(searchText);
    } else if (show == 'favs') {
      //Mostrar os personagens favoritos
      list = list.where((item) {
        return item.fav;
      }).toList();
      _filterList(searchText);
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
        color: Colors.yellow.withOpacity(0.5),
        // alignment: Alignment.center,
        height: MediaQuery.of(context).size.height,
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
                        // bloc.fetchCharacters().then((onValue) {
                        //   setState(() {
                        //     list = bloc.list;
                        //     showList = bloc.list;
                        //   });
                        // });
                      },
                    ),
                  );
                }

                if (snapshot.hasData) {
                  return Column(
                    children: ([_searchBar()] +
                        showList
                            .map((item) => Builder(
                                builder: (context) =>
                                    _characterCard(context, item)))
                            .toList() +
                        [
                          bloc.list.length < 87
                              ? Center(
                                  child: CircularProgressIndicator(),
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
      color: Colors.black54,
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
            color: Colors.yellow.withAlpha(150),
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
    return Padding(
        padding: EdgeInsets.symmetric(vertical: 10.0, horizontal: 10.0),
        child: TextField(
          decoration: InputDecoration(hintText: 'Search character'),
          onChanged: (text) {
            searchText = text.toLowerCase();
            setState(() {
              _filterList(searchText);
            });
          },
        ));
  }
}
