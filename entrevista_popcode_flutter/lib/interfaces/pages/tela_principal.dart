import 'dart:async';
import 'dart:convert';
import 'package:http/http.dart' as http;
import 'package:flutter/material.dart';
import 'package:getflutter/getflutter.dart';

class TelaPrincipal extends StatefulWidget {
  @override
  _TelaPrincipalState createState() => _TelaPrincipalState();
}

class _TelaPrincipalState extends State<TelaPrincipal> {
  Future<dynamic> getPersonagens(http.Client client) async {
    final resposta =
        await client.get("http://swapi.dev/api/people/?format=json");
    var dados = await json.decode(resposta.body);
    return dados;
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        backgroundColor: Colors.lightBlueAccent,
        appBar: AppBar(
          backgroundColor: Colors.amber[400],
          centerTitle: true,
          title: Text("Star Wars Wiki",
              style: TextStyle(fontFamily: "Kanit", color: Colors.black)),
        ),
        body: FutureBuilder(
          future: getPersonagens(http.Client()),
          builder: (context, snapshot) {
            if (snapshot.hasError) print(snapshot.error);
            return snapshot.hasData
                ? ListaPersonagens(personagens: snapshot.data)
                : Center(child: CircularProgressIndicator());
          },
        ));
  }
}

class ListaPersonagens extends StatelessWidget {
  final dynamic personagens;

  ListaPersonagens({Key key, this.personagens}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    var results = personagens['results'];
    return ListView.builder(
      padding: EdgeInsets.all(2.0),
      itemCount: results.length,
      itemBuilder: (context, index) {
        var pessoa = results[index];
        String nome = pessoa['name'];
        String altura = pessoa['height'];
        String genero = pessoa['gender'];
        String peso = pessoa['mass'];
        return Container(
          height: 480.0,
          child: GFCard(
            boxFit: BoxFit.cover,
            image: Image.asset('assets/images/star_wars.jpg'),
            //color: Colors.lightBlueAccent[400],
            shape:
                RoundedRectangleBorder(borderRadius: BorderRadius.circular(15)),
            title: GFListTile(
              padding: EdgeInsets.only(top: 15.0, left: 10.0, right: 10.0),
              title: Text(
                nome,
                style: TextStyle(
                    color: Colors.black, fontFamily: 'Kanit', fontSize: 20.0),
              ),
              icon: GFIconButton(
                onPressed: (){},
                icon: Icon(Icons.favorite, size: 25.0, color: Colors.red),
                color: Colors.white,
                size: 40.0,
                type: GFButtonType.transparent,
              ),
            ),
            content: Container(
              padding: EdgeInsets.only(left: 20.0, bottom: 20.0),
              alignment: Alignment.centerLeft,
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: <Widget>[
                  Text(
                    'Altura: ' + double.parse(altura).toString(),
                    style: TextStyle(fontFamily: 'Kanit', fontSize: 17.0),
                  ),
                  Text(
                    'Peso: ' + double.parse(peso).toString(),
                    style: TextStyle(fontFamily: 'Kanit', fontSize: 17.0),
                  ),
                  Text('Gênero: ' + genero,
                      style: TextStyle(fontFamily: 'Kanit', fontSize: 17.0)),
                ],
              ),
            ),
            buttonBar: GFButtonBar(
              alignment: WrapAlignment.center,
              children: <Widget>[
                ButtonTheme(
                  minWidth: 100,
                  height: 40,
                  child: RaisedButton(
                    onPressed: () {},
                    child: const Text('Ver', style: TextStyle(fontSize: 15)),
                    color: Colors.amber[400],
                    shape: RoundedRectangleBorder(
                        borderRadius: BorderRadius.circular(80)),
                    splashColor: Colors.redAccent,
                  ),
                ),
              ],
            ),
          ),
        );
      },
    );
  }
}
