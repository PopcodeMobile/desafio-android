import 'package:flutter/material.dart';
import 'package:starchars/data/Character.dart';
import 'package:starchars/data/DatabaseProvider.dart';


class CharacterManager{

  static Future<List<Character>> getCharChunk(int fromID, int amnt) async {
    print("CHAMOU");

     List<Character> lst = new List();
     
     for(var i = fromID; i < fromID + amnt; i ++){
       
       Character character = await DatabaseProvider.db.getCharacterWithId(i);
       if(character != null) {
         lst.add(character);
       }

       //print("Added: " + character.name + i.toString());
       
     }

//    for(var i = 0; i < lst.length; i++){
//      print(lst[i].name);
//    }

     return lst;
  }

}