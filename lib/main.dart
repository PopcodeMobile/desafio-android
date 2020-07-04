import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

import 'package:entrevista_pop/utils/app_routes.dart';
import 'package:entrevista_pop/providers/characters.dart';

import 'package:entrevista_pop/screens/character_detail_screen.dart';
import 'package:entrevista_pop/screens/favorites_screen.dart';
import 'package:entrevista_pop/screens/home_screen.dart';

void main() {
  runApp(
    ChangeNotifierProvider(
      create: (_) => Characters(),
      child: StarWarsWikiApp(),
    ),
  );
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
        AppRoutes.FAVORITES: (ctx) => FavoritesScreen(),
        AppRoutes.CHARACTER_DETAIL: (ctx) => CharacterDetailScreen(),
      },
    );
  }
}
