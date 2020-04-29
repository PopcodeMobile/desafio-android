import 'package:entrevista_popcode_flutter/helpers/helperFavoritos.dart';
import 'package:entrevista_popcode_flutter/models/pessoa.dart';
import 'package:entrevista_popcode_flutter/views/pages/tela_principal.dart';
import 'package:flutter/material.dart';

class Favoritos extends StatefulWidget {
  @override
  _FavoritosState createState() => _FavoritosState();
}

class _FavoritosState extends State<Favoritos> {
  List<Pessoa> listaFavoritos = new List();
  HelperFavoritos helperFavoritos = HelperFavoritos();

  void initState() {
    super.initState();
    _getAllPessoas();
  }

  Future<List<Pessoa>> _getAllFavoritos() async {
    return await helperFavoritos.getAll();
  }

  void _getAllPessoas() {
    HelperFavoritos().getAll().then((list) {
      setState(() {
        listaFavoritos = list;
      });
    });
  }

  @override
  Widget build(BuildContext context) {
    return FutureBuilder<List<Pessoa>>(
      future: _getAllFavoritos(),
      builder: (context, snapshot) {
        if (snapshot.hasError) print(snapshot.error);
        return TelaPrincipal(
            favoritos:
                (this.listaFavoritos != null && this.listaFavoritos.length > 0)
                    ? this.listaFavoritos
                    : snapshot.data);
      },
    );
  }
}
