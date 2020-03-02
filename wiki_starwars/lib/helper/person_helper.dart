import 'package:wikistarwars/model/person_model.dart';
import 'package:sqflite/sqflite.dart';
import 'package:path/path.dart';
import 'package:wikistarwars/constants/constants.dart' as Constants;

class PersonHelper {
  static final PersonHelper _personHelper = PersonHelper._internal();

  Database _db;

  factory PersonHelper(){
    return _personHelper;
  }

  PersonHelper._internal();

  get db async {
    return _db != null ? _db : await initializateDB();
  }

  _onCreate(Database db, int version) async {
    String sql = "CREATE TABLE ${Constants.nameTable}" +
        "(id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR UNIQUE, height VARCHAR," +
            "mass VARCHAR, hair_color VARCHAR, skin_color VARCHAR, eye_color VARCHAR,"+
                "birth_year VARCHAR, gender VARCHAR, homeworld VARCHAR, species VARCHAR," +
                    "favorite VARCHAR, url VARCHAR UNIQUE)";
    await db.execute(sql);
  }

  initializateDB() async {
    final path = await getDatabasesPath();
    final pathDB = join(path, Constants.nameDatabase);

    var database = await openDatabase(
      pathDB,
      version: 1,
      onCreate: _onCreate
    );

    return database;
  }

  Future<int> savePerson(PersonModel personModel) async {
    var database = await db;
    int id = await database.insert(Constants.nameTable, personModel.toMap());
    return id;
  }

  getChar() async {
    var database = await db;

    String sql = "SELECT * FROM ${Constants.nameTable}";
    List listChar = await database.rawQuery(sql);
    return listChar;
  }

  Future<int> setFavorite(PersonModel personModel) async {
    var database = await db;
    return await database.update(
      Constants.nameTable,
      personModel.toMap(),
      where: "id = ?", whereArgs: [personModel.id]
    );
  }



}