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
          _showBasicInfo(),
          _showAdicionalInfo(),
        ],
      ),
    );
  }

  _showBasicInfo () {
    return Card(
      elevation: 1.0,
      child: Padding(
        padding: const EdgeInsets.all(20.0),
        child: Column(
          children: <Widget>[
            Padding(
              padding: EdgeInsets.only(bottom: 18.0),
              child: Text(
                controller.charList[index].name.toUpperCase(),
              ),
            ),
            Row(
              //mainAxisAlignment: MainAxisAlignment.center,
              //crossAxisAlignment: CrossAxisAlignment.center,
              children: <Widget>[
                Expanded(
                  child: Text(
                    'Gender: ${controller.charList[index].gender}',
                    textAlign: TextAlign.center,
                  )
                ),
                Expanded(
                  child: Text(
                    'Height: ${controller.charList[index].height}',
                    textAlign: TextAlign.center,
                ),
                ),
                Expanded(
                  child: Text(
                    'Mass: ${controller.charList[index].mass}',
                    textAlign: TextAlign.center,
                  )
                ),
              ],
            ),
          ],
        ),
      ),
    );
  }

  _showAdicionalInfo () {
    return Card(
      child: Column(
        children: <Widget>[
          Text(
            'Hair Color: ${controller.charList[index].hairColor}',
          ),
          Text(
            'Skin Color: ${controller.charList[index].skinColor}',
          ),
          Text(
            'Eye Color: ${controller.charList[index].eyeColor}',
          ),
          Text(
            'Birth Year: ${controller.charList[index].birthYear}',
          ),
          Text(
            'Homeworld: ${controller.charList[index].homeworld}',
          ),
          Text(
            'Species: ',
          ),
          _getSpecies(),
        ],
      ),
    );
  }

  _getSpecies () {
    for (String specie in controller.charList[index].species) {
      return Text(
        specie,
      );
    }
  }
}