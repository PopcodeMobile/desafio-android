import 'package:entrevista_popcode/helpers/pessoa_helper.dart';
import 'package:entrevista_popcode/helpers/requisicao_API.dart';
import 'package:entrevista_popcode/models/pessoa.dart';
import 'package:entrevista_popcode/views/personagens.dart';
import 'package:entrevista_popcode/widgets/menu_drawer.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;

class HomePage extends StatefulWidget {
  HomePage({Key key, this.title}) : super(key: key);

  final String title;

  @override
  _HomePageState createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  final TextEditingController _filter = new TextEditingController();

  PessoaHelper pessoa = PessoaHelper();

  List<Pessoa> personagens = new List();
  List<Pessoa> personagensFiltrados = new List();

  String _searchText = "";

  Icon _searchIcon = new Icon(Icons.search);

  Widget _appBarTitle = new Text('Wiki Star Wars');

  @override
  void initState() {
    _getAllPersonagens();
    super.initState();
  }

  _HomePageState() {
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

  void _getAllPersonagens() {
    pessoa.getAll().then((lista) {
      setState(() {
        personagens = lista;
      });
    });
  }

  List<Pessoa> _filtrar() {
    if (_searchText.isNotEmpty) {
      List<Pessoa> auxList = new List();
      // personagensFiltrados = new List();
      for (int i = 0; i < personagens.length; i++) {
        if (personagens[i]
            .name
            .toLowerCase()
            .contains(_searchText.toLowerCase())) {
          // personagensFiltrados.add(personagens[i]);
          auxList.add(personagens[i]);
        }
      }
      personagensFiltrados = auxList;
      return personagensFiltrados;
    }
    return null;
  }

  void _armazenarPersonagens() {
    // ignore: unnecessary_statements
    _getAllPersonagens;
    setState(() {
      this.personagensFiltrados = personagens;
    });
  }

  void _searchPressed() {
    setState(() {
      if (this._searchIcon.icon == Icons.search) {
        this._searchIcon = new Icon(Icons.close);
        this._appBarTitle = new TextField(
          controller: _filter,
          style: new TextStyle(color: Colors.white),
          decoration: new InputDecoration(
            prefixIcon: new Icon(Icons.search, color: Colors.white),
            fillColor: Colors.white,
            hintText: 'Search by name',
            hintStyle: TextStyle(color: Colors.white),
          ),
        );
        _armazenarPersonagens();
      } else {
        this._searchIcon = new Icon(Icons.search);
        this._appBarTitle = new Text('Wiki Star Wars');
        this.personagensFiltrados = personagens;
        _filter.clear();
      }
    });
  }

  // _buildBar(BuildContext context) {
  //   return new AppBar(
  //     elevation: 0.1,
  //     backgroundColor: Color.fromRGBO(58, 66, 86, 1.0),
  //     centerTitle: true,
  //     title: _appBarTitle,
  //     actions: <Widget>[
  //       IconButton(
  //         onPressed: () => _searchPressed(),
  //         icon: _searchIcon,
  //       )
  //     ],
  //   );
  // }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Color.fromRGBO(58, 66, 86, 1.0),
      //appBar: _buildBar(context),
      appBar: new AppBar(
        elevation: 0.1,
        backgroundColor: Color.fromRGBO(58, 66, 86, 1.0),
        centerTitle: true,
        title: _appBarTitle,
        actions: <Widget>[
          IconButton(
            onPressed: () => _searchPressed(),
            icon: _searchIcon,
          )
        ],
      ),
      drawer: Drawer(child: MenuDrawer()),
      body: FutureBuilder<List<Pessoa>>(
        future: RequisicaoApi().getPessoas(http.Client(), 1),
        builder: (context, snapshot) {
          if (snapshot.hasError) print(snapshot.error);
          return (snapshot.hasData || personagens.length > 0)
              ? Personagem(
                  personagens: ((_filtrar() != null)
                      ? _filtrar()
                      : (personagens != null && personagens.length > 0)
                          ? personagens
                          : snapshot.data),
                  isSearching: (_searchText.isEmpty ? false : true))
              : Center(child: CircularProgressIndicator());
        },
      ),
    );
  }
}
