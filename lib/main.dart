import 'package:flutter/material.dart';
import 'package:oktoast/oktoast.dart';
import 'package:starchars/data/Character.dart';
import 'package:starchars/data/CharacterManager.dart';
import 'package:starchars/data/DatabaseManager.dart';
import 'package:starchars/data/DatabaseProvider.dart';
import 'package:starchars/user_interface/tabview.dart';

void main() => runApp(TelaInicial());

bool splash = true;

class TelaInicial extends StatefulWidget {
  TelaInicialState createState() => TelaInicialState();
}

class TelaInicialState extends State<TelaInicial> {


  @override
  void initState() {
    super.initState();
    getFirstPage();

  }

  void getFirstPage() async {
    print("ENTROU AQUI");
    Character char = await DatabaseProvider.db.getCharacterWithId(10);

    if(char == null){
      bool finished = await DatabaseManager.parseCharacters(1);
      if (finished) {
        splash = false;
        print("LOADED FIRST");
        setState(() {});
      }
    }

    else{

      splash = false;
      setState(() {});

    }
  }



  @override
  Widget build(BuildContext context) {
    if (splash) {
      return MaterialApp(
          home: Container(
              color: Colors.yellow[700],
              child: Center(
                  child: Text(
                "Star Chars",
                style: TextStyle(color: Colors.black, fontFamily: 'StarWars'),
              ))));
    } else {
      return TabPage();
    }
  }
}
