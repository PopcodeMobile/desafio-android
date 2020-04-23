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
      appBar: AppBar(
        backgroundColor: Colors.redAccent,
        centerTitle: true,
        title: Text("Star Wars Wiki", style: TextStyle(fontFamily: "Kanit")),
      ),
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
      padding: EdgeInsets.all(7.0),
      itemCount: results.length,
      itemBuilder: (context, index){
        var pessoa = results[index];
        String nome = pessoa['name'];
        String altura = pessoa['height'];
        String genero = pessoa['gender'];
        String peso = pessoa['mass'];
        return Card(
          color: Colors.black87,
          shape:
              RoundedRectangleBorder(borderRadius: BorderRadius.circular(15)),
          child: ListTile(
          title: Text(nome, style: TextStyle(color: Colors.white, fontFamily: 'Kanit'),),
          subtitle: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: <Widget>[
                Text('Altura: ' + double.parse(altura).toString(), style: TextStyle(color: Colors.red[400], fontFamily: 'Kanit')),
                Text('Peso: ' + double.parse(peso).toString(), style: TextStyle(color: Colors.red[400], fontFamily: 'Kanit')),
                Text('Gênero: ' + genero, style: TextStyle(color: Colors.red[400], fontFamily: 'Kanit')),
              ],
            ),
          trailing: Icon(
              Icons.arrow_forward_ios,
              size: 30,
              color: Colors.white,
            ),
            onTap: () {},
          ),
        );
      },
    );
  }
}