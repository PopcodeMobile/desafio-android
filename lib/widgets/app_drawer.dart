import 'package:entrevista_pop/providers/characters.dart';
import 'package:flutter/material.dart';

import 'package:entrevista_pop/utils/app_routes.dart';
import 'package:provider/provider.dart';

class ApplicationDrawer extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Drawer(
      elevation: 10.0,
      child: Container(
        padding: EdgeInsets.symmetric(
          vertical: 15,
          horizontal: 10,
        ),
        child: ListView(
          children: <Widget>[
            InkWell(
              onTap: () {
                Navigator.of(context).pushReplacementNamed(AppRoutes.HOME);
              },
              child: ListTile(
                leading: Icon(Icons.people),
                title: Text('Personagens'),
                subtitle: Text("Todos os personagens"),
              ),
            ),
            InkWell(
              onTap: () {
                Provider.of<Characters>(context, listen: false).setFavorites();
                Navigator.of(context).pushReplacementNamed(AppRoutes.FAVORITES);
              },
              child: ListTile(
                leading: Icon(Icons.star),
                title: Text('Favoritos'),
                subtitle: Text("Seus personagens favoritos"),
              ),
            )
          ],
        ),
      ),
    );
  }
}
