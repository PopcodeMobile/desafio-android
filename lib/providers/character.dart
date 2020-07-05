import 'package:entrevista_pop/utils/constants.dart';
import 'package:flutter/material.dart';
import 'package:hive/hive.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/widgets.dart';

@HiveType()
class Character with ChangeNotifier {
  String id;
  String name;
  String height;
  String gender;
  String mass;
  String hair_color;
  String skin_color;
  String eye_color;
  String birth_year;
  String homeworld;
  String species;

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

  Future<void> toggleAsFavorite([BuildContext context]) async {
    final Box<Character> locaBox = Hive.box(Constants.favoritesBox);

    if (locaBox.containsKey(id)) {
      locaBox.delete(id);
    } else {
      try {
        locaBox.put(id, this);
        Scaffold.of(context).showSnackBar(SnackBar(
          content: Text('Favorito adicionado com sucesso!'),
          duration: Duration(seconds: 2),
          action: SnackBarAction(
            label: 'OK',
            onPressed: () {},
          ),
        ));
      } catch (error) {}
    }

    notifyListeners();
    return;
  }
}

class CharacterAdapter extends TypeAdapter<Character> {
  @override
  final typeId = 1;

  @override
  Character read(BinaryReader reader) {
    return Character()
      ..id = reader.read()
      ..name = reader.read()
      ..birth_year = reader.read()
      ..mass = reader.read()
      ..eye_color = reader.read()
      ..gender = reader.read()
      ..hair_color = reader.read()
      ..height = reader.read()
      ..homeworld = reader.read()
      ..skin_color = reader.read()
      ..species = reader.read();
  }

  void write(BinaryWriter writer, Character obj) {
    writer.write(obj.id);
    writer.write(obj.name);
    writer.write(obj.birth_year);
    writer.write(obj.mass);
    writer.write(obj.eye_color);
    writer.write(obj.gender);
    writer.write(obj.hair_color);
    writer.write(obj.height);
    writer.write(obj.homeworld);
    writer.write(obj.skin_color);
    writer.write(obj.species);
  }
}
