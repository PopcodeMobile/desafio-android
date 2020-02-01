import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:starchars/data/DatabaseProvider.dart';
import 'package:starchars/data/Character.dart';
import 'dart:async';
import 'package:http/http.dart' as http;
const baseUrl = "https://swapi.co/api/people/";
const apiURL = 'http://private-782d3-starwarsfavorites.apiary-mock.com/favorite/';

/// This class talks directly to SWAPI and Favorites API, parsing, saving and
/// updating the characters in the database.

class DatabaseManager {

  /// Loads a page of character from SWAPI, saves characters to database, if
  /// the character already existis in the database it is updated.
  static Future<bool> parseCharacters(int page) async {
    var url = baseUrl + "?page=" + page.toString();
    print(url);
    final res = await http.get(url);
    final jsonData = json.decode(utf8.decode(res.bodyBytes));
    List lista = jsonData['results'];

    for (var i = 0; i < lista.length; i++) {

      // Get the character id
      String url = lista[i]['url'].toString();
      int id = int.parse(url.substring(28, url.length - 1));


      // Get the Species and Planet
      String planetURL = lista[i]['homeworld'];
      String speciesURL = "";
      if (lista[i]['species']
          .toString()
          .length > 2) {
        speciesURL = lista[i]['species'][0];
      }
      String species = speciesURL;
      String planet = planetURL;

      // Check if character exists
      bool exists = await charExists(id);
      int fav = 0;

      // If it exists, find out if it is favorited
      if(exists){
        Character char = await DatabaseProvider.db.getCharacterWithId(id);
        fav = char.fav;
      }


      // Create new character
      Character novo = Character.fromJson(lista[i], id, planet, species, fav);

      // Save or update
      if(exists){
        DatabaseProvider.db.updateCharacter(novo);
      }

      else {
        DatabaseProvider.db.addCharacterToDatabase(novo);
      }
    }

    return true;
  }


  /// Gets a Species and a Homeworld from a URL.
  static Future<String> loadSpeciesPlanets(String url) async {
    if (url.length > 0) {
      final res = await http.get(url);
      try {
        if (res.statusCode == 200) {
          final jsonData = json.decode(res.body);
          return jsonData['name'];
        }
      } catch (e) {

      }
    }
    return "unknown";
  }

  /// Checks if a character with given id already exists in the database.
  static Future<bool> charExists(int id) async {

    Character char = await DatabaseProvider.db.getCharacterWithId(id);

    if(char != null){
      return true;
    }
    return false;
  }

  /// Post favorite to API, the success variable is a dummy to indicate if the
  /// request is supposed to fail or success.
  static Future<int> postFavorite(int id, bool sucess,
      BuildContext context) async {
    var mensagem = "Não mudou";
    http.Response res;

    if(sucess){
      res = await http.post(apiURL + 'id');
    }

    else{
      res = await http.post(apiURL + 'id', headers: {"prefer": "status=400"});
    }

    final jsonData = json.decode(res.body);
    if(res.statusCode == 201) {
      mensagem = "Success - " + jsonData["message"];
    }

    else if(res.statusCode == 400){
      mensagem = "Failed - " + jsonData["error_message"];
    }
    print(mensagem);

    Scaffold.of(context).showSnackBar(SnackBar(
      content: Text(mensagem),
    ));


    return res.statusCode;

  }

}
