

import 'package:entrevista_android/data/character_repository.dart';
import 'package:entrevista_android/data/sqlite_database.dart';

class DatabaseFactory{


   static CharacterRepository create(){
    return SQLiteDatabase.instance;
  }
}