import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class MenuDrawer extends StatefulWidget {
  MenuDrawer({Key key}) : super(key: key);

  _MenuDrawerState createState() => _MenuDrawerState();
}

class _MenuDrawerState extends State<MenuDrawer> {
  @override
  Widget build(BuildContext context) {
    return ListView(
      children: <Widget>[
        UserAccountsDrawerHeader(
          accountName: Text("Wiki Star Wars", style: TextStyle(fontSize: 20)),
          accountEmail: null,
        ),
        ListTile(
          leading: Icon(Icons.home),
          title: Text('Todos Personagens'),
          trailing: Icon(Icons.arrow_forward),
          onTap: () {
            Navigator.pushNamed(context, '/');
          },
        ),
        ListTile(
          leading: Icon(Icons.star),
          title: Text('Favoritos'),
          trailing: Icon(Icons.arrow_forward),
          onTap: () {
            // Update the state of the app.
            // ...
          },
        ),
      ],
    );
  }
}
