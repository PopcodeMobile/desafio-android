import 'package:entrevista_android/blocs/character-bloc.dart';
import 'package:entrevista_android/ui/screens/character-details.dart';
import 'package:entrevista_android/ui/screens/character-search.dart';
import 'package:entrevista_android/ui/shared/theme.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

import 'ui/screens/character-feed.dart';


void main() => runApp(
  MultiProvider(
      providers: [
        ChangeNotifierProvider(create: (context) => (CharacterBloc())),
      ],
      child: MyApp(),
    ),
);

class MyApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: appTheme,
      initialRoute: '/',
      routes: {
        '/': (context) => CharacterFeed(),
        '/details': (context) => CharacterDetails(),
        '/search': (context) => CharacterSearch(),
      },
    );
  }
}


