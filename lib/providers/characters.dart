import 'dart:convert';

import 'package:entrevista_pop/providers/character.dart';
import 'package:entrevista_pop/utils/constants.dart';
import 'package:entrevista_pop/utils/urls.dart';
import 'package:flutter/foundation.dart';
import 'package:hive/hive.dart';
import 'package:http/http.dart' as http;

class Characters with ChangeNotifier {
  Box<Map<dynamic, dynamic>> charactersListBox =
      Hive.box(Constants.charactersListBox);

  Box<Character> favoritesCharactersBox = Hive.box(Constants.favoritesBox);

  Map<String, Character> _characters = {};

  Map<String, Character> get characters {
    return _characters;
  }

  int get totalCharactersCount {
    return _characters.values.length;
  }

  int _nextPage = 1;

  int get nextPage {
    return _nextPage;
  }

  void loadCharactersFromLocalStorage() {
    _characters = charactersListBox.get('list').cast<String, Character>();
  }

  Future<void> fetchCharacters([int page = 1]) async {
    try {
      final response = await http.get("${AppUrls.BASE_URL}/people/?page=$page");
      final responseBody = json.decode(response.body);

      // if (["", null, false, 0].contains(responseBody) ||
      //     responseBody['next'] == null) {
      //   _nextPage = null;
      // } else {
      //   if (_nextPage != null) {
      //     _nextPage++;
      //   } else {
      //     _nextPage = 1;
      //   }
      // }

      // if (_nextPage == null || responseBody['results'] == null) {
      //   Future.value();
      //   return;
      // }

      // (responseBody['results'] as List<dynamic>).forEach((person) async {
      //   final homeworldReponse = await http.get(person['homeworld']);
      //   final String homeWorld = json.decode(homeworldReponse.body)['name'];

      //   String specie = 'Human';

      //   if ((person['species'] as List<dynamic>).isNotEmpty) {
      //     final specieReponse = await http.get(person['species'][0]);
      //     final specieReponseBody = json.decode(specieReponse.body);

      //     specie = specieReponseBody['name'];
      //   }

      //   final character = Character(
      //     id: person['url'],
      //     name: person['name'],
      //     birth_year: person['birth_year'],
      //     eye_color: person['eye_color'],
      //     gender: person['gender'],
      //     mass: person['mass'],
      //     hair_color: person['hair_color'],
      //     height: person['height'],
      //     skin_color: person['skin_color'],
      //     species: specie,
      //     homeworld: homeWorld,
      //   );

      //   _characters[person['url']] = character;
      //   notifyListeners();
      _characters.addAll(await fetch(responseBody));
      charactersListBox.put('list', _characters);
      // });

      notifyListeners();
    } catch (e) {
      throw "Impossível requisitar os dados no momento";
    }
  }

  Future<Map<String, Character>> fetch(dynamic responseBody) async {
    try {
      Map<String, Character> characters = {};
      if (["", null, false, 0].contains(responseBody) ||
          responseBody['next'] == null) {
        _nextPage = null;
      } else {
        if (_nextPage != null) {
          _nextPage++;
        } else {
          _nextPage = 1;
        }
      }

      if (_nextPage == null || responseBody['results'] == null) {
        Future.value();
        // return {};
      }

      for (int i = 0;
          i < (responseBody['results'] as List<dynamic>).length;
          i++) {
        final person = (responseBody['results'] as List<dynamic>)[i];
        // final homeworldReponse = await http.get(person['homeworld']);
        // final String homeWorld = json.decode(homeworldReponse.body)['name'];
        final String homeWorld = person['homeworld'];

        String specie = 'Human';

        if ((person['species'] as List<dynamic>).isNotEmpty) {
          // final specieReponse = await http.get(person['species'][0]);
          // final specieReponseBody = json.decode(specieReponse.body);
          specie = person['species'][0];
          // specie = specieReponseBody['name'];
        }

        final character = Character(
          id: person['url'],
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

        characters[person['url']] = character;
      }
      notifyListeners();

      return characters;
    } catch (e) {
      throw "Impossível requisitar os dados no momento";
    }
  }

  List<Character> _favoriteCharacters = [];

  void setFavorites() {
    _favoriteCharacters = favoritesCharactersBox.values.toList();
    notifyListeners();
  }

  List<Character> get favoriteCharacters {
    return _favoriteCharacters;
  }

  int get favoriteCharactersCount {
    return _favoriteCharacters.length;
  }

  Map<String, Character> _searchResult = {};

  Map<String, Character> get searchResult => _searchResult;

  Future<void> search(String name, int page) async {
    final response =
        await http.get("${AppUrls.BASE_URL}/people/?search=$name&page=$page");
    final responseBody = json.decode(response.body);

    if (responseBody['previous'] == null && responseBody['next'] == null) {
      _nextPage = null;
      clearSearch();
    } else if (responseBody['next'] == null) {
      _nextPage = null;
    } else {
      _nextPage = page + 1;
    }

    _searchResult.addAll(await fetch(responseBody));
  }

  void clearSearch() {
    _searchResult.clear();
  }

  void clearList() async {
    _characters.clear();
  }
}
