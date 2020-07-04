import 'package:flutter/material.dart';

import 'package:entrevista_pop/providers/character.dart';
import 'package:entrevista_pop/widgets/character_detail.dart';

class CharacterDetailScreen extends StatelessWidget {
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
    );
  }
}
