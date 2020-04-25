import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:getflutter/getflutter.dart';
import 'package:entrevista_popcode_flutter/models/personagem.dart';

class ListaPersonagens extends StatelessWidget {
  final List<Personagem> personagens;

  ListaPersonagens({Key key, this.personagens}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return ListView.builder(
      padding: EdgeInsets.all(2.0),
      itemCount: personagens.length,
      itemBuilder: (context, index) {
        var pessoa = personagens[index];
        return Container(
          height: 480.0,
          child: GFCard(
            boxFit: BoxFit.cover,
            image: Image.asset('assets/images/star_wars.jpg'),
            //color: Colors.lightBlueAccent[400],
            shape:
                RoundedRectangleBorder(borderRadius: BorderRadius.circular(20)),
            title: GFListTile(
              padding: EdgeInsets.only(top: 15.0, left: 10.0, right: 10.0),
              title: Text(
                pessoa.nome,
                style: TextStyle(
                    color: Colors.black, fontFamily: 'Kanit', fontSize: 20.0),
              ),
              icon: GFIconButton(
                onPressed: () {},
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
                    'Altura: ' + double.parse(pessoa.altura).toString(),
                    style: TextStyle(fontFamily: 'Kanit', fontSize: 17.0),
                  ),
                  Text(
                    'Peso: ' + double.parse(pessoa.peso).toString(),
                    style: TextStyle(fontFamily: 'Kanit', fontSize: 17.0),
                  ),
                  Text('Gênero: ' + pessoa.genero,
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
                    splashColor: Colors.green,
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