import 'package:dio/dio.dart';
import 'package:star_wars_wiki/shared/custom_dio/custom_dio.dart';
import 'package:star_wars_wiki/shared/models/character_model.dart';

class HomeRepository {
  final CustomDio _client;

  HomeRepository(this._client);

  Future<List<CharacterModel>> getCharacters({int page: 1}) async {
    try {
      var response = await _client.get('/people/?page=$page');
      return (response.data['results'] as List)
          .map((item) => CharacterModel.fromJson(item))
          .toList();
    } on DioError catch (e) {
      throw (e.message);
    }
  }
}
