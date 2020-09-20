import 'dart:async';

import 'package:bloc_pattern/bloc_pattern.dart';
import 'package:path/path.dart';
import 'package:sqflite/sqflite.dart';
import 'package:wiki_star_wars/app/models/character_model.dart';
import 'package:wiki_star_wars/app/shared/constants.dart';

class CharacterLocalService extends Disposable {
  CharacterLocalService() {
    _initDB();
  }

  Future<Database> _initDB() async {
    return openDatabase(
      join(await getDatabasesPath(), DB_CHARACTERS),
      onCreate: (db, version) {
        return db.execute(tableScript(TB_CHARACTERS));
      },
      version: 1,
    );
  }

  Future<bool> checkCharacterExists(int id) async {
    try {
      final Database db = await _initDB();
      final List<Map<String, dynamic>> maps =
          await db.query(TB_CHARACTERS, where: "id = $id");

      if (maps.length > 0) {
        return true;
      }

      return false;
    } catch (ex) {
      print(ex);
      return false;
    }
  }

  Future<List<CharacterModel>> getCharacters() async {
    try {
      final Database db = await _initDB();
      final List<Map<String, dynamic>> maps = await db.query(TB_CHARACTERS);

      return List.generate(
        maps.length,
        (i) {
          return CharacterModel.fromJson(maps[i]);
        },
      );
    } catch (ex) {
      print(ex);
      return new List<CharacterModel>();
    }
  }

  Future<List<CharacterModel>> getSuggestions(String search) async {
    try {
      final Database db = await _initDB();
      final List<Map<String, dynamic>> maps =
          await db.query(TB_CHARACTERS, where: "name LIKE '%$search%'");

      return List.generate(
        maps.length,
        (i) {
          return CharacterModel.fromJson(maps[i]);
        },
      );
    } catch (ex) {
      print(ex);
      return new List<CharacterModel>();
    }
  }

  Future<List<CharacterModel>> getCharacterFavorites() async {
    try {
      final Database db = await _initDB();
      final List<Map<String, dynamic>> maps =
          await db.query(TB_CHARACTERS, where: "favorite == 'true'");

      return List.generate(
        maps.length,
        (i) {
          return CharacterModel.fromJson(maps[i]);
        },
      );
    } catch (ex) {
      print(ex);
      return new List<CharacterModel>();
    }
  }

  Future add(CharacterModel character) async {
    try {
      final Database db = await _initDB();

      if (await checkCharacterExists(character.id))
        await update(character);
      else
        await db.insert(
          TB_CHARACTERS,
          character.toJson(),
        );
    } catch (ex) {
      print(ex);
      return;
    }
  }

  Future update(CharacterModel character) async {
    try {
      final Database db = await _initDB();

      await db.update(
        TB_CHARACTERS,
        character.toJson(),
        where: "id = ?",
        whereArgs: [character.id],
      );
    } catch (ex) {
      print(ex);
      return;
    }
  }

  Future<bool> checkFavorite(CharacterModel character) async {
    try {
      final Database db = await _initDB();
      final List<Map<String, dynamic>> maps = await db.query(TB_CHARACTERS,
          where: "id = ${character.id} AND favorite = 'true'");

      if (maps.length > 0) {
        return true;
      }

      return false;
    } catch (ex) {
      print(ex);
      return false;
    }
  }

  Future<bool> clear() async {
    final Database db = await _initDB();
    await db.delete(TB_CHARACTERS);
    return true;
  }

  @override
  void dispose() async {
    // final box = await completer.future;
    // box.close();
  }
}
