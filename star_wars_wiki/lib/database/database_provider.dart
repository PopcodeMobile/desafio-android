import 'dart:io';

import 'package:path_provider/path_provider.dart';
import 'package:sqflite/sqflite.dart';
import 'package:path/path.dart';
import 'package:star_wars_wiki/models/character.dart';

final characterTable = 'characterTable';
final idColumn = 'idColumn';
final isFavoriteColumn = 'isFavoriteColumn';
final nameColumn = 'nameColumn';
final heightColumn = 'heightColumn';
final massColumn = 'massColumn';
final hairColorColumn = 'hairColorColumn';
final skinColorColumn = 'skinColorColumn';
final eyeColorColumn = 'eyeColorColumn';
final birthYearColumn = 'birthYearColumn';
final genderColumn = 'genderColumn';
final homeworldReferenceColumn = 'homeworldReferenceColumn';
final speciesReferenceColumn = 'speciesReferenceColumn';
final homeworldColumn = 'homeworldColumn';
final speciesColumn = 'speciesColumn';

class DatabaseProvider {

  static Database _database;
  static final DatabaseProvider db = DatabaseProvider._();
  DatabaseProvider._();

  Future<Database> get database async {
    if (_database != null)
      return _database;
    _database = await initDB();
    return _database;
  }

  initDB() async {
    Directory documentsDirectory = await getApplicationDocumentsDirectory();
    String path = join(documentsDirectory.path, "characters_db5.db");
    return await openDatabase(
      path,
      version: 1,
      onOpen: (db) {},
      onCreate: (Database db, int version) async {
      await db.execute(
        'CREATE TABLE $characterTable ('
        '$idColumn INTEGER PRIMARY KEY,'
        '$isFavoriteColumn INTEGER DEFAULT 0,'
        '$nameColumn TEXT UNIQUE,'
        '$heightColumn TEXT,'
        '$massColumn TEXT,'
        '$hairColorColumn TEXT,'
        '$skinColorColumn TEXT,'
        '$eyeColorColumn TEXT,'
        '$birthYearColumn TEXT,'
        '$genderColumn TEXT,'
        '$homeworldReferenceColumn TEXT,'
        '$speciesReferenceColumn TEXT,'
        '$homeworldColumn TEXT,'
        '$speciesColumn TEXT'
        ')');
      }
    );
  }

  Future<int> replaceCharacter(Character char) async {
    int id = await updateCharacter(char);
    if (id == 0) {
      id = await insertCharacter(char);
    } else {
      id = await getUpdatedId(char);
    }
    return id;
  }

  Future<int> updateCharacter(Character char) async {
    Database dbChar = await database;
    var res = await dbChar.rawUpdate(
      'UPDATE $characterTable SET '
      '$heightColumn = ?, '
      '$massColumn = ?, '
      '$hairColorColumn = ?, '
      '$skinColorColumn = ?, '
      '$eyeColorColumn = ?, '
      '$birthYearColumn = ?, '
      '$genderColumn = ?, '
      '$homeworldReferenceColumn = ?, '
      '$speciesReferenceColumn = ? '
      'WHERE $nameColumn = ?',
      [
        char.height,
        char.mass,
        char.hairColor,
        char.skinColor,
        char.eyeColor,
        char.birthYear,
        char.gender,
        char.homeworldReference,
        char.speciesReference,
        char.name
      ]
    );
    return res;
  }

  Future<int> getUpdatedId(Character char) async {
    Database dbChar = await database;
    var res = await dbChar.rawQuery(
      'SELECT $idColumn FROM $characterTable '
      'WHERE $nameColumn = ?',
      [char.name]);
    return res[0][idColumn];
  }

  Future<int> insertCharacter(Character char) async {
    Database dbChar = await database;
    int id = await dbChar.insert(characterTable, char.toMap());
    print(char.isFavorite);
    return id;
  }

  Future<Character> getCharacter(int id) async {
    final db = await database;
    var res = await db.query(characterTable, where: '$idColumn = ?', whereArgs: [id]);
    return res.isNotEmpty ? Character.fromMap(res.first) : null;
  }

  Future<List<Character>> readAllCharacters() async {
    final db = await database;
    var res = await db.query(characterTable);
    List<Character> list =
        res.isNotEmpty ? res.map((c) => Character.fromMap(c)).toList() : [];
    return list;
  }

  deleteAll() async {
    final dbChar = await database;
    dbChar.rawDelete('DELETE FROM $characterTable');
  }

  Future<int> getIsFavorite(Character tempChar) async {
    final dbChar = await database;
    var res = await dbChar.rawQuery(
      'SELECT $isFavoriteColumn FROM $characterTable '
      'WHERE $nameColumn = ?',
      [tempChar.name]
    );
    return res[0][isFavoriteColumn];
  }

  updateIsFavorite(isFavorite, id) async {
    final dbChar = await database;
    dbChar.rawQuery('UPDATE $characterTable SET $isFavoriteColumn = $isFavorite WHERE $idColumn = $id');
  }

  Future<int> charactersTableLenght() async {
    Database db = await database;
    int count = Sqflite.firstIntValue(await db.rawQuery('SELECT COUNT(*) FROM $characterTable'));
    return count;
  }

}