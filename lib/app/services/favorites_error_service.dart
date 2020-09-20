import 'dart:async';

import 'package:bloc_pattern/bloc_pattern.dart';
import 'package:path/path.dart';
import 'package:sqflite/sqflite.dart';
import 'package:wiki_star_wars/app/models/character_model.dart';
import 'package:wiki_star_wars/app/shared/constants.dart';

class FavoritesErrorService extends Disposable {
  FavoritesErrorService() {
    _initDB();
  }

  Future<Database> _initDB() async {
    return openDatabase(
      join(await getDatabasesPath(), DB_FAVORITES_ERRORS),
      onCreate: (db, version) {
        return db.execute(tableScript(TB_FAVORITES_ERRORS));
      },
      version: 1,
    );
  }

  Future<List<CharacterModel>> getAll() async {
    try {
      final Database db = await _initDB();
      final List<Map<String, dynamic>> maps =
          await db.query(TB_FAVORITES_ERRORS);

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

  Future<bool> checkCharacterExists(int id) async {
    try {
      final Database db = await _initDB();
      final List<Map<String, dynamic>> maps =
          await db.query(TB_FAVORITES_ERRORS, where: "id = $id");

      if (maps.length > 0) {
        return true;
      }

      return false;
    } catch (ex) {
      print(ex);
      return false;
    }
  }

  Future add(CharacterModel character) async {
    try {
      final Database db = await _initDB();

      if (await checkCharacterExists(character.id))
        await update(character);
      else
        await db.insert(
          TB_FAVORITES_ERRORS,
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
        TB_FAVORITES_ERRORS,
        character.toJson(),
        where: "id = ?",
        whereArgs: [character.id],
      );
    } catch (ex) {
      print(ex);
      return;
    }
  }

  Future remove(CharacterModel character) async {
    try {
      final Database db = await _initDB();

      await db.delete(
        TB_FAVORITES_ERRORS,
        where: "id = ?",
        whereArgs: [character.id],
      );
    } catch (ex) {
      print(ex);
      return;
    }
  }

  Future<bool> clear() async {
    final Database db = await _initDB();
    await db.delete(TB_CHARACTERS);
    return true;
  }

  @override
  void dispose() {}
}
