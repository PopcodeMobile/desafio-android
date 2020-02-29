import 'package:flutter/material.dart';
import 'package:get_it/get_it.dart';
import 'package:star_wars_wiki/controllers/character_controller.dart';
import 'package:star_wars_wiki/views/characters_list.dart';

class Body extends StatelessWidget {
  final controller = GetIt.I.get<CharacterController>();
  @override
  Widget build(BuildContext context) {
    return FutureBuilder<Map>(
      future: controller.getData(),
      builder: (context, snapshot) {
        switch(snapshot.connectionState){
          case ConnectionState.none:
          case ConnectionState.waiting:
            return Center(
              child: CircularProgressIndicator(),
            );
          default:
            if(snapshot.hasError){
              return Center(
                child: Text('Error while loading data. Try again later.'),
              );
            } else {
              return CharactersList(snapshot);
            }
        }
      }
    );
  }
}