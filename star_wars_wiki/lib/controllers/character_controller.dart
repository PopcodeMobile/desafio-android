import 'package:mobx/mobx.dart';
import 'package:dio/dio.dart';
import 'package:star_wars_wiki/models/character.dart';
part 'character_controller.g.dart';

class CharacterController = _CharacterControllerBase with _$CharacterController;

abstract class _CharacterControllerBase with Store {
  final character = Character();
  
  Future<Map> getData () async {
    final dio = Dio();
    Response response = await dio.get('https://swapi.co/api/people');
    return response.data;
  }
}