import 'package:flutter/material.dart';
import 'package:get_it/get_it.dart';
import 'package:star_wars_wiki/controllers/character_controller.dart';
import 'package:star_wars_wiki/home.dart';

void main() {
  GetIt getIt = GetIt.I;
  getIt.registerSingleton<CharacterController>(CharacterController());
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'Star Wars Wiki',
      theme: ThemeData(
        accentColor: Colors.yellow[400],
        primaryColor: Colors.black,
      ),
      home: Home(),
    );
  }
}
