

import 'package:entrevista_android/models/character.dart';

abstract class CharacterRepository{

  Future<int> insert(Character character);

  Future<int> delete(int id);

  Future<int> update(Character character);

  Future<List<Character>> readAll();

  Future<Character> getCharacterById(int index);

  void insertMultipleIfNotExists(List<Character> list);
}