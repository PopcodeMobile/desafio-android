import 'package:flutter/material.dart';
import 'package:star_wars_wiki/views/body.dart';

class Home extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Row(
          children: <Widget>[
            Image.asset(
              'images/logo.png',
              height: 50.0,
              ),
            Text('Wiki')
          ],
        ),
        elevation: 1.0,
        actions: <Widget>[
          IconButton(
            icon: Icon(Icons.search),
            tooltip: 'Search',
            onPressed: (){},
          ),
        ],
      ),
      body: Body(),
    );
  }
}