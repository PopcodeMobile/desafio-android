import 'package:entrevista_popcode_flutter/models/pessoa.dart';
import 'package:entrevista_popcode_flutter/views/widgets/home_menu_drawer.dart';
import 'package:entrevista_popcode_flutter/views/widgets/loading.dart';
import 'package:flutter/cupertino.dart';
import 'package:http/http.dart' as http;
import 'package:flutter/material.dart';
import 'package:entrevista_popcode_flutter/views/pages/lista_personagens.dart';
import 'package:entrevista_popcode_flutter/helpers/helperPessoa.dart';
import 'package:entrevista_popcode_flutter/helpers/requisicao.dart';

class TelaPrincipal extends StatefulWidget {
  List<Pessoa> favoritos;

  TelaPrincipal({this.favoritos});
  @override
  _TelaPrincipalState createState() => _TelaPrincipalState();
}

class _TelaPrincipalState extends State<TelaPrincipal> {
  final TextEditingController _filter = new TextEditingController();
  HelperPessoa helper = HelperPessoa();
  List<Pessoa> pessoas = new List();
  List<Pessoa> pessoasFiltradas = new List();
  String _searchText = "";
  Icon _searchIcon = new Icon(Icons.search, color: Colors.black);
  Widget _appBarTitle = new Text('Star Wars Wiki',
      style: TextStyle(fontFamily: "Kanit", color: Colors.black));

  void initState() {
    if (widget.favoritos == null || widget.favoritos.length == 0) {
      setState(() {
        _getAllPessoas();
      });
    } else {
      this.pessoas = widget.favoritos;
    }
    super.initState();
  }

  _TelaPrincipalState() {
    _filter.addListener(() {
      if (_filter.text.isEmpty) {
        setState(() {
          _searchText = "";
        });
      } else {
        setState(() {
          _searchText = _filter.text;
        });
      }
    });
  }

  void _armazenarPersonagens() {
    _getAllPessoas;
    setState(() {
      this.pessoasFiltradas = pessoas;
    });
  }

  void _getAllPessoas() {
    helper.getAll().then((list) {
      setState(() {
        this.pessoas = list;
      });
    });
  }

  size() async {
    int number = await helper.getNumber();
    return number;
  }

  void _searchPressed() {
    setState(() {
      if (this._searchIcon.icon == Icons.search) {
        this._searchIcon = new Icon(Icons.cancel, color: Colors.black);
        this._appBarTitle = TextField(
          controller: _filter,
          decoration: new InputDecoration(
              hintText: 'Procurar...', border: InputBorder.none),
          style: TextStyle(fontFamily: "Kanit", color: Colors.black),
          cursorColor: Colors.green,
        );
        _armazenarPersonagens();
      } else {
        this._searchIcon = new Icon(Icons.search, color: Colors.black);
        this._appBarTitle = new Text('Star Wars Wiki',
            style: TextStyle(fontFamily: "Kanit", color: Colors.black));
        this.pessoasFiltradas = pessoas;
        _filter.clear();
      }
    });
  }

  List<Pessoa> _buildPersonagens() {
    if (_searchText.isNotEmpty) {
      List<Pessoa> tempList = new List();
      for (int i = 0; i < pessoas.length; i++) {
        if (pessoas[i].name.toLowerCase().contains(_searchText.toLowerCase())) {
          tempList.add(pessoas[i]);
        }
      }
      pessoasFiltradas = tempList;
      return pessoasFiltradas;
    }
    return null;
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.transparent,
      appBar: AppBar(
        backgroundColor: Colors.amber[400],
        centerTitle: true,
        actions: <Widget>[
          IconButton(
            onPressed: () => _searchPressed(),
            icon: _searchIcon,
          )
        ],
        title: this._appBarTitle,
        iconTheme: IconThemeData(color: Colors.black),
      ),
      drawer: Drawer(
        child: HomeMenuDrawer(),
      ),
      body: Container(
        child: FutureBuilder<List<Pessoa>>(
          future: Requisicao().getPersonagens(http.Client(), 1),
          builder: (context, snapshot) {
            if (snapshot.hasError) print(snapshot.error);
            return (snapshot.hasData || pessoas.length > 0)
                ? ListaPersonagens(
                    personagens: ((_buildPersonagens() != null)
                        ? _buildPersonagens() : (pessoas != null && pessoas.length > 0)
                            ? pessoas : snapshot.data),
                    isSearching: (_searchText.isEmpty) ? false : true)
                : Loading();
          },
        ),
        decoration: BoxDecoration(
          image: DecorationImage(
              image: AssetImage("assets/images/star_background.jpg"),
              fit: BoxFit.cover),
        ),
      ),
    );
  }
}
