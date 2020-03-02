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
          PersonModel personModel = PersonModel(
            name: dataJson['results'][i]['name'],
            height: dataJson['results'][i]['height'],
            mass: dataJson['results'][i]['mass'],
            hairColor: dataJson['results'][i]['hair_color'],
            skinColor: dataJson['results'][i]['skin_color'],
            eyeColor: dataJson['results'][i]['eye_color'],
            birthYear: dataJson['results'][i]['birth_year'],
            gender: dataJson['results'][i]['gender'],
            homeWorld: dataJson['results'][i]['homeworld'],
            species: dataJson['results'][i]['species'][0]
          );
          personList.add(personModel);
        }
      }catch(e) {
      }
    }
    return personList;

  }

  Future<String> getPlanet(String url) async {
    String planet = "Sem dado";
    if( url.isNotEmpty || url != null){
      HTTP.Response response = await HTTP.get(url);
      var dataJson = json.decode(response.body);
      planet = dataJson['name'];
    }
    return planet;
  }

  Future<String> getSpecie(String url) async {
    String specie = "Sem dado";
    if( url.isNotEmpty || url != null){
      HTTP.Response response = await HTTP.get(url);
      var dataJson = json.decode(response.body);
      specie = dataJson['name'];
    }
    return specie;
  }

}