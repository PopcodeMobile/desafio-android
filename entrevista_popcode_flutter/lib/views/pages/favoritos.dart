import 'package:entrevista_popcode_flutter/helpers/helperFavoritos.dart';
import 'package:entrevista_popcode_flutter/models/pessoa.dart';
import 'package:entrevista_popcode_flutter/views/pages/tela_principal.dart';
import 'package:flutter/material.dart';

class Favoritos extends StatefulWidget {
  @override
  _FavoritosState createState() => _FavoritosState();
}

class _FavoritosState extends State<Favoritos> {
  List<Pessoa> listaFavoritos = List();
  HelperFavoritos helperFavoritos = HelperFavoritos();

  // @override
  // void initState() {
  //   _getAllFavoritos();
  //   super.initState();
  // }

  // void _getAllFavoritos() {
  //   helperFavoritos.getAll().then((list) {
  //     setState(() {
  //       this.listaFavoritos = list;
  //     });
  //   });
  // }

  @override
  Widget build(BuildContext context) {
    return FutureBuilder(
      future: helperFavoritos.getAll(),
      builder: (context, snapshot) {
        return TelaPrincipal(
            favoritos: (this.listaFavoritos != null && this.listaFavoritos.length > 0)
                    ? this.listaFavoritos
                    : snapshot.data);
      },
    );
  }
}
