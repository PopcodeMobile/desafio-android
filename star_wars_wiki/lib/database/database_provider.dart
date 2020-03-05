import 'dart:io';

import 'package:path_provider/path_provider.dart';
import 'package:sqflite/sqflite.dart';
import 'package:path/path.dart';
import 'package:star_wars_wiki/models/character.dart';

class DatabaseProvider {

  final String CHARACTER = 'characterTable';
  final String ID = 'idColumn';
  final String ISFAVORITE = 'isFavoriteColumn';
  final String NAME = 'nameColumn';
  final String HEIGHT = 'heightColumn';

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
    String path = join(documentsDirectory.path, "characters_db.db");
    return await openDatabase(
      path,
      version: 1,
      onOpen: (db) {},
      onCreate: (Database db, int version) async {
      await db.execute(
        "CREATE TABLE $CHARACTER ("
        "$ID INTEGER PRIMARY KEY,"
        "$ISFAVORITE INTEGER,"
        "$NAME TEXT,"
        "$HEIGHT TEXT"
        ")");
      }
    );
  }

  Future<int> charactersTableLenght () async {
    Database db = await database;
    int count = Sqflite.firstIntValue(await db.rawQuery('SELECT COUNT(*) FROM $CHARACTER'));
    return count;
  }

  Future<int> saveCharacter (Character char) async {
    Database dbChar = await database;
    int id = await dbChar.insert(CHARACTER, char.toMap());
    return id;
  }

  Future<Character> getCharacter(int id) async {
    final db = await database;
    var res = await db.query(CHARACTER, where: '$ID = ?', whereArgs: [id]);
    return res.isNotEmpty ? Character.fromMap(res.first) : null;
  }

  Future<List<Character>> readAllCharacters() async {
    final db = await database;
    var res = await db.query(CHARACTER);
    List<Character> list =
        res.isNotEmpty ? res.map((c) => Character.fromMap(c)).toList() : [];
    return list;
  }

  deleteAll() async {
    final db = await database;
    db.rawDelete("DELETE FROM $CHARACTER");
  }

  updateIsFavorite(isFavorite, id) async {
    final db = await database;
    db.rawQuery('UPDATE $CHARACTER SET $ISFAVORITE = $isFavorite WHERE $ID = $id');
  }

}