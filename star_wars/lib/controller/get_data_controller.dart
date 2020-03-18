import 'dart:convert';

import 'package:bloc_pattern/bloc_pattern.dart';
import 'package:rxdart/rxdart.dart';
import 'package:starwars/model/person.dart';
import 'package:http/http.dart' as http;

class GetDataController implements BlocBase {

  BehaviorSubject<List<Person>> _dataController =
      BehaviorSubject<List<Person>>();
  Stream<List<Person>> get outData => _dataController.stream;
  Sink<List<Person>> get inData => _dataController.sink;

  Future<void> getData() async {
    List<Person> people = List<Person>();
    for (int id = 1; id <= 1; id++) { // Por enquanto, pegar somente 1 para teste
      http.Response response =
          await http.get("https://swapi.co/api/people/$id/");
      if (response.statusCode == 200) {
        var decoded = json.decode(response.body);
        people.add(Person.fromJson(decoded, id));
        print(Person.fromJson(decoded,id).id);
        print(Person.fromJson(decoded,id).name);
        print(Person.fromJson(decoded,id).height);
        print(Person.fromJson(decoded,id).mass);
        print(Person.fromJson(decoded,id).hairColor);
        print(Person.fromJson(decoded,id).skinColor);
        print(Person.fromJson(decoded,id).eyeColor);
        print(Person.fromJson(decoded,id).birthYear);
        print(Person.fromJson(decoded,id).gender);
        print(Person.fromJson(decoded,id).nomePlaneta);
        print(Person.fromJson(decoded,id).nomeEspecie);
        inData.add(people);
      }
    }
    print(people.length);
  }

  @override
  void dispose() {
    _dataController.close();
  }
}
