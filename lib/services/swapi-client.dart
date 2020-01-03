import 'dart:convert';
import 'dart:io';

import 'package:entrevista_android/models/character.dart';
import 'package:http/http.dart' as http;

class SwapiClient {
  static const String _BASE_URL = "swapi.co";
  static const String _FAVORITE_API_URL = 'http://private-782d3-starwarsfavorites.apiary-mock.com/favorite/';

  Future<List<Character>> fetchCharactersByPage({int page = 1}) async {
    final Uri uri = new Uri.http(_BASE_URL, "/api/people/", {"page": '$page'});

    http.Response response = await http.get(uri);
    if (response.statusCode == 200) {
      final jsonResponse = json.decode(response.body);
      Iterable list = jsonResponse['results'];
      var charList = list.map((ch) => Character.fromJson(ch)).toList();
      charList.forEach((ch) async => await loadDetails(ch));

      return charList;
    } else {
      throw Exception("Error fetching API data");
    }
  }

  Future<List<Character>> searchCharacterByName(String name) async {
    final Uri uri =
        new Uri.http(_BASE_URL, "/api/people/", {"search": '$name'});

    http.Response response = await http.get(uri);
    if (response.statusCode == 200) {
      final jsonResponse = json.decode(response.body);
      Iterable list = jsonResponse['results'];
      return list.map((ch) => Character.fromJson(ch)).toList();
    } else {
      throw Exception("Error searching API data");
    }
  }

  /// Loads Character data asynchronously
  Future<Character> loadDetails(Character ch) async {
    if (null != ch.birth_planet && ch.birth_planet.startsWith('http')){
      ch.birth_planet = await _loadBirthPlanet(ch.birth_planet);
    }

    if (ch.specie.isNotEmpty && ch.specie[0].startsWith('http')) {
      ch.specie[0] = await _loadSpecies(ch.specie[0]);
    }

    return ch;
  }

  /// Calls GET request for birth planet data from
  /// swapi /planets/ resource
  Future<String> _loadBirthPlanet(String url) async {
    http.Response response = await http.get(url);
    if (response.statusCode == 200) {
      final jsonResponse = json.decode(response.body);
      return (jsonResponse['name']);
    }
  }

  /// Calls GET request for species data from
  /// swapi /species/ resource
  Future<String> _loadSpecies(String specieUrl) async {
    try {
      var res = await http.get(specieUrl);
      if (res.statusCode == 200) {
        final jsonResponse = json.decode(res.body);
        return jsonResponse['name'];
      }
    } catch (e) {
      throw Exception("Error searching API data details");
    }
  }


  Future<String> postFavorite(int characterId) async{
    try{
    http.Response response = await http.post(_FAVORITE_API_URL+'$characterId');
    if(response.statusCode == 201){
        final jsonResponse = json.decode(response.body);
        return jsonResponse['message'];
      }else if(response.statusCode == 400){
        final jsonResponse = json.decode(response.body);
        return jsonResponse['error_message'];
      }

    }on HttpException catch (e){
      print(e);
    }
  }






}
