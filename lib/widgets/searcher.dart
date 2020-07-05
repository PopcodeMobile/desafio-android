import 'package:entrevista_pop/providers/character.dart';
import 'package:entrevista_pop/providers/characters.dart';
import 'package:entrevista_pop/providers/seaarching.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

class Searcher extends StatefulWidget {
  @override
  _SearcherState createState() => _SearcherState();
}

class _SearcherState extends State<Searcher> {
  TextEditingController _searchController = TextEditingController();
  @override
  Widget build(BuildContext context) {
    final characters = Provider.of<Characters>(context);

    final mediaQueryData = MediaQuery.of(context);
    return GestureDetector(
      child: Padding(
        padding: EdgeInsets.all(8),
        child: Container(
          width: double.infinity,
          child: Column(
            children: <Widget>[
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                crossAxisAlignment: CrossAxisAlignment.center,
                children: <Widget>[
                  SizedBox(
                    child: TextField(
                      controller: _searchController,
                    ),
                    width: mediaQueryData.size.width * 0.6,
                  ),
                  FlatButton(
                    onPressed: () {
                      /** Remove o foco do teclado */
                      FocusScopeNode currentFocus = FocusScope.of(context);

                      if (!currentFocus.hasPrimaryFocus) {
                        currentFocus.unfocus();
                      }
                      characters.search(_searchController.text.trim());
                    },
                    child: Text('Pesquisar'),
                  )
                ],
              ),
              if (characters.searchResult.isNotEmpty)
                Center(
                  child: Text('oi'),
                )
            ],
          ),
        ),
      ),
    );
  }
}
