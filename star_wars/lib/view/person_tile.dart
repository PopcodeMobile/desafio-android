import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/painting.dart';
import 'package:flutter/widgets.dart';
import 'package:starwars/model/person.dart';

class PersonTile extends StatelessWidget {
  final Person person;

  PersonTile(this.person);

  @override
  Widget build(BuildContext context) {
    return Container(
      padding: EdgeInsets.symmetric(horizontal: 8),
      child: GestureDetector(
        onTap: () {},
        child: Card(
          elevation: 10,
          child: Padding(
            padding: EdgeInsets.symmetric(vertical: 12, horizontal: 8),
            child: Column(
                children: <Widget>[
                  Text("Nome: ${person.name}"),
                  Text("Altura: ${person.height}"),
                  Text("Gênero: ${person.gender}"),
                  Text("Peso: ${person.mass}")
                ]
            ),
          ),
        ),
      ),
    );
  }
}
