import 'dart:io';

import 'package:entrevista_android/data/character_repository.dart';
import 'package:entrevista_android/models/character.dart';
import 'package:path/path.dart';
import 'package:sqflite/sqflite.dart';
import 'package:path_provider/path_provider.dart';

class SQLiteDatabase implements CharacterRepository {
  // This is the actual database filename that is saved in the docs directory.
  static final _databaseName = "database.db";

  static final _databaseVersion = 1;
  static final SQLiteDatabase instance = SQLiteDatabase._privateConstructor();

  SQLiteDatabase._privateConstructor();

  static Database _database;

  Future<Database> get database async {
    if (_database != null) return _database;
    _database = await _initDatabase();
    return _database;
  }

  _initDatabase() async {
    Directory documentsDirectory = await getApplicationDocumentsDirectory();
    String path = join(documentsDirectory.path, _databaseName);
    return await openDatabase(path,
        version: _databaseVersion, onCreate: _onCreate);
  }

  // SQL string to create the database
  Future _onCreate(Database db, int version) async {
    await db.execute('''
              CREATE TABLE IF NOT EXISTS ${Character.tableName} (
                ${Character.columnId} INTEGER PRIMARY KEY,
                ${Character.columnName} TEXT NOT NULL,
                ${Character.columnBirth_year} TEXT NOT NULL,
                ${Character.columnEye_color} TEXT NOT NULL,
                ${Character.columnHair_color} TEXT NOT NULL,
                ${Character.columnGender} TEXT NOT NULL,
                ${Character.columnHeight} TEXT NOT NULL,
                ${Character.columnMass} TEXT NOT NULL,
                ${Character.columnSkin_color} TEXT NOT NULL,
                ${Character.columnSpeciesUrl} TEXT NOT NULL,
                ${Character.columnBirthPlanetUrl} TEXT NOT NULL,
                ${Character.columnIsFavorite}  INTEGER NULL
              )
              ''');
  }

  @override
  Future<int> delete(int id) async {
    final db = await database;
    var res = await db
        .delete("${Character.tableName}", where: "id = ?", whereArgs: [id]);
    return res;
  }

  @override
  Future<int> insert(Character character) async {
    final db = await database;
    var res = await db.insert("${Character.tableName}", character.toDb());
    return res;
  }

  @override
  Future<List<Character>> readAll() async {
    final db = await database;
    var res = await db.query("${Character.tableName}");
    List<Character> list =
        res.isNotEmpty ? res.map((c) => Character.fromDb(c)).toList() : [];
    return list;
  }

  @override
  Future<int> update(Character character) async {
    final db = await database;
    print('updating..;');
      return await db.update("${Character.tableName}", character.toDb(), where: "id = ?", whereArgs: [character.id]);
  }

  @override
  Future<Character> getCharacterById(int id) async {
    final db = await database;
    var res = await db
        .query("${Character.tableName}", where: "id = ?", whereArgs: [id]);
    return res.isNotEmpty ? Character.fromDb(res.first) : Character();
  }

  @override
   insertMultipleIfNotExists(List<Character> list) async {
    final db = await database;
    final batch = db.batch();
    list.forEach((character) async {
      var existingCharacters = await db.rawQuery(
          'SELECT * FROM ${Character.tableName} WHERE id="${character.id}"');
      if (existingCharacters.isEmpty) {
        batch.insert("${Character.tableName}", character.toDb());
      } else {
        batch.update("${Character.tableName}", character.toDb(),
            where: "id = ?", whereArgs: [character.id]);
      }
    });
    await batch.commit(noResult: true);
  }
}
