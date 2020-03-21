import 'dart:async';

import 'package:bloc_pattern/bloc_pattern.dart';
import 'package:rxdart/rxdart.dart';

import 'package:starwars/model/person_model.dart';

class FavoriteController implements BlocBase {

  PersonDatabase datab = PersonDatabase();

  BehaviorSubject<List<Person>> _favController =
  BehaviorSubject<List<Person>>();
  Stream<List<Person>> get outPutData => _favController.stream;
  Sink<List<Person>> get inPutData => _favController.sink;

  getDbFavorites() async {
    List<Person> people = await datab.favoritesPersons();
    inPutData.add(people);
  }



  @override
  void dispose() {
    _favController.close();
  }

}