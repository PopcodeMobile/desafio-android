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
      name: "Alan"  ,
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

//  Character char = await DatabaseProvider.db.getCharacterWithId(1);
////  char.fav = 1;
////  DatabaseProvider.db.updateCharacter(char)
//  DatabaseProvider.db.deleteAllCharacters();
  DatabaseManager.parseCharacters(1);
  DatabaseManager.parseCharacters(2);
  DatabaseManager.parseCharacters(3);
  DatabaseManager.parseCharacters(4);
  DatabaseManager.parseCharacters(5);
  DatabaseManager.parseCharacters(6);
  DatabaseManager.parseCharacters(7);
  DatabaseManager.parseCharacters(8);
  DatabaseManager.parseCharacters(9);



  }


  
  
}