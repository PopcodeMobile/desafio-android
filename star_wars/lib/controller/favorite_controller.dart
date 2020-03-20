import 'dart:async';

import 'package:bloc_pattern/bloc_pattern.dart';

import 'package:starwars/model/person_model.dart';

class FavoriteController implements BlocBase {

  StreamController<Person> _favController =
  StreamController<Person>();
  Stream<Person> get outData => _favController.stream;
  Sink<Person> get inData => _favController.sink;



  @override
  void dispose() {
    _favController.close();
  }

}