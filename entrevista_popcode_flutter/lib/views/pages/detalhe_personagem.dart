import 'package:entrevista_popcode_flutter/models/pessoa.dart';
import 'package:flutter/material.dart';

class DetalhePersonagem extends StatefulWidget {
  Pessoa personagem;
  DetalhePersonagem({this.personagem});

  @override
  _DetalhePersonagemState createState() => _DetalhePersonagemState();
}

class _DetalhePersonagemState extends State<DetalhePersonagem> {
  @override
  Widget build(BuildContext context) {
    final DetalhePersonagem args = ModalRoute.of(context).settings.arguments; //ESSA FUNÇÃO RETORNA OS ARGUMENTOS QUE PASSEI COMO PARÂMETRO AO NAVEGAR ENTRE AS TELAS
    Pessoa personagem = args.personagem;

    //CRIANDO OS WIDGETS COM AS INFORMAÇÕES ESPECÍFICAS DOS PERSONAGENS
    List<Widget> _personagemInf(BuildContext context) {
      var _informacoes = <Widget>[
         Container(
          padding: EdgeInsets.only(left: 10.0, right: 5.0),
          child: Card(
            color: Colors.amber[400],
            shape:
                RoundedRectangleBorder(borderRadius: BorderRadius.circular(15)),
            elevation: 0.0,
            child: ListTile(
              title: RichText(
                text: TextSpan(
                    text: "Espécie: ",
                    style: TextStyle(
                        color: Colors.white,
                        fontFamily: 'Kanit',
                        fontSize: 20.0),
                    children: <TextSpan>[
                      TextSpan(
                        text: (personagem.specie == null || personagem.specie.isEmpty) ? "unknown" : personagem.specie,
                        style: TextStyle(
                            color: Colors.black,
                            fontFamily: 'Kanit',
                            fontSize: 20.0),
                      ),
                    ]),
              ),
            ),
          ),
        ),
        Container(
          padding: EdgeInsets.only(left: 10.0, right: 5.0),
          child: Card(
            color: Colors.amber[400],
            shape:
                RoundedRectangleBorder(borderRadius: BorderRadius.circular(15)),
            elevation: 0.0,
            child: ListTile(
              title: RichText(
                text: TextSpan(
                    text: "COR DO CABELO: ",
                    style: TextStyle(
                        color: Colors.white,
                        fontFamily: 'Kanit',
                        fontSize: 20.0),
                    children: <TextSpan>[
                      TextSpan(
                        text: personagem.hairColor,
                        style: TextStyle(
                            color: Colors.black,
                            fontFamily: 'Kanit',
                            fontSize: 20.0),
                      ),
                    ]),
              ),
            ),
          ),
        ),
        Container(
          padding: EdgeInsets.only(left: 10.0, right: 5.0),
          child: Card(
            color: Colors.amber[400],
            shape:
                RoundedRectangleBorder(borderRadius: BorderRadius.circular(15)),
            elevation: 0.0,
            child: ListTile(
              title: RichText(
                text: TextSpan(
                    text: "COR DA PELE: ",
                    style: TextStyle(
                        color: Colors.white,
                        fontFamily: 'Kanit',
                        fontSize: 20.0),
                    children: <TextSpan>[
                      TextSpan(
                        text: personagem.skinColor,
                        style: TextStyle(
                            color: Colors.black,
                            fontFamily: 'Kanit',
                            fontSize: 20.0),
                      ),
                    ]),
              ),
            ),
          ),
        ),
        Container(
          padding: EdgeInsets.only(left: 10.0, right: 5.0),
          child: Card(
            color: Colors.amber[400],
            shape:
                RoundedRectangleBorder(borderRadius: BorderRadius.circular(15)),
            elevation: 0.0,
            child: ListTile(
              title: RichText(
                text: TextSpan(
                    text: "COR DOS OLHOS: ",
                    style: TextStyle(
                        color: Colors.white,
                        fontFamily: 'Kanit',
                        fontSize: 20.0),
                    children: <TextSpan>[
                      TextSpan(
                        text: personagem.eyeColor,
                        style: TextStyle(
                            color: Colors.black,
                            fontFamily: 'Kanit',
                            fontSize: 20.0),
                      ),
                    ]),
              ),
            ),
          ),
        ),
        Container(
          padding: EdgeInsets.only(left: 10.0, right: 5.0),
          child: Card(
            color: Colors.amber[400],
            shape:
                RoundedRectangleBorder(borderRadius: BorderRadius.circular(15)),
            elevation: 0.0,
            child: ListTile(
              title: RichText(
                text: TextSpan(
                    text: "ANO DE NASCIMENTO: ",
                    style: TextStyle(
                        color: Colors.white,
                        fontFamily: 'Kanit',
                        fontSize: 20.0),
                    children: <TextSpan>[
                      TextSpan(
                        text: personagem.birthYear,
                        style: TextStyle(
                            color: Colors.black,
                            fontFamily: 'Kanit',
                            fontSize: 20.0),
                      ),
                    ]),
              ),
            ),
          ),
        ),
      ];
      return _informacoes;
    }

    Widget _buildInformacoes() {
      return ListView.builder(
        itemBuilder: (BuildContext context, int index) =>
            _personagemInf(context)[index],
        itemCount: _personagemInf(context).length,
        shrinkWrap: true,
      );
    }

    //CONSTRUINDO PERFIL DO PERSONAGEM
    _buildPerfil() {
      String maleGender = "assets/images/male.png";
      String femaleGender = "assets/images/female.png";
      return SingleChildScrollView(
        child: Padding(
          padding: EdgeInsets.only(top: 20, left: 20, right: 20),
          child: Column(
            children: <Widget>[
              SizedBox(height: 25.0),
              Align(
                alignment: Alignment.topCenter,
                child: Container(
                  width: 90.0,
                  height: 90.0,
                  padding: EdgeInsets.only(left: 50),
                  decoration: BoxDecoration(
                    shape: BoxShape.circle,
                    color: Colors.white54,
                    image: new DecorationImage(
                        image: (AssetImage((personagem.gender == 'female') ? femaleGender : maleGender)),
                        fit: BoxFit.cover),
                    border:
                        Border.all(width: 2.0, color: Colors.lightBlue[400]),
                  ),
                ),
              ),
              Padding(padding: EdgeInsets.only(right: 20, top: 10)),
              Text(personagem.name,
                  style: TextStyle(
                      fontSize: 30, fontFamily: "Kanit", color: Colors.white),
                  textAlign: TextAlign.left),
              Text(personagem.gender.toUpperCase(),
                  style: TextStyle(
                      fontSize: 20, color: Colors.white, fontFamily: "Kanit"),
                  textAlign: TextAlign.left),
              Text(personagem.homeworld,
                  style: TextStyle(
                      fontSize: 20, color: Colors.white, fontFamily: "Kanit"),
                  textAlign: TextAlign.left),
              SizedBox(height: 25),
              Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: <Widget>[
                  Column(
                    children: <Widget>[
                      Text(personagem.mass,
                          style: TextStyle(
                              fontSize: 20,
                              color: Colors.white,
                              fontFamily: "Kanit"),
                          textAlign: TextAlign.left),
                      Text('mass',
                          style: TextStyle(
                              fontSize: 15,
                              color: Colors.white,
                              fontFamily: "Kanit"),
                          textAlign: TextAlign.left),
                    ],
                  ),
                  VerticalDivider(
                    width: 35.0,
                    color: Colors.white,
                    thickness: 5.0,
                  ),
                  Container(
                    height: 30,
                    width: 2,
                    color: Colors.white,
                  ),
                  VerticalDivider(
                    width: 30.0,
                    color: Colors.white,
                    thickness: 5.0,
                  ),
                  Column(
                    children: <Widget>[
                      Text(personagem.height,
                          style: TextStyle(
                              fontSize: 20,
                              color: Colors.white,
                              fontFamily: "Kanit"),
                          textAlign: TextAlign.left),
                      Text('height',
                          style: TextStyle(
                              fontSize: 15,
                              color: Colors.white,
                              fontFamily: "Kanit"),
                          textAlign: TextAlign.left),
                    ],
                  ),
                ],
              ),
              SizedBox(height: 25.0),
              Container(
                height: 430,
                width: 400,
                decoration: BoxDecoration(
                  color: Colors.grey[200],
                  borderRadius: BorderRadius.circular(20.0),
                ),
                child: Column(
                  children: <Widget>[
                    Padding(
                      padding:
                          EdgeInsets.only(left: 15.0, top: 25.0, bottom: 25.0),
                      child: Align(
                        alignment: Alignment.centerLeft,
                        child: Text('Informações',
                            style: TextStyle(
                                fontSize: 24,
                                color: Colors.black,
                                fontFamily: "Kanit",
                                fontWeight: FontWeight.bold)),
                      ),
                    ),
                    _buildInformacoes()
                  ],
                ),
              ),
            ],
          ),
        ),
      );
    }

    return Scaffold(
      resizeToAvoidBottomInset: false,
      backgroundColor: Colors.transparent,
      appBar: AppBar(
        backgroundColor: Colors.amber[400],
        centerTitle: true,
        title: Text("Star Wars Perfil",
            style: TextStyle(fontFamily: "Kanit", color: Colors.black)),
        iconTheme: IconThemeData(color: Colors.black),
      ),
      body: Container(
        decoration: BoxDecoration(
          image: DecorationImage(
              image: AssetImage("assets/images/star_background.jpg"),
              fit: BoxFit.cover),
        ),
        child: _buildPerfil(),
      ),
    );
  }
}
