import 'package:flutter/material.dart';
import 'package:wikistarwars/model/person_model.dart';
import 'package:wikistarwars/model/planet_model.dart';
import 'package:wikistarwars/model/specie_model.dart';
import 'package:wikistarwars/service/swapi.dart';
import 'package:http/http.dart' as HTTP;
import 'dart:async';
import 'dart:convert';

class PersonDetail extends StatefulWidget {

  final PersonModel personModel;
  PersonDetail({this.personModel});

  @override
  _PersonDetailState createState() => _PersonDetailState();
}

class _PersonDetailState extends State<PersonDetail> {

  String _planet = "Carregando";
  String _specie = "Carregando";

  getPlanet(String url) async {
    String planet = "Sem dado";
    if( url.isNotEmpty || url != null){
      HTTP.Response response = await HTTP.get(url);
      var dataJson = json.decode(response.body);
      planet = dataJson['name'];
    }
    setState(() {
      _planet = planet;
    });
  }

  getSpecie(String url) async {
    String specie = "Sem dado";
    if( url.isNotEmpty || url != null){
      HTTP.Response response = await HTTP.get(url);
      var dataJson = json.decode(response.body);
      specie = dataJson['name'];
    }
    setState(() {
      _specie = specie;
    });
  }

  @override
  void initState() {
    super.initState();
    getPlanet(widget.personModel.homeWorld);
    getSpecie(widget.personModel.species);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        centerTitle: true,
        title: Text(widget.personModel.name),
        backgroundColor: Color(0xFF182E59),
      ),

      body: Container(
        color: Color(0xFF0C1A35),
        padding: EdgeInsets.all(16),
        child: Center(
          child: Column(
            children: <Widget>[
              SizedBox(height: 16,),

              Text('Nome: ${widget.personModel.name}',
                    style: TextStyle(color: Colors.white, fontSize: 18),
              ),

              SizedBox(height: 16,),

              Text('Altura: ${widget.personModel.height}',
                style: TextStyle(color: Colors.white, fontSize: 18),
              ),

              SizedBox(height: 16,),

              Text('Peso: ${widget.personModel.mass}',
                style: TextStyle(color: Colors.white, fontSize: 18),
              ),

              SizedBox(height: 16,),

              Text('Cor do Cabelo: ${widget.personModel.hairColor}',
                style: TextStyle(color: Colors.white, fontSize: 18),
              ),

              SizedBox(height: 16,),

              Text('Cor da Pele: ${widget.personModel.skinColor}',
                style: TextStyle(color: Colors.white, fontSize: 18),
              ),

              SizedBox(height: 16,),

              Text('Cor dos Olhos: ${widget.personModel.eyeColor}',
                style: TextStyle(color: Colors.white, fontSize: 18),
              ),

              SizedBox(height: 16,),

              Text('Ano de Nascimento: ${widget.personModel.birthYear}',
                style: TextStyle(color: Colors.white, fontSize: 18),
              ),

              SizedBox(height: 16,),

              Text('Genero: ${widget.personModel.gender}',
                style: TextStyle(color: Colors.white, fontSize: 18),
              ),

              SizedBox(height: 16,),

              Text('Planeta Natal: $_planet',
                style: TextStyle(color: Colors.white, fontSize: 18),
              ),

              SizedBox(height: 16,),

              Text('Espécie: $_specie',
                style: TextStyle(color: Colors.white, fontSize: 18),
              ),


            ],
          ),
        ),
      ),
    );
  }
}
