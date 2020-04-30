import 'package:entrevista_popcode_flutter/helpers/helperFavoritos.dart';
import 'package:entrevista_popcode_flutter/models/pessoa.dart';
import 'package:entrevista_popcode_flutter/views/widgets/home_menu_drawer.dart';
import 'package:entrevista_popcode_flutter/views/widgets/loading.dart';
import 'package:entrevista_popcode_flutter/views/pages/lista_personagens.dart';
import 'package:entrevista_popcode_flutter/helpers/helperPessoa.dart';
import 'package:entrevista_popcode_flutter/helpers/requisicao.dart';
import 'package:flutter/cupertino.dart';
import 'package:http/http.dart' as http;
import 'package:flutter/material.dart';

class TelaPrincipal extends StatefulWidget {
  List<Pessoa> favoritos;  //ESSA VARIÁVEL SERVE PARA PASSAR COMO PARÂMETRO OPCIONAL OS PERSONAGENS MARCADOS COMO FAVORITOS

  TelaPrincipal({this.favoritos});
  @override
  _TelaPrincipalState createState() => _TelaPrincipalState();
}

class _TelaPrincipalState extends State<TelaPrincipal> {
  final TextEditingController _filter = new TextEditingController();
  HelperPessoa helper = HelperPessoa();
  List<Pessoa> pessoas = new List();  //LISTA DE PERSONAGENS
  List<Pessoa> pessoasFiltradas = new List();  //LISTA DE PERSONAGENS PARA BUSCA
  String _searchText = "";
  bool apresentandoFavoritos = false; //ESSA FLAG SERVE PARA O PULL REFRESH NÃO FICAR FAZENDO NOVAS REQUISIÇÕES DURANTE OS FAVORITOS
  Icon _searchIcon = new Icon(Icons.search, color: Colors.black);
  Widget _appBarTitle = new Text('Star Wars Wiki',
      style: TextStyle(fontFamily: "Kanit", color: Colors.black));

  void initState() {
    _getAllPessoas();
    _verificarRequisicoes();
    super.initState();
  }

  //VERIFICANDO SE HOUVE MUDANÇA NO TEXTO DE BUSCA
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

  //VERIFICAR AO INICIAR O PROGRAMA SE HOUVE REQUISIÇÕES POST QUE FALHARAM
  void _verificarRequisicoes() async {
    List<Pessoa> listaFavoritos = await HelperFavoritos().getAll();
    if (listaFavoritos != null && listaFavoritos.length > 0) {
      listaFavoritos.forEach((person) {
        if (person.requestFailed == 1 || person.requestFailed == null) //SE FALHOU, IRÁ REALIZAR NOVAMENTE O POST
          Requisicao().adicionaFavoritos(person);
      });
    }
  }

  //ARMAZENANDO PERSONAGENS PARA SER UTILIZADO DURANTE A BUSCA
  void _armazenarPersonagens() {
    _getAllPessoas;
    setState(() {
      this.pessoasFiltradas = pessoas;
    });
  }

  //BUSCAR TODOS OS PERSONAGENS DO BANCO DE DADOS LOCAL
  void _getAllPessoas() {
    helper.getAll().then((list) {
      setState(() {
        pessoas = list;
      });
    });
  }

  //AO CLICAR NO BOTÃO DE PESQUISAR
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

  //FILTRA PERSONAGENS (BUSCA)
  List<Pessoa> _filtraPersonagens() {
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
    if (widget.favoritos != null && widget.favoritos.length > 0) {
      setState(() {
        //SE A LISTA DE FAVORITOS NÃO ESTIVER VAZIA, ENTÃO A LISTA A SER EXIBIDA SERÁ ESSA
        this.pessoas = widget.favoritos;
        this.apresentandoFavoritos = true;
      });
    }

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
                    personagens: ((_filtraPersonagens() != null)
                        ? _filtraPersonagens()
                        : (pessoas != null && pessoas.length > 0)
                            ? pessoas
                            : snapshot.data),
                    isSearching:
                        (_searchText.isEmpty && !this.apresentandoFavoritos)
                            ? false
                            : true)
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
