import 'package:entrevista_popcode_flutter/helpers/helperBase.dart';
import 'package:entrevista_popcode_flutter/models/pessoa.dart';
import 'package:sqflite/sqflite.dart';

class HelperFavoritos extends HelperBase<Pessoa> {
  static final String pessoaTable = "tb_favoritos";
  static final String idColumn = "idColumn";
  static final String nameColumn = "nameColumn";
  static final String heightColumn = "heightColumn";
  static final String massColumn = "massColumn";
  static final String hairColorColumn = "hairColorColumn";
  static final String skinColorColumn = "skinColorColumn";
  static final String eyeColorColumn = "eyeColorColumn";
  static final String birthYearColumn = "birthYearColumn";
  static final String genderColumn = "genderColumn";
  static final String homeWorldColumn = "homeWorldColumn";
  static final String specieColumn = "specieColumn";
  static final String isFavoriteColumn = "isFavoriteColumn";
  static final HelperFavoritos _instance = HelperFavoritos.getInstance();

  factory HelperFavoritos() => _instance;
  HelperFavoritos.getInstance();

  @override
  Future<Pessoa> save(Pessoa pessoa) async {
    Database database = await db;
    Pessoa personagem = await getFirst(pessoa.name);

    if (personagem == null) {
      pessoa.idPessoa = await database.insert(pessoaTable, pessoa.toJson());
    }
    return pessoa;
  }

  @override
  Future<Pessoa> getFirst(String nome) async {
    Database database = await db;
    List<Map> personagem = await database
        .rawQuery("SELECT * FROM $pessoaTable WHERE $nameColumn LIKE '$nome'");
    if (personagem != null && personagem.length > 0) {
      return Pessoa.fromJson(personagem.first, true);
    } else {
      return null;
    }
  }

  @override
  Future<int> delete(Pessoa pessoa) async {
    Database database = await db;
    return await database
        .delete(pessoaTable, where: "$nameColumn = ?", whereArgs: [pessoa.name]);
  }

  @override
  Future<int> update(Pessoa pessoa) async {
    Database database = await db;
    return database.update(pessoaTable, pessoa.toJson(),
        where: "$nameColumn = ?", whereArgs: [pessoa.name]);
  }

  @override
  Future<List> getAll() async {
    Database database = await db;
    List listMap = await database.rawQuery("SELECT * FROM $pessoaTable");
    List<Pessoa> listaPessoas = List();
    for (Map m in listMap) {
      listaPessoas.add(Pessoa.fromJson(m, true));
    }
    return listaPessoas;
  }

  @override
  Future<int> getNumber() async {
    Database database = await db;
    return Sqflite.firstIntValue(
        await database.rawQuery("SELECT COUNT(*) FROM $pessoaTable"));
  }

  Future deleteAll() async {
    Database database = await db;
    database.delete(pessoaTable);
    //close();
  }

  @override
  Future close() async {
    Database database = await db;
    database.close();
  }
}
