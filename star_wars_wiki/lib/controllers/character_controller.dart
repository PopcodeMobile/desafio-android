import 'package:mobx/mobx.dart';
import 'package:dio/dio.dart';
import 'package:star_wars_wiki/models/character.dart';
part 'character_controller.g.dart';

class CharacterController = _CharacterControllerBase with _$CharacterController;

abstract class _CharacterControllerBase with Store {
  final character = Character();

  @action
  setCharacterInfo (selected) {
    character.name = selected['name'];
    character.height = selected['height'];
    character.mass = selected['mass'];
    character.hairColor = selected['hair_color'];
    character.skinColor = selected['skin_color'];
    character.eyeColor = selected['eye_color'];
    character.birthYear = selected['birth_year'];
    character.gender = selected['gender'];
    character.homeworld = selected['homeworld'];
    //character.species = selected['species'];
  }

  Future<Map> getData () async {
    final dio = Dio();
    Response response = await dio.get('https://swapi.co/api/people/');
    return response.data;
  }
}