import 'package:flutter/material.dart';
import 'package:get_it/get_it.dart';
import 'package:star_wars_wiki/controllers/character_controller.dart';
import 'package:star_wars_wiki/views/home_page/body.dart';

class Home extends StatelessWidget {
  final controller = GetIt.I.get<CharacterController>();
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
        actions: <Widget>[
          IconButton(
            icon: Icon(Icons.stars), 
            onPressed: () {
              controller.refresh();
            }
          ),
        ],
      ),
      body: Body(),
    );
  }
}
