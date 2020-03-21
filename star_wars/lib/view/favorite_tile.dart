
import 'package:bloc_pattern/bloc_pattern.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/painting.dart';
import 'package:flutter/widgets.dart';
import 'package:starwars/controller/favorite_controller.dart';
import 'package:starwars/controller/person_data_controller.dart';
import 'package:starwars/model/person_model.dart';
import 'package:starwars/view/person_view.dart';

class FavoriteTile extends StatelessWidget {
  final Person person;

  FavoriteTile(this.person);


  @override
  Widget build(BuildContext context) {

    return Container(
      padding: EdgeInsets.symmetric(horizontal: 5),
      child: GestureDetector(
        onTap: () {
          Navigator.push(context, MaterialPageRoute(
              builder: (context) => PersonView(person)
          ));
        },
        child: Card(
          elevation: 10,
          child: Padding(
            padding: EdgeInsets.fromLTRB(10, 10, 0, 10),
            child: Container(
                child:
                    Column(
                        crossAxisAlignment: CrossAxisAlignment.center,
                        children: <Widget>[
                          Text("Nome: ${person.name}"),
                          Text("Altura: ${person.height}"),
                          Text("Gênero: ${person.gender}"),
                          Text("Peso: ${person.mass}")
                        ]),

                ),
          ),
        ),
      ),
    );
  }
}
