import 'package:entrevista_android/blocs/character-bloc.dart';
import 'package:entrevista_android/models/character.dart';
import 'package:entrevista_android/ui/screens/character-details.dart';
import 'package:flappy_search_bar/flappy_search_bar.dart';
import 'package:flappy_search_bar/search_bar_style.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

class CharacterSearch extends StatefulWidget {
  @override
  _CharacterSearchState createState() => _CharacterSearchState();
}

class _CharacterSearchState extends State<CharacterSearch> {
  CharacterBloc bloc;

  @override
  void didChangeDependencies() {
    super.didChangeDependencies();
    var blocProvider = Provider.of<CharacterBloc>(context);
    if (blocProvider != this.bloc) {
      this.bloc = blocProvider;
      this.bloc.load();
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        leading: IconButton(
          icon: Icon(Icons.arrow_back),
          onPressed: () {
            Navigator.of(context).pop();
          },
        ),
        title: Text('Search Character',
            style: TextStyle(
                fontFamily: 'Lato', fontWeight: FontWeight.w600, fontSize: 22)),
        centerTitle: true,
      ),
      body: Container(
        decoration: BoxDecoration(color: Colors.black),
        child: SafeArea(
          child: Padding(
            padding: const EdgeInsets.symmetric(horizontal: 15),
            child: SearchBar<Character>(
              hintStyle: TextStyle(color: Colors.grey[600]),
              hintText: 'Type character name',
              cancellationText: Text(
                'Cancel',
                style: TextStyle(color: Colors.white),
              ),
              searchBarStyle: SearchBarStyle(
                backgroundColor: Colors.white,
                padding: EdgeInsets.all(10),
                borderRadius: BorderRadius.circular(10),
              ),
              onSearch: search,
              emptyWidget: Center(
                child: Text("Ops..nothing here", style: TextStyle(color: Colors.white,
                fontFamily: 'Lato', fontWeight: FontWeight.w600, fontSize: 22)),),

              onItemFound: (Character character, int index) {
                return Container(
                  margin: EdgeInsets.symmetric(vertical: 6),
                  child: _makeListTile(character),
                );
              },
            ),
          ),
        ),
      ),
    );
  }

  Future<List<Character>> search(String search) async {
    var list = await bloc.searchByName(name: search);
    return list;
  }

  Container _makeListTile(Character character) => Container(
        decoration: BoxDecoration(
          color: Colors.grey[800],
          border: Border(),
        ),
        child: ListTile(
          contentPadding: EdgeInsets.symmetric(horizontal: 20.0, vertical: 2.0),
          leading: Container(
            padding: EdgeInsets.only(right: 12.0),
            decoration: new BoxDecoration(
                border: new Border(
                    right: new BorderSide(width: 1.0, color: Colors.white))),
            child: Icon(Icons.person, color: Colors.white),
          ),
          title: Text(
            character.name,
            style: TextStyle(
              color: Colors.white,
              fontWeight: FontWeight.bold,
              fontFamily: 'Lato',
            ),
          ),
          subtitle: Row(
            children: <Widget>[
              Expanded(
                flex: 4,
                child: Padding(
                    padding: EdgeInsets.only(left: 10.0),
                    child: Text(character.gender,
                        style: TextStyle(color: Colors.white))),
              )
            ],
          ),
          trailing:
              Icon(Icons.keyboard_arrow_right, color: Colors.white, size: 30.0),
          onTap: () {
            Navigator.push(
                context,
                MaterialPageRoute(
                    builder: (context) => CharacterDetails(character: character)));
          },
        ),
      );
}
