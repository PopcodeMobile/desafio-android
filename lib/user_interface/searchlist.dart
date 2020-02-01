import 'package:flappy_search_bar/flappy_search_bar.dart';
import 'package:flappy_search_bar/search_bar_style.dart';
import 'package:flutter/material.dart';
import 'package:starchars/data/CharacterManager.dart';
import 'package:starchars/data/DatabaseProvider.dart';
import 'package:starchars/data/Character.dart';
import 'package:starchars/data/DatabaseManager.dart';
import 'package:starchars/user_interface/char.dart';

/// This is the search screen, given a query, it lists the matching characters
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
