import 'package:entrevista_popcode_flutter/interfaces/pages/boas_vindas.dart';
import 'package:entrevista_popcode_flutter/interfaces/pages/tela_principal.dart';
import 'package:flutter/material.dart';

void main() => runApp(WikiStarWars()); 

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
         '/': (context) => TelaPrincipal(),
       },
    );
  }
}