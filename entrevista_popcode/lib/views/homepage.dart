import 'package:entrevista_popcode/helpers/pessoa_helper.dart';
import 'package:entrevista_popcode/helpers/requisicao_API.dart';
import 'package:entrevista_popcode/models/pessoa.dart';
import 'package:entrevista_popcode/views/personagens.dart';
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
  List<Pessoa> personagens = new List();
  List<Pessoa> personagensFiltrados = new List();
  PessoaHelper pessoa = PessoaHelper();

  String _searchText = "";

  Icon _searchIcon = new Icon(Icons.search);

  Widget _appBarTitle = new Text('Wiki Star Wars');

  @override
  void initState() {
    super.initState();

    _getAllPersonagens();
    // _armazenarPersonagens();
  }

  void _armazenarPersonagens() {
    _getAllPersonagens();
    setState(() {
      this.personagensFiltrados = personagens;
    });
  }

  void _getAllPersonagens() {
    pessoa.getAll().then((lista) {
      setState(() {
        personagens = lista;
      });
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Color.fromRGBO(58, 66, 86, 1.0),
      appBar: _buildBar(context),
      body: FutureBuilder<List<Pessoa>>(
        future: RequisicaoApi().getPessoas(http.Client(), 1),
        builder: (context, snapshot) {
          if (snapshot.hasError) print(snapshot.error);

          return snapshot.hasData
              ? Personagem(
                  personagens: ((_filtrar() != null)
                      ? _filtrar()
                      : (personagens != null && personagens.length > 0)
                          ? personagens
                          : snapshot.data),
                  isSearching: (_searchText.isEmpty) ? false : true)
              : Center(child: CircularProgressIndicator());
        },
      ),
    );
  }

  Widget _buildBar(BuildContext context) {
    return new AppBar(
        elevation: 0.1,
        backgroundColor: Color.fromRGBO(58, 66, 86, 1.0),
        centerTitle: true,
        title: _appBarTitle,
        leading: new IconButton(
          icon: _searchIcon,
          onPressed: _searchPressed,
        ));
  }

  List<Pessoa> _filtrar() {
    if (_searchText.isNotEmpty) {
      personagensFiltrados = new List();
      for (int i = 0; i < personagens.length; i++) {
        if (personagens[i]
            .name
            .toLowerCase()
            .contains(_searchText.toLowerCase())) {
          personagensFiltrados.add(personagens[i]);
        }
      }
      return personagensFiltrados;
    }
    return null;
  }

  _HomePageState() {
    _filter.addListener(() {
      if (_filter.text.isEmpty) {
        setState(() {
          _searchText = "";
          //_resetRecords();
        });
      } else {
        setState(() {
          _searchText = _filter.text;
        });
      }
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
        _searchIcon = new Icon(Icons.search);
        _appBarTitle = new Text('Wiki Star Wars');
        personagensFiltrados = personagens;
        _filter.clear();
      }
    });
  }
}

//class PessoasList extends StatelessWidget {
//  final List<Pessoa> pessoas;

// PessoasList({Key key, this.pessoas}) : super(key: key);

//  @override
//  Widget build(BuildContext context) {
//    return ListView.builder(
//     itemCount: pessoas.length,
//     itemBuilder: (context, index) {
//       Pessoa p = pessoas[index];
//       return ListTile(
//         title: Text(p.name),
//         subtitle: Text(p.birthYear),
//       );
//     },
//   );
// }
//}
