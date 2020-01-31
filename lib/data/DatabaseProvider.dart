import 'dart:async';
import 'dart:io';
import 'package:path/path.dart';
import 'package:path_provider/path_provider.dart';
import 'package:starchars/data/Character.dart';
import 'package:sqflite/sqflite.dart';

class DatabaseProvider {
  DatabaseProvider._();

  static final DatabaseProvider db = DatabaseProvider._();
  Database _database;

  Future<Database> get database async {
    if (_database != null) return _database;
    _database = await getDatabaseInstance();
    return _database;
  }

  Future<Database> getDatabaseInstance() async {
    Directory directory = await getApplicationDocumentsDirectory();
    String path = join(directory.path, "data.db");
    return await openDatabase(path, version: 1,
        onCreate: (Database db, int version) async {
          await db.execute("CREATE TABLE character ("
              "id integer primary key AUTOINCREMENT,"
              "name TEXT,"
              "height TEXT,"
              "mass TEXT,"
              "hair TEXT,"
              "skin TEXT,"
              "eye TEXT,"
              "year TEXT,"
              "gender TEXT,"
              "planet TEXT,"
              "species TEXT,"
              "fav INTEGER"
              ")");
        });
  }

  addCharacterToDatabase(Character character) async {
    final db = await database;
    var raw = await db.insert(
      "character",
      character.toMap(),
      conflictAlgorithm: ConflictAlgorithm.replace,
    );
    return raw;
  }

  updateCharacter(Character character) async {
    final db = await database;
    var response = await db.update("character", character.toMap(),
        where: "id = ?", whereArgs: [character.id]);
    return response;
  }

  Future<Character> getCharacterWithId(int id) async {
    final db = await database;
    var response = await db.query("character", where: "id = ?", whereArgs: [id]);
    return response.isNotEmpty ? Character.fromMap(response.first) : null;
  }

  Future<List<Map<String, dynamic>>> getFavorites() async {
    final db = await database;
    List<Map<String, dynamic>> rec = await db.rawQuery('SELECT * FROM character where fav = 1');

    return rec.isNotEmpty ? rec : null;
  }


  deleteCharacterWithId(int id) async {
    final db = await database;
    return db.delete("character", where: "id = ?", whereArgs: [id]);
  }

  deleteAllCharacters() async {
    final db = await database;
    db.delete("character");
  }
}