import 'package:flutter/foundation.dart';

class Character with ChangeNotifier {
  final String id;
  final String name;
  final String height;
  final String gender;
  final String mass;
  final String hair_color;
  final String skin_color;
  final String eye_color;
  final String birth_year;
  final String homeworld;
  final String species;

  bool isFavorite = false;

  Character({
    this.id,
    this.name,
    this.height,
    this.gender,
    this.mass,
    this.hair_color,
    this.skin_color,
    this.eye_color,
    this.birth_year,
    this.homeworld,
    this.species,
  });

  Future<void> toggleAsFavorite() async {
    isFavorite = !isFavorite;

    notifyListeners();
    return;
  }
}
