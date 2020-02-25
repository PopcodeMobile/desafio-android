import 'package:dio/dio.dart';
import 'package:sw_wiki/app/shared/models/character_model.dart';

class CharacterRepository {
  final Dio dio;

  CharacterRepository(this.dio);

  // Future<List<PokemonModel>> getAllPokemons() async {
  //   var response = await dio.get('/pokemon');
  //   List<PokemonModel> list = [];
  //   for (var json in (response.data['results'] as List)) {
  //     PokemonModel model = PokemonModel(name: json['name']);
  //     list.add(model);
  //   }

  //   return list;
  // }

  Future<List<CharacterModel>> getAllCharacters() async {
    var response = await dio.get('/people');

    List<CharacterModel> list = [];
    for (var json in (response.data['results'] as List)) {
      CharacterModel charModel = CharacterModel.fromJson(json);
      list.add(charModel);
    }

    return list;
  }
}
