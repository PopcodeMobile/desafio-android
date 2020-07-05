import 'package:flutter/material.dart';
import 'package:hive/hive.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/widgets.dart';

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

  Future<void> toggleAsFavorite([Box boxInstance, BuildContext context]) async {
    isFavorite = !isFavorite;

    if (!isFavorite && boxInstance.containsKey(id)) {
      boxInstance.delete(id);
    } else {
      boxInstance.put(id, id);
      Scaffold.of(context).showSnackBar(SnackBar(
        content: Text('Favorito adicionado com sucesso!'),
        action: SnackBarAction(
          label: 'OK',
          onPressed: () {},
        ),
      ));
    }

    notifyListeners();
    return;
  }
}
