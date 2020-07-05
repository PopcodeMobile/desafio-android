import 'package:entrevista_pop/widgets/favorite_characters_list.dart';
import 'package:flutter/material.dart';

import 'package:entrevista_pop/widgets/app_drawer.dart';

class FavoritesScreen extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return SafeArea(
      child: Scaffold(
        appBar: AppBar(
          title: Text(
            "Lista de Favoritos",
            style: TextStyle(color: Theme.of(context).accentColor),
            textAlign: TextAlign.center,
          ),
        ),
        body: FavoriteCharactersList(),
        drawer: ApplicationDrawer(),
      ),
    );
  }
}
