import 'package:http/http.dart' as HTTP;
import 'dart:convert';
import 'dart:async';
import 'package:wikistarwars/model/person_model.dart';
import 'package:wikistarwars/helper/person_helper.dart';

class SWAPI{

  String _urlBase = "https://swapi.co/api";
  String _paramPerson = '/people/?page=';

  var _db = PersonHelper();

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
          personModel.url = dataJson['results'][i]['url'];
          personModel.favorite = false;
          await _db.savePerson(personModel);
          personList.add(personModel);
        }
      }catch(e) {
      }
    }
    return personList;

  }

}