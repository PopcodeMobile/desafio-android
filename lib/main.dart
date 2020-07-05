import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

import 'package:entrevista_pop/utils/app_routes.dart';
import 'package:entrevista_pop/providers/characters.dart';
import 'package:hive/hive.dart';
import 'package:hive_flutter/hive_flutter.dart';

import 'package:entrevista_pop/utils/constants.dart';
import 'package:entrevista_pop/screens/character_detail_screen.dart';
import 'package:entrevista_pop/screens/favorites_screen.dart';
import 'package:entrevista_pop/screens/home_screen.dart';

import 'providers/character.dart';

void main() async {
  await Hive.initFlutter();
  Hive.registerAdapter(CharacterAdapter());
  // await Hive.deleteBoxFromDisk(Constants.favoritesBox);
  // await Hive.deleteBoxFromDisk(Constants.charactersListBox);
  await Hive.openBox<Character>(Constants.favoritesBox);
  await Hive.openBox<Map<dynamic, dynamic>>(Constants.charactersListBox);

  runApp(
    ChangeNotifierProvider(
      create: (_) => Characters(),
      child: StarWarsWikiApp(),
    ),
  );
}

class StarWarsWikiApp extends StatefulWidget {
  @override
  _StarWarsWikiAppState createState() => _StarWarsWikiAppState();
}

class _StarWarsWikiAppState extends State<StarWarsWikiApp> {
  @override
  void dispose() {
    super.dispose();
    Hive.close();
  }

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
