import 'package:entrevista_popcode_flutter/helpers/helperFavoritos.dart';
import 'package:entrevista_popcode_flutter/models/pessoa.dart';
import 'package:entrevista_popcode_flutter/views/pages/tela_principal.dart';
import 'package:flutter/material.dart';

class Favoritos extends StatefulWidget {
  @override
  _FavoritosState createState() => _FavoritosState();
}

class _FavoritosState extends State<Favoritos> {
  HelperFavoritos helperFavoritos = HelperFavoritos();

  //BUSCAR TODOS OS PERSONAGENS FAVORITOS E PASSAR COMO PARÂMETRO PARA A CLASSE TELA_PRINCIPAL
  Future<List<Pessoa>> _getAllFavoritos() async {
    return await helperFavoritos.getAll();
  }

  @override
  Widget build(BuildContext context) {
    return FutureBuilder<List<Pessoa>>(
      future: _getAllFavoritos(),
      builder: (context, snapshot) {
        if (snapshot.hasError) print(snapshot.error);
        return TelaPrincipal(favoritos: snapshot.data);
      },
    );
  }
}
