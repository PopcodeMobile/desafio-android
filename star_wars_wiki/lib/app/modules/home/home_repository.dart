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
      if ((response.statusCode == 200) || (response.statusCode == 201)) {
        var charList = (response.data['results'] as List)
            .map((item) => CharacterModel.fromJson(item))
            .toList();
        SharedPreferences prefs = await SharedPreferences.getInstance();
        for (var char in charList) {
          if (prefs.containsKey('favorites/${char.name}')) char.fav = true;
        }
        return charList;
      } else {
        return null;
      }
    } on DioError catch (e) {
      print(e.message);
      return null;
      // throw (e.message);
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
      message = e.response.data['error_message'];
      // throw (e.message);
    }
    SharedPreferences prefs = await SharedPreferences.getInstance();
    prefs.setString("favorites/$id", id);
    return message;
  }

  Future<String> removeFavorite(String id) async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    if (prefs.containsKey('favorites/$id')) {
      if (await prefs.remove('favorites/$id'))
        return 'Removed from favorites';
      else
        return 'Error removing from favorites';
    } else {
      return "Something weird happened";
    }
  }

  Future retryFavorites() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    List<String> pendingFavs = prefs.getStringList('pending_favorites');
    for (var pending in pendingFavs) {
      await setFavorite(pending);
    }
  }

  // Future<bool> isFavorite(String id) async {
  //   SharedPreferences prefs = await SharedPreferences.getInstance();
  //   return prefs.containsKey('favorites/$id');
  // }
}
