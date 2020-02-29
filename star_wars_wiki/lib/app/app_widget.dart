import 'package:flutter/material.dart';
import 'package:star_wars_wiki/app/modules/home/home_module.dart';

class AppWidget extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'Flutter Slidy',
      theme: ThemeData(
        primarySwatch: Colors.yellow,
      ),
      home: HomeModule(),
    );
  }
}
