import 'dart:convert';

import 'package:bloc_pattern/bloc_pattern.dart';
import 'package:flutter/services.dart';
import 'package:rxdart/rxdart.dart';
import 'package:starwars/helper/aux_test_connexion.dart';
import 'package:starwars/model/favorite_model.dart';
import 'package:starwars/model/person_model.dart';
import 'package:http/http.dart' as http;

class PersonDataController implements BlocBase {
  BehaviorSubject<List<Person>> _dataController =
      BehaviorSubject<List<Person>>();

  Stream<List<Person>> get outData => _dataController.stream;

  Sink<List<Person>> get inData => _dataController.sink;

  PersonDatabase datab = PersonDatabase();

  Future<void> getData() async {
    bool connected = await AuxTestConnexion("https://swapi.co/").connexion();
    if (connected) {
      List<Person> people = List<Person>();
      for (int id = 1; id <= 87; id++) {
        http.Response response =
            await http.get("https://swapi.co/api/people/$id/");
        if (response.statusCode == 200) {
          var decoded = await _getPersonModified(response);
          Person character = Person.fromJson(decoded, identifier: id);
          character = await datab.savePerson(character);
          FavoriteModel().favOnline(character.id); // Para enviar os favoritos quando ficar online
          people.add(character);
          inData.add(people);
        }
      }
    } else {
      List<Person> people = await datab.getAllPersons();
      inData.add(people);
    }
  }

  Future<String> updateList(String id) async {
    String message = await FavoriteModel().setFav(id);
    List<Person> people = await datab.getAllPersons();
    inData.add(people);
    return message;
  }

  _getPersonModified(http.Response resp) async {
    var decoded = json.decode(resp.body); //Para atribuir nome ao planeta natal
    http.Response name = await http.get(decoded["homeworld"]);
    var nameDecoded = json.decode(name.body);
    decoded["homeworld"] = nameDecoded["name"];

    List<dynamic> species = decoded["species"];

    decoded["species"] = "";
    for (int index = 0; index < species.length; index++) {
      // Para atribuir nome das especies
      name = await http.get(species[index]);
      nameDecoded = json.decode(name.body);
      decoded["species"] = "${decoded["species"]} ${nameDecoded["name"]} \n";
    }

    return decoded;
  }

  @override
  void dispose() {
    _dataController.close();
  }
}
