import 'package:entrevista_popcode_flutter/helpers/helperFavoritos.dart';
import 'package:entrevista_popcode_flutter/helpers/requisicao.dart';
import 'package:entrevista_popcode_flutter/models/pessoa.dart';
import 'package:entrevista_popcode_flutter/views/pages/detalhe_personagem.dart';
import 'package:entrevista_popcode_flutter/views/widgets/loading.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:getflutter/getflutter.dart';
import 'package:entrevista_popcode_flutter/helpers/helperPessoa.dart';
import 'package:pull_to_refresh/pull_to_refresh.dart';
import 'package:http/http.dart' as http;
import 'package:sprintf/sprintf.dart';

class ListaPersonagens extends StatefulWidget {
  List<Pessoa> personagens;
  bool isSearching;
  ListaPersonagens({Key key, this.personagens, this.isSearching})
      : super(key: key);

  @override
  _ListaPersonagensState createState() => _ListaPersonagensState();
}

class _ListaPersonagensState extends State<ListaPersonagens> {
  HelperPessoa helper = HelperPessoa();
  HelperFavoritos helperFavoritos = HelperFavoritos();
  RefreshController _refreshController =
      RefreshController(initialRefresh: false);
  Icon _favoriteIcon =
      new Icon(Icons.favorite_border, size: 25.0, color: Colors.red);
  Icon _fullFavoriteIcon =
      new Icon(Icons.favorite, size: 25.0, color: Colors.red);
  int numeroPagina = 2;

  void _onRefresh() async {
    // monitor network fetch
    await Future.delayed(Duration(milliseconds: 1000));
    // if failed,use refreshFailed()
    _refreshController.refreshCompleted();
  }

  void _onLoading() async {
    await Future.delayed(Duration(milliseconds: 1000));
    if (this.numeroPagina < 9 && !widget.isSearching) {
      List<Pessoa> pessoas =
          await Requisicao().getPersonagens(http.Client(), this.numeroPagina);
      widget.personagens.addAll(pessoas);
      pessoas.forEach((personagem) => helper.save(personagem));
      this.numeroPagina++;
    }
    if (mounted) setState(() {});
    _refreshController.loadComplete();
  }

  void apresentarMensagem(BuildContext context, bool adicionou) {
    final scaffold = Scaffold.of(context);
    scaffold.showSnackBar(
      SnackBar(
        content: Text(
            (adicionou) ? 'Adicionado aos favoritos' : 'Removido dos favoritos',
            style: TextStyle(fontFamily: 'Kanit')),
        action: SnackBarAction(
            label: 'Fechar',
            onPressed: scaffold.hideCurrentSnackBar,
            textColor: Colors.green),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scrollbar(
      child: SmartRefresher(
        enablePullDown: true,
        enablePullUp: true,
        header: WaterDropMaterialHeader(
          backgroundColor: Colors.amber[400],
          color: Colors.black,
        ),
        footer: CustomFooter(
          builder: (BuildContext context, LoadStatus mode) {
            Widget body;
            if (mode == LoadStatus.idle) {
              body = Text("");
            } else if (mode == LoadStatus.loading) {
              body = Loading();
            } else if (mode == LoadStatus.failed) {
              body = Text("Falha em carregar! Clique novamente!");
            } else if (mode == LoadStatus.canLoading) {
              body = Text("Solte para carregar mais!");
            } else {
              body = Text("Não há mais dados a serem exibidos");
            }
            return Container(
              height: 55.0,
              child: Center(child: body),
            );
          },
        ),
        controller: _refreshController,
        onRefresh: _onRefresh,
        onLoading: _onLoading,
        child: ListView.builder(
          padding: EdgeInsets.all(2.0),
          itemCount: widget.personagens.length,
          itemBuilder: (context, index) {
            Pessoa pessoa = widget.personagens[index];
            helper.save(pessoa);
            return Container(
              height: 500.0,
              child: GFCard(
                boxFit: BoxFit.cover,
                image: Image.asset('assets/images/star_wars.jpg'),
                //color: Colors.lightBlueAccent[400],
                shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(20)),
                elevation: 4.0,
                title: GFListTile(
                  padding: EdgeInsets.only(top: 15.0, left: 10.0, right: 10.0),
                  title: Text(
                    pessoa.name,
                    style: TextStyle(
                        color: Colors.black,
                        fontFamily: 'Kanit',
                        fontSize: 20.0),
                  ),
                  icon: GFIconButton(
                      onPressed: () {
                        bool adicionou;
                        setState(() {
                          if (pessoa.isFavorite == null ||
                              pessoa.isFavorite == 0) {
                            pessoa.isFavorite = 1;
                            helper.update(pessoa);
                            helperFavoritos.save(pessoa);
                            adicionou = true;
                          } else {
                            pessoa.isFavorite = 0;
                            helper.update(pessoa);
                            helperFavoritos.delete(pessoa);
                            adicionou = false;
                          }
                          apresentarMensagem(context, adicionou);
                        });
                      },
                      icon: (pessoa.isFavorite != 1)
                          ? this._favoriteIcon
                          : this._fullFavoriteIcon,
                      color: Colors.white,
                      size: 40.0,
                      type: GFButtonType.transparent,
                      splashColor: Colors.transparent),
                ),
                content: Container(
                  padding: EdgeInsets.only(left: 20.0, bottom: 20.0),
                  alignment: Alignment.centerLeft,
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: <Widget>[
                      Text(
                        sprintf(
                            'Altura: %s',
                            (pessoa.height != 'unknown')
                                ? [
                                    double.parse(pessoa.height
                                            .replaceFirst(',', '.'))
                                        .toString()
                                  ]
                                : ['unknown']),
                        style: TextStyle(fontFamily: 'Kanit', fontSize: 17.0),
                      ),
                      Text(
                        sprintf(
                            'Peso: %s',
                            (pessoa.mass != 'unknown')
                                ? [
                                    double.parse(
                                            pessoa.mass.replaceFirst(',', '.'))
                                        .toString()
                                  ]
                                : ['unknown']),
                        style: TextStyle(fontFamily: 'Kanit', fontSize: 17.0),
                      ),
                      Text('Gênero: ' + pessoa.gender,
                          style:
                              TextStyle(fontFamily: 'Kanit', fontSize: 17.0)),
                    ],
                  ),
                ),
                buttonBar: GFButtonBar(
                  alignment: WrapAlignment.center,
                  children: <Widget>[
                    ButtonTheme(
                      minWidth: 100,
                      height: 40,
                      child: RaisedButton(
                        onPressed: () => Navigator.pushNamed(
                            context, '/detalhePers',
                            arguments: DetalhePersonagem(personagem: pessoa)),
                        child:
                            const Text('Ver', style: TextStyle(fontSize: 15)),
                        color: Colors.amber[400],
                        shape: RoundedRectangleBorder(
                            borderRadius: BorderRadius.circular(80)),
                        splashColor: Colors.green,
                      ),
                    ),
                  ],
                ),
              ),
            );
          },
        ),
      ),
    );
  }
}
