import 'package:flutter/material.dart';
import 'package:starchars/data/Character.dart';
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

}