import 'package:entrevista_android/models/character.dart';
import 'package:flutter/material.dart';

class CharacterDetails extends StatelessWidget {

  final Character character;

  const CharacterDetails({Key key, this.character}) : super(key: key);

  
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('${character.name}',
            style: TextStyle(
                fontFamily: 'Lato', fontWeight: FontWeight.w600, fontSize: 22)),
        
        
      ),
      body: Container(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          crossAxisAlignment: CrossAxisAlignment.center,
          children: <Widget>[
            Text(character.name),
          ],
        ),
      ),
    );
  }
}
