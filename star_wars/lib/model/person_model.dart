import 'package:path/path.dart';
import 'package:sqflite/sqflite.dart';

final String personTable = "personTable";
final String idString = "id";
final String nameString = "name";
final String massString = "mass";
final String heightString = "height";
final String hairString = "hair_color";
final String skinString = "skin_color";
final String eyeString = "eye_color";
final String birthString = "birth_year";
final String worldString = "homeworld";
final String specieString = "species";
final String genderString = "gender";
final String sentString = "enviado";
final String favString = "favorito";

class Person {
  final String id;
  final String name;
  final String height;
  final String mass;
  final String hairColor;
  final String skinColor;
  final String eyeColor;
  final String birthYear;
  final String gender;
  final String nomePlaneta;
  final String nomeEspecie;
  final String sent;
  final String favorite;

  Person({this.id,
    this.name,
    this.height,
    this.mass,
    this.hairColor,
    this.skinColor,
    this.eyeColor,
    this.birthYear,
    this.gender,
    this.nomePlaneta,
    this.nomeEspecie,
    this.sent,
    this.favorite});

  factory Person.fromJson(Map<String, dynamic> json, int identifier) {
    return Person(
        id: identifier.toString(),
        name: json[nameString],
        mass: json[massString],
        height: json[heightString],
        hairColor: json[hairString],
        skinColor: json[skinString],
        eyeColor: json[eyeString],
        birthYear: json[birthString],
        nomePlaneta: json[worldString],
        nomeEspecie: json[specieString],
        gender: json[genderString],
        sent: json[sentString],
        favorite: json[favString]);
  }

  Map<String, dynamic> toJson() {
    return {
      idString: id,
      nameString: name,
      massString: mass,
      heightString: height,
      hairString: hairColor,
      skinString: skinColor,
      eyeString: eyeColor,
      birthString: birthYear,
      worldString: nomePlaneta,
      specieString: nomeEspecie,
      genderString: gender,
      sentString: sent,
      favString: favorite
    };
  }
}

class PersonDatabase {
  static final PersonDatabase _instance = PersonDatabase.internal();

  factory PersonDatabase() => _instance;

  PersonDatabase.internal();

  Database _db;

  Future<Database> get db async {
    if (_db != null) {
      return _db;
    } else {
      _db = await initDb();
      return _db;
    }
  }

  Future<Database> initDb() async {
    final databasepath = await getDatabasesPath();
    final path = join(databasepath, "person.db");

    return await openDatabase(path, version: 1,
        onCreate: (Database db, int neweVersion) async {
          await db.execute(
              "CREATE TABLE $personTable($idString TEXT PRIMARY KEY, $nameString TEXT, $massString TEXT, $heightString TEXT, $hairString TEXT, $skinString TEXT, $eyeString TEXT, $birthString TEXT, $worldString TEXT, $specieString TEXT, $genderString TEXT, $sentString TEXT, $favString TEXT)");
        });
  }

  Future<Person> savePerson(Person person) async {
    if(await getPerson(person.id) == null){
      Database dbPerson = await db;
      await dbPerson.insert(personTable, person.toJson());
    }
    return person;
  }

  Future<Person> getPerson(String id) async {
    Database dbPerson = await db;
    List<Map> maps = await dbPerson.query(personTable,
        columns: [
          idString,
          nameString,
          massString,
          heightString,
          hairString,
          skinString,
          eyeString,
          birthString,
          worldString,
          specieString,
          genderString,
          sentString
        ],
        where: "$idString = ?",
        whereArgs: [id]);
    if (maps.length > 0) {
      return Person.fromJson(maps.first, int.parse(id));
    } else {
      return null;
    }
  }

  Future<int> updatePerson(Person person) async {
    Database dbPerson = await db;
    return await dbPerson.update(personTable,
        person.toJson(),
        where: "$idString = ?",
        whereArgs: [person.id]);
  }

  Future<List> getAllPersons() async {
    Database dbPerson = await db;
    int count = 1;
    List listMap = await dbPerson.rawQuery("SELECT * FROM $personTable");
    List<Person> listPerson = List();
    for(Map m in listMap){
      count++;
      listPerson.add(Person.fromJson(m,count));
    }
    return listPerson;
  }

  Future close() async {
    Database dbPerson = await db;
    dbPerson.close();
  }

}
