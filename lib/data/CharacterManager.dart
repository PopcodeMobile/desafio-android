import 'package:flutter/material.dart';
import 'package:starchars/data/Character.dart';
import 'package:starchars/data/DatabaseManager.dart';
import 'package:starchars/data/DatabaseProvider.dart';


/// This class manages the Characters given to the UI, it talks directly to
/// DatabaseProvider and DatabaseManager and returns lists populated with valid
/// characters.

class CharacterManager{


  /// Returns a chunk of characters with size 'amnt' from the database,
  /// starting in 'fromID'.
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

  /// Returns a list with all the characters marked as favorite in the database.
  static Future<List<Character>> getFavorites() async {
    List<Character> lst = new List();

    List<Map<String, dynamic>> rec = await DatabaseProvider.db.getFavorites();

    if(rec != null) {
      rec.forEach((char) => lst.add(Character.fromMap(char)));
    }

    return lst;
  }


  /// Returns a list of characters that match a given search query.
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

  /// Loads the species and planet of a character and updates it on the database.
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