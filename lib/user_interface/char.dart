import 'package:flutter/material.dart';
import 'package:flutter_icons/flutter_icons.dart';
import 'package:line_icons/line_icons.dart';
import 'package:starchars/data/Character.dart';
import 'package:starchars/data/DatabaseManager.dart';
import 'package:starchars/data/DatabaseProvider.dart';
import 'package:starchars/user_interface/detailscreen.dart';

bool sucesso = true;

class CharItem extends StatefulWidget {

  final Function() notifyParent;

  Character character;

  CharItem(this.character, {Key key, @required this.notifyParent}) : super(key: key);

  @override
  _CharItemState createState() => _CharItemState();
}

class _CharItemState extends State<CharItem> {
  @override
  Widget build(BuildContext context) {
    return Container(
        color: Color.fromRGBO(126, 120, 99, 1.0),
        padding: EdgeInsets.fromLTRB(10, 10, 10, 0),
        height: 180,
        width: double.maxFinite,
        child: Card(
            shape: RoundedRectangleBorder(
                borderRadius: BorderRadius.circular(20.0)),
            color: Colors.white,
            elevation: 6,
            child: InkWell(
              onTap: _detailPage,
              child: new Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: <Widget>[
                  new Row(
                    children: <Widget>[
                      Container(
                          padding: EdgeInsets.fromLTRB(15, 15, 0, 5),
                          child: Text(
                            widget.character.name,
                            style: TextStyle(
                                fontSize: 35,),
                          )),
                      Expanded(
                          child: Container(
                              child: Align(
                        alignment: Alignment.bottomRight,
                        child: FlatButton(
                          splashColor: Colors.transparent,
                          highlightColor: Colors.transparent,
                          onPressed: _pressStar,
                          child: Icon(_getIcon(),
                            size: 30, color: Colors.yellow[700],
                          ),
                        ),
                      ))),
                    ],
                  ),

                  Container(height: 2, width: 310, color: Colors.black,),
                  new Row(
                    children: <Widget>[
                      Expanded(child: Container(padding: EdgeInsets.fromLTRB(15, 15, 0, 0),child:Row(
                          children: <Widget>[
                            Icon(MaterialCommunityIcons.human_male_height, size: 30,),
                            Container(padding: EdgeInsets.fromLTRB(10, 0, 0, 0), child: Text(_getFormatedHeight(), style: TextStyle(fontSize: 25,),))
                          ]
                      ))),

                      Expanded(child: Container(padding: EdgeInsets.fromLTRB(15, 15, 0, 0),child:Row(
                          children: <Widget>[
                            Icon(MaterialCommunityIcons.scale, size: 30,),
                            Container(padding: EdgeInsets.fromLTRB(10, 0, 0, 0), child: Text(_getFormatedWeight(), style: TextStyle(fontSize: 25, ),))
                          ]
                      ))),
                    ],
                  ),
                  new Row(
                    children: <Widget>[
                      Expanded(child: Container(padding: EdgeInsets.fromLTRB(15, 15, 0, 0),child:Row(
                          children: <Widget>[
                            Icon(MaterialCommunityIcons.gender_male_female, size: 30,),
                            Container(padding: EdgeInsets.fromLTRB(10, 0, 0, 0), child: Text(_getFormatedGender(), style: TextStyle(fontSize: 25,),))
                          ]
                      ))),
                    ],
                  ),
                ],
              ),
            )));
  }

  _detailPage() {
    Navigator.push(
      context,
      MaterialPageRoute(builder: (context) => DetailScreen(widget.character)),
    );
  }

  _pressStar() async {

    if(widget.character.fav == 1){
      widget.character.fav = 0;
    }

    else{
      int StatusCode = await DatabaseManager.postFavorite(widget.character.id, sucesso, context);
      sucesso = !sucesso;

      if(StatusCode == 201) {
        widget.character.fav = 1;
      }
    }

    setState((){


      DatabaseProvider.db.updateCharacter(widget.character);


    });

    if(widget.notifyParent != null) {
      widget.notifyParent();
    }

  }

  String _getFormatedWeight(){

    if(isNumeric(widget.character.mass)){

      return widget.character.mass + " kg";
    }

    return widget.character.mass;

  }

  String _getFormatedHeight(){

    if(isNumeric(widget.character.height)){

      return widget.character.height + " cm";
    }

    return widget.character.height;

  }

  String _getFormatedGender(){

    if(widget.character.gender != "n/a"){

      return widget.character.gender[0].toUpperCase() + widget.character.gender.substring(1);
    }

    return widget.character.gender;

  }

  IconData _getIcon(){

    if(widget.character.fav == 1){
      return LineIcons.star;
    }

    return LineIcons.star_o;

  }

}


bool isNumeric(String s) {
  if(s == null) {
    return false;
  }

  s = s.replaceFirst(RegExp(','), '.');
  return double.parse(s, (e) => null) != null;
}

