import 'package:flutter/material.dart';

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
                print('oi');
              },
              child: ListTile(
                leading: Icon(Icons.people),
                title: Text('Personagens'),
                subtitle: Text("Todos os personagens"),
              ),
            ),
            InkWell(
              onTap: () {
                print('oi');
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
