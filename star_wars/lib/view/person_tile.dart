
import 'package:bloc_pattern/bloc_pattern.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/painting.dart';
import 'package:flutter/widgets.dart';
import 'package:starwars/controller/favorite_controller.dart';
import 'package:starwars/controller/person_data_controller.dart';
import 'package:starwars/model/person_model.dart';
import 'package:starwars/view/person_view.dart';

class PersonTile extends StatelessWidget {
  final Person person;

  PersonTile(this.person);


  @override
  Widget build(BuildContext context) {
    final PersonDataController getData =
    BlocProvider.of<PersonDataController>(context);

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
                child: Row(
              children: <Widget>[
                Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: <Widget>[
                      Text("Nome: ${person.name}"),
                      Text("Altura: ${person.height}"),
                      Text("Gênero: ${person.gender}"),
                      Text("Peso: ${person.mass}")
                    ]),
                Spacer(),
                IconButton(
                  icon: Icon(
                    person.favorite == "1" ? Icons.star:Icons.star_border,
                  ),
                  onPressed: () async {
                    final snackbar = SnackBar(
                      content: Text(await getData.updateList(person.id),textAlign: TextAlign.center),
                      duration: Duration(seconds: 3),
                    );
                    Scaffold.of(context).showSnackBar(snackbar);
                  },
                )
              ],
            )),
          ),
        ),
      ),
    );
  }
}
