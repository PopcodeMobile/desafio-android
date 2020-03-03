import 'package:flutter/material.dart';
import 'package:wikistarwars/helper/person_helper.dart';
import 'package:wikistarwars/model/person_model.dart';
import 'package:http/http.dart' as HTTP;
import 'dart:convert';

import 'package:wikistarwars/service/sw_fav.dart';

class PersonDetail extends StatefulWidget {

  final PersonModel personModel;
  PersonDetail({this.personModel});

  @override
  _PersonDetailState createState() => _PersonDetailState();
}

class _PersonDetailState extends State<PersonDetail> {

  String _planet = "Carregando";
  String _specie = "Carregando";
  String _message = "";
  String _name = "Carregando";
  String _height = "Carregando";
  String _mass = "Carregando";
  String _hairColor = "Carregando";
  String _skinColor = "Carregando";
  String _eyeColor = "Carregando";
  String _birthYear = "Carregando";
  String _gender = "Carregando";
  String _favorite = "Carregando";

  var _db = PersonHelper();

  _setFavorite(PersonModel personModel) async {
    String status;
    String message = await SW_FAV().favorite(personModel);
    if (widget.personModel.favorite == 'false'){
      status = 'true';
    }else{
      status = 'false';
    }
    setState(() {
      _message = message;
      _favorite = status;
    });

    await _db.setFavorite(widget.personModel);

  }

  _loadPerson(){
    setState(() {
      _name = widget.personModel.name;
      _height = widget.personModel.height;
      _mass = widget.personModel.mass;
      _hairColor = widget.personModel.hairColor;
      _skinColor = widget.personModel.skinColor;
      _eyeColor = widget.personModel.eyeColor;
      _birthYear = widget.personModel.birthYear;
      _gender = widget.personModel.gender;
      _favorite = widget.personModel.favorite;
    });
  }

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
    print(widget.personModel.favorite);
    _loadPerson();
    getPlanet(widget.personModel.homeWorld);
    getSpecie(widget.personModel.species);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        centerTitle: true,
        title: Text(_name),
        backgroundColor: Color(0xFF182E59),
        actions: <Widget>[
          IconButton(
              onPressed: (){
                _setFavorite(widget.personModel);
              },
              icon: _favorite != 'true'
                  ? Icon(Icons.star_border, color: Colors.yellow,)
                  : Icon(Icons.star, color: Colors.yellow,)
          ),
        ],
      ),

      body: Container(
        color: Color(0xFF0C1A35),
        padding: EdgeInsets.all(16),
        child: Center(
          child: Column(
            children: <Widget>[
              SizedBox(height: 16,),

              Text('Nome: $_name',
                    style: TextStyle(color: Colors.white, fontSize: 18),
              ),

              SizedBox(height: 16,),

              Text('Altura: $_height',
                style: TextStyle(color: Colors.white, fontSize: 18),
              ),

              SizedBox(height: 16,),

              Text('Peso: $_mass',
                style: TextStyle(color: Colors.white, fontSize: 18),
              ),

              SizedBox(height: 16,),

              Text('Cor do Cabelo: $_hairColor',
                style: TextStyle(color: Colors.white, fontSize: 18),
              ),

              SizedBox(height: 16,),

              Text('Cor da Pele: $_skinColor',
                style: TextStyle(color: Colors.white, fontSize: 18),
              ),

              SizedBox(height: 16,),

              Text('Cor dos Olhos: $_eyeColor',
                style: TextStyle(color: Colors.white, fontSize: 18),
              ),

              SizedBox(height: 16,),

              Text('Ano de Nascimento: $_birthYear',
                style: TextStyle(color: Colors.white, fontSize: 18),
              ),

              SizedBox(height: 16,),

              Text('Genero: $_gender',
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

              SizedBox(height: 30,),

              _favorite == "true"
              ? Text(_message,
                  style: TextStyle(fontSize: 24, color: Colors.yellow))
              : Container()

            ],
          ),
        ),
      ),
    );
  }
}
