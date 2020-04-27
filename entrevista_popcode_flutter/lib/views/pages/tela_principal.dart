import 'package:entrevista_popcode_flutter/models/pessoa.dart';
import 'package:entrevista_popcode_flutter/views/widgets/loading.dart';
import 'package:flutter/cupertino.dart';
import 'package:http/http.dart' as http;
import 'package:flutter/material.dart';
import 'package:entrevista_popcode_flutter/views/pages/lista_personagens.dart';
import 'package:entrevista_popcode_flutter/helpers/helperPessoa.dart';
import 'package:entrevista_popcode_flutter/helpers/requisicao.dart';

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
          future: Requisicao().getPersonagens(http.Client(), 1),
          builder: (context, snapshot) {
            if (snapshot.hasError) print(snapshot.error);
            return (snapshot.hasData || pessoas.length > 0)
                ? ListaPersonagens(personagens: ((pessoas != null && pessoas.length > 0)
                        ? pessoas : snapshot.data))
                : Loading();
          },
        ),
        decoration: BoxDecoration(image: DecorationImage(
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