import 'dart:async';

import 'package:entrevista_android/data/character_repository.dart';
import 'package:entrevista_android/data/database_factory.dart';
import 'package:entrevista_android/models/character.dart';
import 'package:entrevista_android/services/swapi-client.dart';
import 'package:flutter/widgets.dart';

class CharacterService extends ChangeNotifier {
  //paginator counter used in api responses
  int _pageIndex = 1;

  //api client
  final SwapiClient _api = SwapiClient();

  //database instance
  final CharacterRepository database = DatabaseFactory.create();

  Future<List<Character>> readAllfromDatabase() async {
    var list = await database.readAll();
    print('ffrom database: $list');

    return list;
  }

  Future<List<Character>> load() async {
    if(_pageIndex <= 9){
    List<Character> list = await _api.fetchCharactersByPage(page: _pageIndex);
    database.insertMultipleIfNotExists(list);
    print('fetched and saved locally: $list');
    _pageIndex++;
    return list;
    }
    return [];
    
  }

  Future<List<Character>> searchByName({String name}) async {
    return await _api.searchCharacterByName(name);
  }

  void markFavorite(Character character) {
    character.isFavorite = !character.isFavorite;
    database.update(character);

  }

  Future<Character> readCharacterById(int index) async {
    return await database.getCharacterById(index);
  }
}
