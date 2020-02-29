import 'package:flutter/material.dart';
import 'package:star_wars_wiki/views/body.dart';

class Home extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Star Wars Wiki'),
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