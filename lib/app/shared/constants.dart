import 'package:flutter/material.dart';

const STAR_JEDI = "StarJedi";
const UBUNTU_MONO = "UbuntuMono";

const PrimaryColor = Colors.black;
const SecondaryColor = Colors.yellow;
const TertiaryColor = Colors.white;

const PrimaryBackground = "assets/images/background.png";

String getID(String url) {
  url = url.split("people/")[1];
  url = url.split("/")[0];

  return url;
}

const DB_CHARACTERS = "db_characters";
const TB_CHARACTERS = "tb_characters";
const DB_FAVORITES_ERRORS = "db_favorites_errors";
const TB_FAVORITES_ERRORS = "tb_favorites_errors";

String tableScript(String table) {
  return "CREATE TABLE $table(id INTEGER PRIMARY KEY, name TEXT, height TEXT, mass TEXT, hair_color TEXT, skin_color TEXT, eye_color TEXT, birth_year TEXT, gender TEXT, homeworld TEXT, species TEXT, created TEXT, edited TEXT, url TEXT, favorite INTEGER)";
}
