import 'package:entrevista_popcode_flutter/helpers/helperPessoa.dart';
import 'package:sqflite/sqflite.dart';
import 'package:path/path.dart';

abstract class HelperBase<T> {
  static final String dataBaseName = "starWars.db";
  Database _database;

  Future<T> getFirst(String id);
  Future<T> save(T curso);
  Future<int> delete(String nome);
  Future<int> update(T data);
  Future<List> getAll();
  Future<int> getNumber();
  Future deleteAll();

  Future<Database> get db async {
    if (_database != null) {
      return _database;
    } else {
      _database = await _create();
      return _database;
    }
  }

  Future<Database> _create() async {
    final databasesPath = await getDatabasesPath();
    final path = join(databasesPath, dataBaseName);

    return openDatabase(path, version: 1,
        onCreate: (Database db, int newerVersion) async {
      await db.execute(
        "CREATE TABLE IF NOT EXISTS ${HelperPessoa.pessoaTable}(${HelperPessoa.idColumn} INTEGER PRIMARY KEY AUTOINCREMENT,"
        "${HelperPessoa.nameColumn} TEXT, ${HelperPessoa.heightColumn} TEXT, ${HelperPessoa.massColumn} TEXT,"
        "${HelperPessoa.hairColorColumn} TEXT, ${HelperPessoa.skinColorColumn} TEXT, ${HelperPessoa.eyeColorColumn} TEXT,"
        "${HelperPessoa.birthYearColumn} TEXT, ${HelperPessoa.genderColumn} TEXT, ${HelperPessoa.homeWorldColumn} TEXT)");});
  }
}