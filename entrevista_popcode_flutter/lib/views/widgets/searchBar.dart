import 'package:entrevista_popcode_flutter/models/pessoa.dart';
import 'package:flutter/material.dart';
import 'package:getflutter/getflutter.dart';

class SearchBar extends StatefulWidget {
  List<Pessoa> personagens;

  SearchBar({@required this.personagens});
  @override
  _SearchBarState createState() => _SearchBarState();
}

class _SearchBarState extends State<SearchBar> {
  @override
  Widget build(BuildContext context) {
    List<String> nomePersonagens = new List();
    widget.personagens.forEach((pessoa) => nomePersonagens.add(pessoa.name));
    return Expanded(
      child: Container(
        color: Colors.black,
        // height: 55,
        // width: 400,
        child: TextField()
        // GFSearchBar(
        //   searchBoxInputDecoration: InputDecoration(
        //     fillColor: Colors.white,
        //     filled: true,
        //     focusColor: Colors.blue,
        //     //border: InputBorder()
        //   ),
        //   searchList: nomePersonagens,
        //   searchQueryBuilder: (query, nomePersonagens) {
        //     List<String> listaEncontrados = nomePersonagens
        //         .where((pessoa) =>
        //             pessoa.toLowerCase().contains(query.toLowerCase()))
        //         .toList();
        //   },
        //   overlaySearchListItemBuilder: (starWarsPersonagem) {
        //     return Container(
        //       //padding: const EdgeInsets.all(8),
        //       child: Text(
        //         starWarsPersonagem,
        //         style: const TextStyle(fontSize: 18),
        //       ),
        //     );
        //   },
        //   onItemSelected: (starWarsPersonagem) {
        //     setState(() {
        //       print('$starWarsPersonagem');
        //     });
        //   },
        // ),
      ),
    );
  }
}
