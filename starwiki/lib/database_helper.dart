import 'package:sqflite/sqflite.dart';
import 'package:path/path.dart';


final String personTable = 'personTable';
final String personName = 'name';
final String personHeight = 'height';
final String personMass = 'mass';
final String personHairColor = 'hair_color';
final String personSkinColor = 'skin_color';
final String personEyeColor = 'eye_color';
final String personBirthYear = 'birth_year';
final String personGender = 'gender';
final String personPlanet = 'homeworld';
final String personSpecies = 'species';
final String personIsFav = 'isFav';
final String personFavLater = 'favLater';

class People{
  String name;
  String height;
  String mass;
  String hairColor;
  String skinColor;
  String eyeColor;
  String birthYear;
  String gender;
  String planet;
  String species;
  String isFav;
  String favLater;

  People({this.name, this.height, this.mass, this.hairColor, this.skinColor,
  this.eyeColor, this.birthYear, this.gender, this.planet, this.species, this.isFav, this.favLater});

  People.fromMap(Map<String, dynamic> map)
        : name = map[personName],
          height = map[personHeight],
          mass = map[personMass],
          hairColor = map[personHairColor],
          skinColor = map[personSkinColor],
          eyeColor = map[personEyeColor],
          birthYear = map[personBirthYear],
          gender = map[personGender],
          planet = map[personPlanet],
          species = map[personSpecies].isEmpty ? "" : map[personSpecies][0];

  Map<String, String> toMap() {
    return {
      personName : name,
      personHeight : height,
      personMass : mass,
      personHairColor : hairColor,
      personSkinColor : skinColor,
      personEyeColor : eyeColor,
      personBirthYear : birthYear,
      personGender : gender,
      personPlanet : planet,
      personSpecies : species,
      personIsFav : isFav,
    };
  }
}

class Page {
  List<People> results;

  Page({this.results});

  Page.fromJson(Map<String, dynamic> json)
      : results = List.from(json['results'])
      .map((people) => People.fromMap(people))
      .toList();
}

class Planet {
  String name;

  Planet({this.name});

  Planet.fromJson(Map<String, dynamic> json)
      : name = json['name'];
}

class Species {
  String name;

  Species({this.name});

  Species.fromJson(Map<String, dynamic> json)
      : name = json['name'];
}

class DatabaseHelper {

  static DatabaseHelper _databaseHelper;
  static Database _database;
  static final _databaseName = "swWiki.db";
  static final _databaseVersion = 4;

  DatabaseHelper._createInstance();

  factory DatabaseHelper() {
    if (_databaseHelper == null) {
      _databaseHelper = DatabaseHelper._createInstance();
    }
    return _databaseHelper;
  }

  Future<Database> get database async {
    if (_database == null) {
      _database = await initializeDatabase();
    }
    return _database;
  }

  Future<Database> initializeDatabase() async {
    //Directory directory = await getApplicationDocumentsDirectory();
    String path = join(await getDatabasesPath(), _databaseName);
    var swWikiDatabase = await openDatabase(path, version: _databaseVersion, onCreate: _createDb, onUpgrade: _upgradeDb);
    return swWikiDatabase;
  }

  void _createDb(Database db, int newVersion) async {
    await db.execute('CREATE TABLE $personTable($personName TEXT PRIMARY KEY, '+
      '$personHeight TEXT, $personMass TEXT, $personHairColor TEXT, $personSkinColor TEXT, $personEyeColor TEXT, '+
      '$personBirthYear TEXT, $personGender TEXT, $personPlanet TEXT, $personSpecies TEXT, $personIsFav TEXT, $personFavLater TEXT)');
  }

