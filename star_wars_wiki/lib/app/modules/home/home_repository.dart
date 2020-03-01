import 'dart:convert';

import 'package:dio/dio.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:star_wars_wiki/shared/constants.dart';
import 'package:star_wars_wiki/shared/custom_dio/custom_dio.dart';
import 'package:star_wars_wiki/shared/models/character_model.dart';
import 'package:star_wars_wiki/shared/models/planet_model.dart';
import 'package:star_wars_wiki/shared/models/specie_model.dart';

class HomeRepository {
  final CustomDio _client;

  HomeRepository(this._client);

  Future<List<CharacterModel>> getCharacters({int page: 1}) async {
    try {
      var response = await _client.get('$BASE_URL/people/?page=$page');
      var charList = (response.data['results'] as List)
          .map((item) => CharacterModel.fromJson(item))
          .toList();
      SharedPreferences prefs = await SharedPreferences.getInstance();
      for (var char in charList) {
        if (prefs.containsKey('favorites/${char.name}')) char.fav = true;
      }
      return charList;
    } on DioError catch (e) {
      throw (e.message);
    }
  }

  Future<PlanetModel> getPlanet(String path) async {
    try {
      var response = await _client.get('$BASE_URL$path');
      return PlanetModel.fromJson(response.data);
    } on DioError catch (e) {
      throw (e.message);
    }
  }

  Future<SpecieModel> getSpecie(path) async {
    try {
      var response = await _client.get('$BASE_URL$path');
      return SpecieModel.fromJson(response.data);
    } on DioError catch (e) {
      throw (e.message);
    }
  }

  Future<String> setFavorite(String id) async {
    String message;
    try {
      var response = await _client.post(
        '$FAVS_BASE_URL/favorite/$id',
      );
      message = response.data['message'];
    } on DioError catch (e) {
      message = e.message;
      throw (e.message);
    }
    SharedPreferences prefs = await SharedPreferences.getInstance();
    prefs.setString("favorites/$id", id);
    return message;
  }

  Future<String> removeFavorite(String id) async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    if (prefs.containsKey('favorites/$id')) {
      if (await prefs.remove('favorites/$id'))
        return 'Removido dos favoritos';
      else
        return 'Erro ao remover dos favoritos';
    } else {
      return 'Você não deveria estar vendo isso';
    }
  }

  Future<bool> isFavorite(String id) async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    return prefs.containsKey('favorites/$id');
  }
}
