import 'package:entrevista_pop/widgets/searcher.dart';
import 'package:flutter/material.dart';

class SearchScreen extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(
          'Encontre uma personagem',
          style: TextStyle(color: Theme.of(context).accentColor),
        ),
      ),
      body: Searcher(),
    );
  }
}
