import 'package:flutter/material.dart';
import 'package:wiki_star_wars/app/screens/home/home_module.dart';
import 'package:wiki_star_wars/app/shared/constants.dart';

class AppWidget extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'Wiki Star Wars',
      theme: ThemeData(
        primarySwatch: SecondaryColor,
        primaryColor: PrimaryColor,
      ),
      home: HomeModule(),
    );
  }
}
