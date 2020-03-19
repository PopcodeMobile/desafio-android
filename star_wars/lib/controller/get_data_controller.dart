import 'dart:convert';

import 'package:bloc_pattern/bloc_pattern.dart';
import 'package:rxdart/rxdart.dart';
import 'package:starwars/controller/aux_get_data.dart';
import 'package:starwars/model/person_model.dart';
import 'package:http/http.dart' as http;

class GetDataController implements BlocBase {

  BehaviorSubject<List<Person>> _dataController =
      BehaviorSubject<List<Person>>();
  Stream<List<Person>> get outData => _dataController.stream;
  Sink<List<Person>> get inData => _dataController.sink;

  Future<void> getData() async {
    List<Person> people = List<Person>();
    for (int id = 1; id <= 2; id++) { // Por enquanto, pegar somente 2 para teste
      http.Response response =
          await http.get("https://swapi.co/api/people/$id/");
      if (response.statusCode == 200) {
        var decoded = await AuxGetData(response).getPersonModified();
        people.add(Person.fromJson(decoded, id));
        inData.add(people);
      }
    }
  }

  @override
  void dispose() {
    _dataController.close();
  }
}
