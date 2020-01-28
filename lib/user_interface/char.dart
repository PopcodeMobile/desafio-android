import 'package:flutter/material.dart';

class CharItem extends StatefulWidget {

  String nome = "";

  CharItem(this.nome);

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
          child: Text(widget.nome),
          elevation: 6,
        ));
  }
// ···
}