import 'package:flutter/material.dart';
import 'package:get_it/get_it.dart';
import 'package:star_wars_wiki/controllers/character_controller.dart';

class CharacterDetails extends StatelessWidget {
  
  CharacterDetails(this.index);
  final int index;
  final controller = GetIt.I.get<CharacterController>();
  
  @override
  Widget build(BuildContext context) {
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
      body: FutureBuilder(
        future: Future.wait(
          [
            controller.loadHomeworldData(index),
            controller.loadSpeciesData(index),
          ]
        ),
        builder: (context, snapshot) {
          //controller.charList[index].homeworld = snapshot.data['name'];
          switch (snapshot.connectionState) {
            case ConnectionState.none:
            case ConnectionState.waiting:
              return Center(
                child: CircularProgressIndicator(),
              );
            default:
              if (snapshot.hasError) {
                return Center(
                  child: Text('Error on load data. Try again later.'),
                );
              } else {
                return _showBody();
              }
          }
        }
      ),
    );
  }

  _showBody () {
    return SingleChildScrollView(
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.stretch,
        children: <Widget>[
          _showCard(controller.charList[index].name),
          _showCard('Height: ${controller.charList[index].height}'),
          _showCard('Mass: ${controller.charList[index].mass}'),
          _showCard('Hair Color: ${controller.charList[index].hairColor}'),
          _showCard('Skin Color: ${controller.charList[index].skinColor}'),
          _showCard('Eye Color: ${controller.charList[index].eyeColor}'),
          _showCard('Birth Year: ${controller.charList[index].birthYear}'),
          _showCard('Gender: ${controller.charList[index].gender}'),
          _showCard('Homeworld: ${controller.charList[index].homeworld}'),
          _showCard('Species: ${controller.charList[index].species[0]}'),
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