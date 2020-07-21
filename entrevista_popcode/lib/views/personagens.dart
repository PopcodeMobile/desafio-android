import 'package:entrevista_popcode/helpers/pessoa_helper.dart';
import 'package:entrevista_popcode/helpers/requisicao_API.dart';
import 'package:entrevista_popcode/models/pessoa.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:pull_to_refresh/pull_to_refresh.dart';
import 'package:http/http.dart' as http;
import 'package:flutter/foundation.dart';

// ignore: must_be_immutable
class Personagem extends StatefulWidget {
  List<Pessoa> personagens;
  bool isSearching;
  Personagem({Key key, this.personagens, this.isSearching}) : super(key: key);

  @override
  _Personagem createState() => _Personagem();
}

class _Personagem extends State<Personagem> {
  RefreshController _refreshController =
      RefreshController(initialRefresh: false);
  PessoaHelper helperP = PessoaHelper();
  int page = 2;

  void _onRefresh() async {
    await Future.delayed(Duration(milliseconds: 1000));
    _refreshController.refreshCompleted();
  }

  void _onLoading() async {
    await Future.delayed(Duration(milliseconds: 1000));

    if (this.page < 9 && widget.isSearching) {
      List<Pessoa> listPessoa =
          await RequisicaoApi().getPessoas(http.Client(), this.page);
      widget.personagens.addAll(listPessoa);
      for (var item in listPessoa) {
        await helperP.savePeople(item);
      }
      this.page++;
    }
    if (mounted) setState(() {});
    _refreshController.loadComplete();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        backgroundColor: Color.fromRGBO(58, 66, 86, 1.0),
        body: SmartRefresher(
          enablePullDown: true,
          enablePullUp: true,
          header: WaterDropHeader(),
          footer: CustomFooter(
            builder: (BuildContext context, LoadStatus mode) {
              Widget body;
              if (mode == LoadStatus.idle) {
                body = Text("pull up load");
              } else if (mode == LoadStatus.loading) {
                body = CupertinoActivityIndicator();
              } else if (mode == LoadStatus.failed) {
                body = Text("Falha ao carregar. Tente novamente!");
              } else if (mode == LoadStatus.canLoading) {
                body = Text("Solte para carregar mais");
              } else {
                body = Text("Não há mais dados");
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
          child: _buildListItem(),
          //child: ListView.builder(
          //  itemCount: widget.personagens.length,
          //  itemBuilder: (context, index) {
          //    Pessoa p = widget.personagens[index];
          //    PessoaHelper().savePeople(p);
          //   return Card(
          //       child: Center(
          //            child: ListTile(
          //      title: Text(p.name),
          //      subtitle: Text(p.birthYear),
          //    )));
          //  },
          // ),
        ));
  }

  Widget _buildListItem() {
    return ListView.builder(
        itemCount: widget.personagens.length,
        itemBuilder: (context, index) {
          Pessoa p = widget.personagens[index];
          return Card(
            key: ValueKey(p.name),
            elevation: 8.0,
            margin: new EdgeInsets.symmetric(horizontal: 10.0, vertical: 6.0),
            child: Container(
              decoration: BoxDecoration(color: Color.fromRGBO(64, 75, 96, .9)),
              child: ListTile(
                contentPadding:
                    EdgeInsets.symmetric(horizontal: 20.0, vertical: 10.0),
                leading: Container(
                    padding: EdgeInsets.only(right: 12.0),
                    decoration: new BoxDecoration(
                        border: new Border(
                            right: new BorderSide(
                                width: 1.0, color: Colors.white24))),
                    child: Hero(
                        tag: "avatar_" + p.name,
                        child: CircleAvatar(
                          radius: 32,
                          // backgroundImage: NetworkImage(record.photo),
                        ))),
                title: Text(
                  p.name,
                  style: TextStyle(
                      color: Colors.white, fontWeight: FontWeight.bold),
                ),
                subtitle: Row(
                  children: <Widget>[
                    new Flexible(
                        child: new Column(
                            crossAxisAlignment: CrossAxisAlignment.start,
                            children: <Widget>[
                          RichText(
                            text: TextSpan(
                              text: p.birthYear,
                              style: TextStyle(color: Colors.white),
                            ),
                            maxLines: 3,
                            softWrap: true,
                          )
                        ]))
                  ],
                ),
                trailing: Icon(Icons.keyboard_arrow_right,
                    color: Colors.white, size: 30.0),
                onTap: () {},
              ),
            ),
          );
        });
  }
}
