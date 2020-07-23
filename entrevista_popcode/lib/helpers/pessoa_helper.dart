import 'package:entrevista_popcode/helpers/database.dart';
import 'package:entrevista_popcode/helpers/requisicao_API.dart';
import 'package:entrevista_popcode/models/pessoa.dart';
import 'package:sqflite/sqflite.dart';

class PessoaHelper extends DataBaseHelper<Pessoa> {
  static final String tableName = 'tbPersonagem';
  static final String idColumn = 'id';
  static final String nameColumn = 'name';
  static final String heightColumn = 'height';
  static final String massColumn = 'mass';
  static final String hairColorColumn = 'hair_color';
  static final String skinColorColumn = 'skin_color';
  static final String eyeColorColumn = 'eye_color';
  static final String birthYearColumn = 'birth_year';
  static final String genderColumn = 'gender';
  static final String homeWorldColumn = 'homeworld';
  static final String specieColumn = 'species';

  static final PessoaHelper _instance = PessoaHelper.getInstance();

  factory PessoaHelper() => _instance;
  PessoaHelper.getInstance();

  //PessoaHelper._privateConstructor();
  //static final PessoaHelper _instance = PessoaHelper._privateConstructor();

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
            nomePlaneta = await RequisicaoApi().getPlaneta(people.homeWorld);
          if (people.species != null && people.species.length > 0)
            nomeEspecie =
                await RequisicaoApi().getEspecie(people.species.first);
        } catch (e) {
          throw new Exception("Não foi possível realizar a requisição!");
        }
      }

      people.homeWorld = nomePlaneta;
      people.specie = nomeEspecie;

      people.id = await db.insert(tableName, people.toJson());
    }
    return people;
  }

  @override
  Future<Pessoa> getFirst(String nome) async {
    Database db = await database;
    List<Map> personagem = await db
        .rawQuery("SELECT * FROM $tableName WHERE $nameColumn LIKE '$nome'");
    if (personagem != null && personagem.length > 0) {
      return Pessoa.fromJson(personagem.first, true);
    } else {
      return null;
    }
  }

  @override
  Future<List> getAll() async {
    Database db = await database;
    List lista = await db.rawQuery("SELECT * FROM $tableName");
    List<Pessoa> personagens = List();
    for (Map m in lista) {
      personagens.add(Pessoa.fromJson(m, true));
    }
    // List<Pessoa> personagens = lista.isNotEmpty
    //     ? lista.map((e) => Pessoa.fromJson(e, true)).toList()
    //     : [];

    return personagens;
  }

  @override
  Future<int> getRowCount() async {
    Database db = await database;
    return Sqflite.firstIntValue(
        await db.rawQuery("SELECT COUNT(*) FROM $tableName"));
  }
}
