import 'package:entrevista_popcode_flutter/interfaces/pages/boas_vindas.dart';
import 'package:flutter/material.dart';

class WikiStarWars extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
     return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: "Wiki Star Wars",
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      initialRoute: './',
       routes: <String, WidgetBuilder>{
         '/': (context) => BoasVindas(),
       },
    );
  }
}