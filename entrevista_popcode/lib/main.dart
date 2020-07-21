import 'package:entrevista_popcode/views/homepage.dart';
//import 'package:entrevista_popcode/views/personagens.dart';
import 'package:flutter/material.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      // title: appTitle,
      theme: new ThemeData(
        primaryColor: Color.fromRGBO(58, 66, 86, 1.0),
      ),
      routes: {
        '/': (context) => HomePage(
              title: 'Wiki Star Wars',
            ),
      },
      //  title: 'Flutter Demo',
      // theme: ThemeData(
      //    primarySwatch: Colors.blue,
      //     visualDensity: VisualDensity.adaptivePlatformDensity,
      //   ),
      //  home: MyHomePage(),
    );
  }
}
