import 'package:flutter/material.dart';
import 'package:star_wars_wiki/shared/models/character_model.dart';

class DetailPage extends StatefulWidget {
  final CharacterModel char;

  const DetailPage({Key key, @required this.char}) : super(key: key);
  @override
  _DetailPageState createState() => _DetailPageState();
}

class _DetailPageState extends State<DetailPage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(
          widget.char.name,
          style: TextStyle(
            fontFamily: 'Star Jedi',
            // fontSize: 25.0,
          ),
        ),
        centerTitle: true,
      ),
    );
  }
}
