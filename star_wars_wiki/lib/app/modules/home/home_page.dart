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
  List<CharacterModel> filter = List<CharacterModel>();
  @override
  void initState() {
    for (int i = 1; i < 10; i++) {
      bloc.fetchCharacters(page: i);
    }
    list = bloc.list;
    super.initState();
  }

  void _refreshList(String s) {
    if (s == 'all')
      setState(() {
        list = bloc.list;
      });
    else {
      setState(() {
        list = list.where((item) {
          return item.fav;
        }).toList();
      });
    }
  }

  @override
  Widget build(BuildContext context) {
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
      body: SingleChildScrollView(
        child: StreamBuilder<List<CharacterModel>>(
            stream: bloc.responseOut,
            builder: (context, snapshot) {
              if (snapshot.hasError) {
                return Center(child: Text(snapshot.error.toString()));
              }

              if (snapshot.hasData) {
                return Column(
                  children: ([_searchBar()] +
                      list.map((item) => _characterCard(item)).toList() +
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
    );
  }

  Widget _searchBar() {
    return Padding(
        padding: EdgeInsets.symmetric(vertical: 10.0, horizontal: 10.0),
        child: TextField(
          decoration: InputDecoration(hintText: 'Search character'),
          onChanged: (text) {
            text = text.toLowerCase();
            setState(() {
              list = bloc.list.where((item) {
                var itemTitle = item.name.toLowerCase();
                return itemTitle.contains(text);
              }).toList();
            });
          },
        ));
  }

  Widget _characterCard(CharacterModel char) {
    return Card(
      shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(5.0)),
      elevation: 3.0,
      // color: Theme.of(context).primaryColor.withAlpha(100),
      color: Colors.black54,
      child: ListTile(
        title: Text(
          char.name,
          style: TextStyle(
            fontFamily: 'Times',
          ),
        ),
        subtitle: Text(
          'Mass:${char.mass}, Height: ${char.height}, Gender: ${char.gender}',
        ),
        trailing: IconButton(
          tooltip: 'Favorite',
          icon: Icon(
            char.fav ? Icons.star : Icons.star_border,
            color: Colors.yellow.withAlpha(150),
            size: 35.0,
          ),
          onPressed: () {
            setState(() {
              bloc.favoriteCharacter(char);
            });
          },
          // splashColor: Theme.of(context).primaryColor,
        ),
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
}
