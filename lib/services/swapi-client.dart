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
      return list.map((ch) => Character.fromJson(ch)).toList();
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
}