  void _upgradeDb(Database db, int oldVersion, int newVersion) async {
    if (oldVersion < newVersion){
      await db.execute("DROP TABLE favoritePersonTable");
      await db.execute('DROP TABLE $personTable');
      await db.execute('CREATE TABLE $personTable($personName TEXT PRIMARY KEY, '+
      '$personHeight TEXT, $personMass TEXT, $personHairColor TEXT, $personSkinColor TEXT, $personEyeColor TEXT, '+
      '$personBirthYear TEXT, $personGender TEXT, $personPlanet TEXT, $personSpecies TEXT, $personIsFav TEXT, $personFavLater TEXT)');
    }
  }

  Future<List<People>> getPeopleList() async {
    Database db = await this.database;
    List<Map<String, dynamic>> maps = await db.query('$personTable');
    List<People> peopleList = new List<People>();
    People person;
    for (var row in maps) {
      person = new People(
        name: row['$personName'],
        height: row['$personHeight'],
        mass: row['$personMass'],
        hairColor: row['$personHairColor'],
        skinColor: row['$personSkinColor'],
        eyeColor: row['$personEyeColor'],
        birthYear: row['$personBirthYear'],
        gender: row['$personGender'],
        planet: row['$personPlanet'],
        species: row['$personSpecies'],
        isFav: row['$personIsFav'],
        favLater: row['$personFavLater']);
      peopleList.add(person);
    }
    return peopleList;    
  }

  Future<void> createOrUpdatePeople(List<People> people) async {
    Database db = await this.database;
    Batch batch = db.batch();
    List<Map> result;
    for (People person in people) {
      result = await db.query('$personTable', where: '$personName = ?', whereArgs: [person.name]);
      if (result.isEmpty) {
        batch.rawInsert("INSERT INTO $personTable($personName,$personHeight,$personMass,"+
        "$personHairColor,$personSkinColor,$personEyeColor,$personBirthYear,$personGender,"+
        "$personPlanet,$personSpecies,$personIsFav,$personFavLater) "+
        "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
        [person.name, person.height, person.mass, person.hairColor, person.skinColor, person.eyeColor,
        person.birthYear, person.gender, person.planet, person.species, 'false', 'false']);
      } else {
        batch.rawUpdate('''
        UPDATE $personTable
        SET $personHeight = ?, $personMass = ?, $personHairColor = ?, $personSkinColor = ?, $personEyeColor = ?,
        $personBirthYear = ?, $personGender = ?, $personPlanet = ?, $personSpecies = ?
        WHERE $personName = ?        
        ''',
        [person.height, person.mass, person.hairColor, person.skinColor, person.eyeColor,
        person.birthYear, person.gender, person.planet, person.species, person.name]);
      }
    }
    await batch.commit();
  }

  Future<void> addFavoritePeople(People person) async {
    Database db = await this.database;
    await db.update('$personTable', {'$personIsFav': 'true', '$personFavLater': 'false'},
    where: '$personName = ?', whereArgs: [person.name]);
  }

  Future<void> removeFavoritePeople(People person) async {
    Database db = await this.database;
    await db.update("$personTable", {'$personIsFav': 'false'},
    where: "$personName = ?", whereArgs: [person.name]);
  }

  Future<void> saveFavoriteForLater(People person, String id) async {
    Database db = await this.database;
    await db.update('$personTable', {'$personFavLater': id},
    where: '$personName = ?', whereArgs: [person.name]);
  }

  Future<List<People>> getFavoriteList() async {
    Database db = await this.database;
    List<Map<String, dynamic>> maps = await db.query('$personTable', where: '$personIsFav = ?', whereArgs: ['true']);
    List<People> peopleList = new List<People>();
    People person;
    for (var row in maps) {
      person = new People(
        name: row['$personName'],
        height: row['$personHeight'],
        mass: row['$personMass'],
        hairColor: row['$personHairColor'],
        skinColor: row['$personSkinColor'],
        eyeColor: row['$personEyeColor'],
        birthYear: row['$personBirthYear'],
        gender: row['$personGender'],
        planet: row['$personPlanet'],
        species: row['$personSpecies'],
        isFav: row['$personIsFav'],
        favLater: row['$personFavLater'],
      );
      peopleList.add(person);
    }
    return peopleList;
  } 
}