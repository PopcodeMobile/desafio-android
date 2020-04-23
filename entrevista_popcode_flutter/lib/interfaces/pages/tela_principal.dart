import 'dart:io';
import 'dart:async';
import 'dart:convert';
import 'package:http/http.dart' as http;
import 'package:flutter/material.dart';

class TelaPrincipal extends StatefulWidget {

  @override
  _TelaPrincipalState createState() => _TelaPrincipalState();
}

class _TelaPrincipalState extends State<TelaPrincipal> {
  Future<dynamic> getPersonagens(http.Client client) async {
    final resposta = await client.get("http://swapi.dev/api/people/?format=json");
    var dados = await json.decode(resposta.body);
    return dados;
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: FutureBuilder(
        future: getPersonagens(http.Client()),
        builder: (context, snapshot) {
          if(snapshot.hasError)
            print(snapshot.error);
          return snapshot.hasData ? ListaPersonagens(personagens: snapshot.data) 
          : Center(child: CircularProgressIndicator());
        },
      )
    );
  }
}

class ListaPersonagens extends StatelessWidget {
  final dynamic personagens;

  ListaPersonagens({Key key, this.personagens}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    var results = personagens['results'];
    return ListView.builder(
      itemCount: results.length,
      itemBuilder: (context, index){
        var pessoa = results[index];
        String nome = pessoa['name'];
        String altura = pessoa['height'];
        String genero = pessoa['gender'];
        return ListTile(
          title: Text(nome, style: TextStyle(fontFamily: 'Kanit'),),
          subtitle: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: <Widget>[
                Text('Altura: ' + double.parse(altura).toString(), style: TextStyle(fontFamily: 'Kanit')),
                Text('Gênero: ' + genero, style: TextStyle(fontFamily: 'Kanit')),
              ],
            ),
        );
      },
    );
  }
}