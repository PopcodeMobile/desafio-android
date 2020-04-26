import 'dart:async';
import 'dart:convert';
import 'package:entrevista_popcode_flutter/models/pessoa.dart';
import 'package:flutter/foundation.dart';
import 'package:getflutter/getflutter.dart';
import 'package:http/http.dart' as http;
import 'package:flutter/material.dart';
import 'package:entrevista_popcode_flutter/views/pages/lista_personagens.dart';
import 'package:entrevista_popcode_flutter/helpers/helperPessoa.dart';

class TelaPrincipal extends StatefulWidget {
  @override
  _TelaPrincipalState createState() => _TelaPrincipalState();
}

class _TelaPrincipalState extends State<TelaPrincipal> {
  HelperPessoa helper = HelperPessoa();
  List<Pessoa> pessoas = List();
  
  void initState() {
    super.initState();
    if (size() != 0) {
      _getAllPessoas();
    }
  }

  Future<List<Pessoa>> getPersonagens(http.Client client) async {
    http.Response resposta;
    try{
      resposta = await client.get("http://swapi.dev/api/people/?format=json");
    } catch (e){
      throw new Exception("Não foi possível obter informações da API!");
    }
    return compute(parsePersonagem, resposta.body);
  }

  // Future<List<Personagem>> getPersonagens2(http.Client client, int offSet, int limit) async {
  //   String data = await _getJson(offSet, limit);

  //   List<Map> list = json.decode(data);

  //   var personagens = List<Personagem>();

  //   return personagens;

  //   final resposta =
  //       await client.get("http://swapi.dev/api/people/?format=json");
  //   //var dados = await json.decode(resposta.body);
  //   //return dados;
  //   return compute(parsePersonagem, resposta.body);
  // }

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
        child: FutureBuilder<List<Pessoa>>(
          future: getPersonagens(http.Client()),
          builder: (context, snapshot) {
            if (snapshot.hasError) print(snapshot.error);
            return (snapshot.hasData || pessoas.length > 0)
                ? ListaPersonagens(personagens: ((pessoas != null && pessoas.length > 0) ? pessoas : snapshot.data))
                : Center(child: GFLoader(type:GFLoaderType.custom, child: Image(image: AssetImage("assets/images/eclipse_loading.gif"), width: 70, height: 70)));
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

  void _getAllPessoas() {
    helper.getAll().then((list) {
      setState(() {
        pessoas = list;
      });
    });
  }

  size() async {
    int number = await helper.getNumber();
    return number;
  }
}

List<Pessoa> parsePersonagem(String responseBody) {
  if(responseBody != ""){
    final parsed = jsonDecode(responseBody);
    var results = parsed['results'];
    return results.map<Pessoa>((json) => Pessoa.fromJson(json, false)).toList();
  } else {
    return null;
  }
}

// Future<String> _getJson(int offSet, int limit) async {
//   String json = "[";

//   for(int i = offSet; i < offSet + limit; i++){
//     String id = i.toString();
//     String value = "value ($id)";
//     json += '{"id":"$id", "value":"$value"}';
//     if(i < offSet + limit - 1){
//       json += ",";
//     }
//   }

//   json += "]";
//   return json;
// }
