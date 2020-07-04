import 'package:entrevista_pop/screens/home_screen.dart';
import 'package:entrevista_pop/utils/app_routes.dart';
import 'package:flutter/material.dart';

void main() {
  runApp(StarWarsWikiApp());
}

class StarWarsWikiApp extends StatelessWidget {
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Star Wars Wiki',
      theme: ThemeData(
        primaryColor: Colors.black,
        accentColor: Colors.yellow[600],
      ),
      routes: {
        AppRoutes.HOME: (ctx) => HomeScreen(),
      },
    );
  }
}
