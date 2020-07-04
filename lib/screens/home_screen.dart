import 'package:flutter/material.dart';

class HomeScreen extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(
          "Star Wars Wiki",
          style: TextStyle(color: Theme.of(context).accentColor),
          textAlign: TextAlign.center,
        ),
      ),
    );
  }
}
