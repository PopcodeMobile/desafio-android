import 'package:entrevista_popcode_flutter/helpers/helperBase.dart';
import 'package:entrevista_popcode_flutter/helpers/requisicao.dart';
import 'package:entrevista_popcode_flutter/models/pessoa.dart';
import 'package:sqflite/sqflite.dart';

//TABELA DOS PERSONAGENS
class HelperPessoa extends HelperBase<Pessoa> {
  static final String pessoaTable = "tb_pessoa";
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
  static final String requestFailedColumn = "requestFailedColumn";
  static final HelperPessoa _instance = HelperPessoa.getInstance();

  factory HelperPessoa() => _instance;
  HelperPessoa.getInstance();

  @override
  Future<Pessoa> save(Pessoa pessoa) async {
    Database database = await db;
    Pessoa personagem = await getFirst(pessoa.name);
    String nomePlaneta = "";
    String nomeEspecie = "";

    if (personagem == null) {
      if (pessoa.homeworld.isNotEmpty || (pessoa.species != null)) {
        try {
          if (pessoa.homeworld.isNotEmpty)
            nomePlaneta = await Requisicao().getNomePlaneta(pessoa.homeworld);
          if (pessoa.species != null && pessoa.species.length > 0)
            nomeEspecie = await Requisicao().getNomeEspecie(pessoa.species.first);
        } catch (e) {
          throw new Exception("Não foi possível realizar a requisição!");
        }
      }

      pessoa.homeworld = nomePlaneta;
      pessoa.specie = nomeEspecie;

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

  Future<int> delete(Pessoa pessoa) async {
    Database database = await db;
    return await database
        .delete(pessoaTable, where: "$nameColumn = ?", whereArgs: [pessoa.name]);
  }

  @override
  Future<int> update(Pessoa pessoa) async {
    Database database = await db;
    int atualizou = await database.rawUpdate("UPDATE $pessoaTable SET $nameColumn = '${pessoa.name}', "
    "$heightColumn = '${pessoa.height}', $massColumn = '${pessoa.mass}',  $hairColorColumn = '${pessoa.hairColor}', "
    "$skinColorColumn = '${pessoa.skinColor}',  $eyeColorColumn = '${pessoa.eyeColor}', $birthYearColumn = '${pessoa.birthYear}', "
    "$genderColumn = '${pessoa.gender}', $homeWorldColumn = '${pessoa.homeworld}', $specieColumn = '${pessoa.specie}', $isFavoriteColumn = ${pessoa.isFavorite} "
    "WHERE $nameColumn = '${pessoa.name}'");
    return atualizou;
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
