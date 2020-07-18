import 'package:entrevista_popcode/helpers/database.dart';
import 'package:entrevista_popcode/models/pessoa.dart';
import 'package:sqflite/sqflite.dart';

class PessoaHelper extends DataBaseHelper<Pessoa> {
  static final String tableName = 'tbPersonage';
  static final String idColumn = 'id';
  static final String nameColumn = 'name';
  static final String heightColumn = 'height';
  static final String masColumn = 'mass';
  static final String hairColorColumn = 'hairColor';
  static final String skinColorColumn = 'skinColor';
  static final String eyeColorColumn = 'eyeColor';
  static final String birthYearColumn = 'birthYear';
  static final String genderColumn = 'gender';
  static final String homeWorldColumn = 'homeWorld';
  static final String specieColumn = 'specie';

  PessoaHelper._privateConstructor();
  static final PessoaHelper instance = PessoaHelper._privateConstructor();

  @override
  Future<Pessoa> savePeople(Pessoa people) async {
    Database db = await database;
    Pessoa personagem = await getFirst(people.name);
    String nomePlaneta = "";
    String nomeEspecie = "";

    if (personagem == null) {
      if (people.homeWorld.isNotEmpty || (people.species != null)) {
        try {
          if (people.homeWorld.isNotEmpty)
            nomePlaneta = await Requisicao().getNomePlaneta(people.homeWorld);
          if (people.species != null && people.species.length > 0)
            nomeEspecie =
                await Requisicao().getNomeEspecie(people.species.first);
        } catch (e) {
          throw new Exception("Não foi possível realizar a requisição!");
        }
      }

      people.homeWorld = nomePlaneta;
      people.specie = nomeEspecie;

      people.id = await db.insert(tableName, people.toMap());
    }
    return people;
  }

  @override
  Future<Pessoa> getFirst(String nome) async {
    Database db = await database;
    List<Map> personagem = await db
        .rawQuery("SELECT * FROM $tableName WHERE $nameColumn LIKE '$nome'");
    if (personagem != null && personagem.length > 0) {
      return Pessoa.fromJson(personagem.first);
    } else {
      return null;
    }
  }

  @override
  Future<List> getAll() async {
    Database db = await database;
    List listMap = await db.rawQuery("SELECT * FROM $tableName");
    List<Pessoa> listaPessoas = List();
    for (Map m in listMap) {
      listaPessoas.add(Pessoa.fromJson(m));
    }
    return listaPessoas;
  }

  @override
  Future<int> getNumber() async {
    Database db = await database;
    return Sqflite.firstIntValue(
        await db.rawQuery("SELECT COUNT(*) FROM $tableName"));
  }
}
