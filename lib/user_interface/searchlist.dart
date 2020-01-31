import 'package:flappy_search_bar/flappy_search_bar.dart';
import 'package:flappy_search_bar/search_bar_style.dart';
import 'package:flutter/material.dart';
import 'package:starchars/data/CharacterManager.dart';
import 'package:starchars/data/DatabaseProvider.dart';
import 'package:starchars/data/Character.dart';
import 'package:starchars/data/DatabaseManager.dart';
import 'package:starchars/user_interface/char.dart';

class Debug extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
          child: RaisedButton(
        onPressed: _apertar,
        child: Text('Hello World'),
      )),
    );
  }

  void _apertar() async {
//  Character char = await DatabaseProvider.db.getCharacterWithId(1);
////  char.fav = 1;
////  DatabaseProvider.db.updateCharacter(char)
//  DatabaseProvider.db.deleteAllCharacters();
//  DatabaseManager.parseCharacters(1);
//  DatabaseManager.parseCharacters(2);
//  DatabaseManager.parseCharacters(3);
//  DatabaseManager.parseCharacters(4);
//  DatabaseManager.parseCharacters(5);
//  DatabaseManager.parseCharacters(6);
//  DatabaseManager.parseCharacters(7);
//  DatabaseManager.parseCharacters(8);
//  DatabaseManager.parseCharacters(9);

    //atabaseManager.postFavorite(1, false, context);
    CharacterManager.getSearch('Ra');
  }
}

class Search extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Container(
        color: Color.fromRGBO(126, 120, 99, 1.0),
        child: SafeArea(
        child: Padding(
          padding: const EdgeInsets.symmetric(horizontal: 10),
          child: SearchBar<Character>(
            minimumChars: 2,
            searchBarStyle: SearchBarStyle(
              backgroundColor: Colors.white,
              borderRadius: BorderRadius.circular(10),
            ),
            onSearch: search,
            onItemFound: (Character character, int index) {
              return CharItem(character);
            },
          ),
        ),
      ),
    ));
  }

  Future<List<Character>> search(String search) async {
    await Future.delayed(Duration(seconds: 2));
    return CharacterManager.getSearch(search);
  }




}
