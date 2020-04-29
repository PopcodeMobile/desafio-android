import 'package:entrevista_popcode_flutter/views/pages/detalhe_personagem.dart';
import 'package:entrevista_popcode_flutter/views/pages/favoritos.dart';
import 'package:entrevista_popcode_flutter/views/pages/tela_principal.dart';
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
         '/detalhePers': (context) => DetalhePersonagem(),
         '/favorites': (context) => Favoritos(),
       },
    );
  }
}