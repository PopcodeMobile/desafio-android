import 'package:flutter/material.dart';
import 'package:line_icons/line_icons.dart';
import 'package:starchars/data/Character.dart';

class CharItem extends StatefulWidget {
  Character character;

  CharItem(this.character);

  @override
  _CharItemState createState() => _CharItemState();
}

class _CharItemState extends State<CharItem> {
  @override
  Widget build(BuildContext context) {
    return Container(
        color: Color.fromRGBO(126, 120, 99, 1.0),
        padding: EdgeInsets.fromLTRB(10, 10, 10, 0),
        height: 220,
        width: double.maxFinite,
        child: Card(
            shape: RoundedRectangleBorder(
                borderRadius: BorderRadius.circular(20.0)),
            color: Colors.white,
            elevation: 6,
            child: InkWell(
              onTap: () {},
              child: new Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: <Widget>[
                  new Row(
                    children: <Widget>[Align(alignment: Alignment.topRight, child:Text("oi11")), Text("oi11"),],
                  ),
                  new Row(
                    children: <Widget>[Text("oi21"), Text("oi22"),],
                  ),
                  new Row(
                    children: <Widget>[Text("oi31"), Text("oi32"),],
                  ),
                ],
              ),
            )));
  }
// ···
}
