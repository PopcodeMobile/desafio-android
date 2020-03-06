import 'package:flutter/material.dart';
import 'package:flutter_mobx/flutter_mobx.dart';
import 'package:get_it/get_it.dart';
import 'package:star_wars_wiki/controllers/character_controller.dart';
import 'package:toast/toast.dart';

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
          Observer(builder: (_) {
            return IconButton(
                icon: controller.charList[index].isFavorite
                    ? Icon(
                        Icons.star,
                        color: Theme.of(context).accentColor,
                      )
                    : Icon(Icons.star_border),
                onPressed: () async {
                  String message = await controller.setFavorite(
                      controller.charList[index], index);
                  Toast.show(message, context,
                      duration: Toast.LENGTH_LONG, gravity: Toast.BOTTOM);
                });
          }),
        ],
      ),
      body: FutureBuilder(
          future: Future.wait([
            controller.loadHomeworldData(index),
            controller.loadSpeciesData(index),
          ]),
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
          }),
    );
  }

  _showBody() {
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

  _showBasicInfo() {
    return Card(
      elevation: 1.0,
      child: Padding(
        padding: const EdgeInsets.all(20.0),
        child: Column(
          children: <Widget>[
            Padding(
              padding: EdgeInsets.symmetric(vertical: 18.0),
              child: Text(
                controller.charList[index].name.toUpperCase(),
              ),
            ),
          ],
        ),
      ),
    );
  }

  _showAdicionalInfo() {
    return Card(
      elevation: 1.0,
      child: Column(
        children: <Widget>[
          _showText('Height: ${controller.charList[index].height}'),
          _showText('Mass: ${controller.charList[index].mass}'),
          _showText('Gender: ${controller.charList[index].gender}'),
          _showText('Hair Color: ${controller.charList[index].hairColor}'),
          _showText('Skin Color: ${controller.charList[index].skinColor}'),
          _showText('Eye Color: ${controller.charList[index].eyeColor}'),
          _showText('Birth Year: ${controller.charList[index].birthYear}'),
          _showText('Homeworld: ${controller.charList[index].homeworld}'),
          _showText('Species: ${controller.charList[index].species}'),
        ],
      ),
    );
  }

  _showText(text) {
    return Padding(
      padding: const EdgeInsets.all(20.0),
      child: Text(
        text,
      ),
    );
  }
}
