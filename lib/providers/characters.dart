import 'dart:convert';

import 'package:entrevista_pop/models/character.dart';
import 'package:entrevista_pop/utils/urls.dart';
import 'package:flutter/foundation.dart';
import 'package:http/http.dart' as http;

class Characters with ChangeNotifier {
  Map<String, Character> _characters = {};

  Map<String, Character> get characters {
    return _characters;
  }

  int get totalCharactersCount {
    return _characters.values.length;
  }

  int _nextPage;

  Future<void> fetchCharacters([int page = 1]) async {
    final response = await http.get("${AppUrls.BASE_URL}/people/?page=$page");
    final responseBody = json.decode(response.body);

    _characters.clear();
    (responseBody['results'] as List<dynamic>).forEach((person) async {
      final homeworldReponse = await http.get(person['homeworld']);
      final String homeWorld = json.decode(homeworldReponse.body)['name'];

      String specie = 'Human';

      if ((person['species'] as List<dynamic>).isNotEmpty) {
        final specieReponse = await http.get(person['species'][0]);
        final specieReponseBody = json.decode(specieReponse.body);

        specie = specieReponseBody['name'];
      }

      final character = Character(
        name: person['name'],
        birth_year: person['birth_year'],
        eye_color: person['eye_color'],
        gender: person['gender'],
        mass: person['mass'],
        hair_color: person['hair_color'],
        height: person['height'],
        skin_color: person['skin_color'],
        species: specie,
        homeworld: homeWorld,
      );

      _characters[person['url']] = character;
      notifyListeners();
    });

    notifyListeners();
  }
}
