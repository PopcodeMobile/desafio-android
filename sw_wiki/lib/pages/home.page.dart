import 'package:flutter/material.dart';

class HomePage extends StatefulWidget {
  @override
  _HomePageState createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.black,
        title: Text("Star Wars Wiki"),
        centerTitle: true,
        textTheme: TextTheme(
          title: TextStyle(
            color: Theme.of(context).primaryColor,
            fontSize: 25,
            fontFamily: "Courier",
          ),
        ),
      ),
    );
  }
}
