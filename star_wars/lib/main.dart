import 'package:bloc_pattern/bloc_pattern.dart';
import 'package:flutter/material.dart';
import 'package:starwars/controller/favorite_controller.dart';
import 'package:starwars/controller/person_data_controller.dart';
import 'package:starwars/view/home.dart';

void main() {
  runApp(BlocProvider(
      bloc: PersonDataController(),
      child: BlocProvider(
        bloc: FavoriteController(),
        child: MaterialApp(
          title: "Star Wars Wiki",
          debugShowCheckedModeBanner: false,
          home: Home(),
        ),
      )));
}
