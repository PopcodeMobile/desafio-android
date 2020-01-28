import 'package:flutter/material.dart';
import 'package:starchars/data/DatabaseProvider.dart';
import 'package:starchars/data/Character.dart';

class Debug extends StatelessWidget {

  Character novo = new Character(
      id: 0,
      name: "Alan",
      height: 188,
      mass: 110,
      hair: "Black",
      skin: "Black",
      eye: "Brown",
      year: "1996",
      gender: "Male",
      planet: "Earth",
      species: "Human",
      fav: 0);



  @override
  Widget build(BuildContext context) {
    return Scaffold(
        body: Center(
          child: RaisedButton(
              onPressed: _apertar,
              child: Text('Hello World'),)
        ),
      );
  }
  
  
  void _apertar() async {
    
    Character saved = await DatabaseProvider.db.getCharacterWithId(0);
    print(saved.name);

  }

  
  
}