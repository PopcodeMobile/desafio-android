import 'dart:convert';

import 'package:entrevista_android/models/character.dart';
import 'package:http/http.dart' as http;

class SwapiClient {
  static const String _BASE_URL = "swapi.co";

  Future<List<Character>> fetchCharactersByPage({int page = 1}) async {
    final Uri uri = new Uri.http(_BASE_URL, "/api/people/", {"page": '$page'});

    http.Response response = await http.get(uri);
    if (response.statusCode == 200) {
      final jsonResponse = json.decode(response.body);
      Iterable list = jsonResponse['results'];
      var charList = list.map((ch) => Character.fromJson(ch)).toList();
      charList.forEach((ch) => _loadDetails(ch));

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
  Future<Character> _loadDetails(Character ch) async {
    ch.birth_planet = await _loadBirthPlanet(ch.birth_planet);
    ch.species = await _loadSpecies(ch.species);

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
  Future<List<String>> _loadSpecies(List<dynamic> specieUrls) async {
    List<String> list = List();
    try {
      for (var url in specieUrls) {
        var res = await http.get(url);
        if (res.statusCode == 200) {
          final jsonResponse = json.decode(res.body);
          list.add(jsonResponse['name']);
        }
      }
    } catch (e) {
      throw Exception("Error searching API data details");
    }

    return list;
  }
}
