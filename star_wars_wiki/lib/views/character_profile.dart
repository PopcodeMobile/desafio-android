import 'package:flutter/material.dart';
import 'package:get_it/get_it.dart';
import 'package:star_wars_wiki/controllers/character_controller.dart';

class CharacterProfile extends StatelessWidget {

  @override
  Widget build(BuildContext context) {
    final controller = GetIt.I.get<CharacterController>();
    return Scaffold(
      appBar: AppBar(
        title: Text('Details'),
        actions: <Widget>[
          IconButton(
            icon: Icon(Icons.star_border),
            tooltip: 'Add To Favorites',
            onPressed: () {}
          )
        ],
      ),
      body: Column(
        crossAxisAlignment: CrossAxisAlignment.stretch,
        children: <Widget>[
          _showCard('Name: ${controller.character.name}'),
          _showCard('Height: ${controller.character.height}'),
          _showCard('Mass: ${controller.character.mass}'),
          _showCard('Hair Color: ${controller.character.hairColor}'),
          _showCard('Skin Color: ${controller.character.skinColor}'),
          _showCard('Eye Color: ${controller.character.eyeColor}'),
          _showCard('Birth Year: ${controller.character.birthYear}'),
          _showCard('Gender: ${controller.character.gender}'),
          _showCard('Homeworld: ${controller.character.homeworld}'),
          _showCard('Species: ${controller.character.species}'),
        ],
      ),
    );
  }

  _showCard (String cardText) {
    return Card(
      elevation: 2.0,
      child: Padding(
        padding: const EdgeInsets.all(20.0),
        child: Text(
          cardText.toUpperCase(),
        ),
      ),
    );
  }
}