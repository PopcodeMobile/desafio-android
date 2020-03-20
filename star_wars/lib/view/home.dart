import 'package:bloc_pattern/bloc_pattern.dart';
import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:starwars/controller/person_data_controller.dart';
import 'package:starwars/delegate/search_person.dart';
import 'package:starwars/view/person_tile.dart';

class Home extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final PersonDataController getData =
        BlocProvider.of<PersonDataController>(context);

    getData.getData();

    return Scaffold(
      appBar: AppBar(
        title: Text("Star Wars Wiki"),
        backgroundColor: Colors.black38,
        centerTitle: true,
        actions: <Widget>[
          IconButton(
            icon: Icon(
              Icons.search,
              color: Colors.white,
            ),
            onPressed: () {
              showSearch(context: context, delegate: SearchPerson());
            },
          )
        ],
      ),
      body: StreamBuilder(
        stream: BlocProvider.of<PersonDataController>(context).outData,
        builder: (context, snapshot) {
          if (snapshot.hasData)
            return ListView.builder(
              itemBuilder: (context, index) {
                return PersonTile(snapshot.data[index]);
              },
              itemCount: snapshot.data.length,
            );
          else
            return Container(
            );
        },
      ),
      backgroundColor: Colors.white,
    );
  }
}
