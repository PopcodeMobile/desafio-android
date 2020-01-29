import 'package:flutter/material.dart';
import 'package:starchars/data/CharacterManager.dart';
import 'package:starchars/data/DatabaseProvider.dart';
import 'package:starchars/data/Character.dart';
import 'package:starchars/data/DatabaseManager.dart';

class Debug extends StatelessWidget {

  Character novo = new Character(
      id: 0,
      name: "Alan",
      height: "188",
      mass: "110",
      hair: "Black",
      skin: "Black",
      eye: "Brown",
      year: "1996",
      gender: "Male",
      planet: "Earth",
      species: "Human",
      fav: 0);

  Character novo1 = new Character(
      id: 0,
      name: "Alan",
      height: "188",
      mass: "110",
      hair: "Black",
      skin: "Black",
      eye: "Brown",
      year: "1996",
      gender: "Male",
      planet: "Earth",
      species: "Human",
      fav: 0);

  Character novo2 = new Character(
      id: 0,
      name: "Alan",
      height: "188",
      mass: "108",
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
  Character character = await DatabaseProvider.db.getCharacterWithId(50);
  print(character.name);
  }

  
  
}