import 'package:http/http.dart' as HTTP;
import 'dart:convert';
import 'dart:async';
import 'package:wikistarwars/model/person_model.dart';
import 'package:wikistarwars/model/planet_model.dart';
import 'package:wikistarwars/model/specie_model.dart';

class SWAPI{

  String _urlBase = "https://swapi.co/api";
  String _paramPerson = '/people/?page=';

  Future<List<PersonModel>> getAllPersons() async {
    List<PersonModel> personList = List();
    for (int i = 1; i <= 9; i++){
      HTTP.Response response = await HTTP.get(
          Uri.encodeFull(_urlBase + _paramPerson +'$i'),
          headers: {"Accept": "application/json"}
          );

      var dataJson = json.decode(response.body);
      try{
        for (int i = 0; i < 10; i++){
          PersonModel personModel = PersonModel();
          personModel.name = dataJson['results'][i]['name'];
          personModel.height = dataJson['results'][i]['height'];
          personModel.mass = dataJson['results'][i]['mass'];
          personModel.hairColor = dataJson['results'][i]['hair_color'];
          personModel.skinColor = dataJson['results'][i]['skin_color'];
          personModel.eyeColor = dataJson['results'][i]['eye_color'];
          personModel.birthYear = dataJson['results'][i]['birth_year'];
          personModel.gender = dataJson['results'][i]['gender'];
          personModel.homeWorld = dataJson['results'][i]['homeworld'];
          personModel.species = dataJson['results'][i]['species'][0];
          personModel.favorite = false;

          personList.add(personModel);
        }
      }catch(e) {
      }
    }
    return personList;

  }

  getPlanet(String url) async {
    String planet = "Sem dado";
    if( url.isNotEmpty || url != null){
      HTTP.Response response = await HTTP.get(url);
      var dataJson = json.decode(response.body);
      planet = dataJson['name'];
    }
    return planet;
  }

  getSpecie(String url) async {
    String specie = "Sem dado";
    if( url.isNotEmpty || url != null){
      HTTP.Response response = await HTTP.get(url);
      var dataJson = json.decode(response.body);
      specie = dataJson['name'];
    }
    return specie;
  }

}