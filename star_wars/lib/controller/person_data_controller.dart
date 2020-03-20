import 'dart:convert';

import 'package:bloc_pattern/bloc_pattern.dart';
import 'package:rxdart/rxdart.dart';
import 'package:starwars/helper/aux_test_connexion.dart';
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
      for (int id = 1; id <= 4; id++) {
        // Por enquanto, pegar somente 4 para teste
        http.Response response =
            await http.get("https://swapi.co/api/people/$id/");
        if (response.statusCode == 200) {
          var decoded = await _getPersonModified(response);
          Person character = Person.fromJson(decoded, identifier: id);
          await datab.savePerson(character);
          people.add(character);
          inData.add(people);
        }
      }
    } else {
      List<Person> people = await datab.getAllPersons();
      inData.add(people);
    }
  }

  _getPersonModified(http.Response resp) async {
    var decoded = json.decode(resp.body); //Para atribuir nome ao planeta natal
    http.Response name = await http.get(decoded["homeworld"]);
    var nameDecoded = json.decode(name.body);
    decoded["homeworld"] = nameDecoded["name"];

    List<dynamic> species = decoded["species"];

    decoded["species"] = "";
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
