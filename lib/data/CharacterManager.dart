import 'package:flutter/material.dart';
import 'package:starchars/data/Character.dart';
import 'package:starchars/data/DatabaseManager.dart';
import 'package:starchars/data/DatabaseProvider.dart';


class CharacterManager{

  static Future<List<Character>> getCharChunk(int fromID, int amnt) async {

     List<Character> lst = new List();
     
     for(var i = fromID; i < fromID + amnt; i ++){
       
       Character character = await DatabaseProvider.db.getCharacterWithId(i);
       if(character != null) {
         lst.add(character);
       }
     }

     return lst;
  }


  static Future<List<Character>> getFavorites() async {
    List<Character> lst = new List();

    List<Map<String, dynamic>> rec = await DatabaseProvider.db.getFavorites();

    if(rec != null) {
      rec.forEach((char) => lst.add(Character.fromMap(char)));
    }

    return lst;
  }

  static Future<List<Character>> getSearch(String query) async {
    List<Character> lst = new List();

    List<Map<String, dynamic>> rec = await DatabaseProvider.db.getSearch(query);

    if(rec != null) {
      rec.forEach((char) => lst.add(Character.fromMap(char)));
    }

    for( var i = 0; i < lst.length; i++){
      print(lst[i].name);
    }

    return lst;
  }

  static Future<bool> getSpeciesPlanet(Character character) async {

    print("GETTING SPECIES AND PLANET OF " + character.name);

    String species = character.species;
    String planet = character.planet;

    if(Uri.parse(species).isAbsolute) {
      species = await DatabaseManager.loadSpeciesPlanets(species);
    }

    if(Uri.parse(planet).isAbsolute) {
      planet = await DatabaseManager.loadSpeciesPlanets(planet);
    }

    character.planet = planet;
    character.species = species;

    DatabaseProvider.db.updateCharacter(character);

    return true;

  }

}