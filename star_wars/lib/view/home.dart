import 'package:bloc_pattern/bloc_pattern.dart';
import 'package:flutter/material.dart';
import 'package:starwars/controller/get_data_controller.dart';

class Home extends StatelessWidget {
  @override
  Widget build(BuildContext context) {

    final GetDataController getData  = BlocProvider.of<GetDataController>(context);
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
            onPressed: () {},
          )
        ],
      ),
    );
  }
}
