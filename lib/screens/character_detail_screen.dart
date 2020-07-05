import 'package:entrevista_pop/providers/characters.dart';
import 'package:flutter/material.dart';

import 'package:entrevista_pop/providers/character.dart';
import 'package:entrevista_pop/widgets/character_detail.dart';

class CharacterDetailScreen extends StatefulWidget {
  @override
  _CharacterDetailScreenState createState() => _CharacterDetailScreenState();
}

class _CharacterDetailScreenState extends State<CharacterDetailScreen> {
  @override
  Widget build(BuildContext context) {
    final Character character = ModalRoute.of(context).settings.arguments;
    return Scaffold(
      appBar: AppBar(
        title: Text(
          character.name,
          style: TextStyle(color: Theme.of(context).accentColor),
        ),
      ),
      body: CharacterDetail(character),
      floatingActionButton: FloatingActionButton(
        backgroundColor: Theme.of(context).primaryColor,
        child: Icon(
          Icons.star,
          color: character.isFavorite
              ? Theme.of(context).accentColor
              : Colors.white,
        ),
        onPressed: () {
          setState(() {
            character.toggleAsFavorite();
          });
        },
      ),
    );
  }
}
