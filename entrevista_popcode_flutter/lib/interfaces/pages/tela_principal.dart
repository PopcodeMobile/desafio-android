import 'dart:async';
import 'dart:convert';
import 'package:flutter/foundation.dart';
import 'package:http/http.dart' as http;
import 'package:flutter/material.dart';
import 'package:entrevista_popcode_flutter/models/personagem.dart';
import 'package:entrevista_popcode_flutter/interfaces/pages/lista_personagens.dart';

class TelaPrincipal extends StatefulWidget {
  @override
  _TelaPrincipalState createState() => _TelaPrincipalState();
}

class _TelaPrincipalState extends State<TelaPrincipal> {
  Future<List<Personagem>> getPersonagens(http.Client client) async {
    final resposta =
        await client.get("http://swapi.dev/api/people/?format=json");
    //var dados = await json.decode(resposta.body);
    //return dados;
    return compute(parsePersonagem, resposta.body);
  }

  Future<List<Personagem>> getPersonagens2(http.Client client, int offSet, int limit) async {
    String data = await _getJson(offSet, limit);

    List<Map> list = json.decode(data);

    var personagens = List<Personagem>();

    return personagens; 
    
    final resposta =
        await client.get("http://swapi.dev/api/people/?format=json");
    //var dados = await json.decode(resposta.body);
    //return dados;
    return compute(parsePersonagem, resposta.body);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.transparent,
      appBar: AppBar(
        backgroundColor: Colors.amber[400],
        centerTitle: true,
        title: Text("Star Wars Wiki",
            style: TextStyle(fontFamily: "Kanit", color: Colors.black)),
      ),
      body: Container(
        child: FutureBuilder<List<Personagem>>(
          future: getPersonagens(http.Client()),
          builder: (context, snapshot) {
            if (snapshot.hasError) print(snapshot.error);
            return snapshot.hasData
                ? ListaPersonagens(personagens: snapshot.data)
                : Center(child: CircularProgressIndicator());
          },
        ),
        decoration: BoxDecoration(
          image: DecorationImage(
              image: AssetImage("assets/images/star_background.jpg"),
              fit: BoxFit.cover),
        ),
      ),
    );
  }
}

List<Personagem> parsePersonagem(String responseBody) {
  final parsed = jsonDecode(responseBody);
  var results = parsed['results'];
  return results.map<Personagem>((json) => Personagem.fromJson(json)).toList();
}

Future<String> _getJson(int offSet, int limit) async {
  String json = "[";

  for(int i = offSet; i < offSet + limit; i++){
    String id = i.toString();
    String value = "value ($id)";
    json += '{"id":"$id", "value":"$value"}';
    if(i < offSet + limit - 1){
      json += ",";
    }
  }

  json += "]";
  return json;
}
