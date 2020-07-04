import 'package:entrevista_pop/widgets/characters_list.dart';
import 'package:flutter/material.dart';

import 'package:entrevista_pop/widgets/app_drawer.dart';

class HomeScreen extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return SafeArea(
      child: Scaffold(
        appBar: AppBar(
          title: Text(
            "Star Wars Wiki",
            style: TextStyle(color: Theme.of(context).accentColor),
            textAlign: TextAlign.center,
          ),
        ),
        body: CharactersList(),
        drawer: ApplicationDrawer(),
      ),
    );
  }
}
