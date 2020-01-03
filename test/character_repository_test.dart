
import 'package:entrevista_android/data/database_factory.dart';
import 'package:entrevista_android/models/character.dart';
import 'package:flutter_test/flutter_test.dart';

main(List<String> args) {
  test('test character insertion', () async {

    final database = DatabaseFactory.create();
    var character = Character(name: 'test name');
    int idResulted = await database.insert(character);

    expect(idResulted, 1);
  });
}