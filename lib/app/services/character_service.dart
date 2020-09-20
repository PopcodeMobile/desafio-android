import 'dart:convert';

import 'package:bloc_pattern/bloc_pattern.dart';
import 'package:http/http.dart' as http;
import 'package:wiki_star_wars/app/models/character_model.dart';
import 'package:wiki_star_wars/app/models/people_model.dart';
import 'package:wiki_star_wars/app/services/character_local_service.dart';
import 'package:wiki_star_wars/app/shared/constants.dart';

class CharacterService extends Disposable {
  Future<PeopleModel> getCharacters(String url) async {
    try {
      if (url == null) url = "https://swapi.dev/api/people/";

      http.Response response = await http.get(url);

      if (response.statusCode == 200) {
        var json = jsonDecode(response.body);

        PeopleModel people = PeopleModel.fromJson(json);

        CharacterModel characterModel = CharacterModel();
        CharacterLocalService characterLS = CharacterLocalService();

        List<dynamic> body = json["results"];
        List<CharacterModel> characters = body.map<CharacterModel>((character) {
          if (character["species"].length > 0) {
            character["species"] = character["species"][0];
          } else {
            character["species"] = "-";
          }
          characterModel = CharacterModel.fromJson(character);
          characterModel.id = int.parse(getID(characterModel.url));
          characterModel.favorite = 'false';
          characterLS.add(characterModel);
          return characterModel;
        }).toList();

        characters = await checkFavorites(characters);

        people.results = characters;

        return people;
      } else {
        throw "Error";
      }
    } catch (e) {
      print(e.toString());
      throw e;
    }
  }

  Future<List<CharacterModel>> checkFavorites(
      List<CharacterModel> characters) async {
    CharacterLocalService characterLS = CharacterLocalService();

    for (int i = 0; i < characters.length; i++) {
      if (await characterLS.checkFavorite(characters[i])) {
        characters[i].favorite = 'true';
        characterLS.add(characters[i]);
      }
    }

    return characters;
  }

  Future<String> getHomeworld(CharacterModel character) async {
    try {
      if (character.homeworld.contains('http://') ||
          character.homeworld.contains('https://')) {
        http.Response response = await http.get(character.homeworld);
        if (response.statusCode == 200) {
          var json = jsonDecode(response.body);
          return json["name"];
        }
      }
      return '-';
    } catch (e) {
      print(e);
      return '-';
    }
  }

  Future<String> getSpecie(CharacterModel character) async {
    try {
      if (character.species.contains('http://') ||
          character.species.contains('https://')) {
        http.Response response = await http.get(character.species);
        if (response.statusCode == 200) {
          var json = jsonDecode(response.body);
          return json["name"];
        }
      }
      return '-';
    } catch (e) {
      print(e);
      return '-';
    }
  }

  //dispose will be called automatically
  @override
  void dispose() {}
}
