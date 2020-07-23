import 'package:entrevista_popcode/models/pessoa.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class Detalhes extends StatelessWidget {
  final Pessoa pessoa;

  Detalhes({this.pessoa});

  @override
  Widget build(BuildContext context) {
    return new Scaffold(
        appBar: new AppBar(
          centerTitle: true,
          title: new Text(pessoa.name),
        ),
        body: new ListView(children: <Widget>[
          Hero(
            tag: "avatar_" + pessoa.name,
            child: new Image.asset('assets/starWars.jpg'),
          ),
          Container(
            padding: const EdgeInsets.all(32.0),
            child: new Row(
              children: [
                // First child in the Row for the name and the
                // Release date information.
                new Expanded(
                  // Name and Address are in the same column
                  child: new Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Padding(
                        padding: EdgeInsets.only(left: 5.0, right: 5.0),
                        child: Card(
                            color: Color.fromRGBO(58, 66, 86, 1.0),
                            shape: RoundedRectangleBorder(
                                borderRadius: BorderRadius.circular(15)),
                            child: ListTile(
                              title: RichText(
                                  text: TextSpan(
                                      text: "Altura: ",
                                      style: TextStyle(
                                          color: Colors.white, fontSize: 20),
                                      children: <TextSpan>[
                                    TextSpan(
                                      text: pessoa.height,
                                      style: TextStyle(
                                          color: Colors.white, fontSize: 20),
                                    ),
                                  ])),
                            )),
                      ),
                      Padding(
                        padding: EdgeInsets.only(left: 5.0, right: 5.0),
                        child: Card(
                            color: Color.fromRGBO(58, 66, 86, 1.0),
                            shape: RoundedRectangleBorder(
                                borderRadius: BorderRadius.circular(15)),
                            child: ListTile(
                              title: RichText(
                                  text: TextSpan(
                                      text: "Peso: ",
                                      style: TextStyle(
                                          color: Colors.white, fontSize: 20),
                                      children: <TextSpan>[
                                    TextSpan(
                                      text: pessoa.mass,
                                      style: TextStyle(
                                          color: Colors.white, fontSize: 20),
                                    ),
                                  ])),
                            )),
                      ),
                      // Row(children: <Widget>[
                      //   Flexible(
                      //       child: Card(
                      //           color: Color.fromRGBO(58, 66, 86, 1.0),
                      //           //color: Colors.grey[700],
                      //           shape: RoundedRectangleBorder(
                      //               borderRadius: BorderRadius.circular(15)),
                      //           child: ListTile(
                      //               title: RichText(
                      //                   text: TextSpan(
                      //                       text: "Altura: ",
                      //                       style: TextStyle(
                      //                           color: Colors.white,
                      //                           fontSize: 20),
                      //                       children: <TextSpan>[
                      //                 TextSpan(
                      //                   text: pessoa.height,
                      //                   style: TextStyle(
                      //                       color: Colors.white,
                      //                       fontSize: 20.0),
                      //                 ),
                      //               ]))))),
                      //   Flexible(
                      //       child: Card(
                      //           color: Color.fromRGBO(58, 66, 86, 1.0),
                      //           shape: RoundedRectangleBorder(
                      //               borderRadius: BorderRadius.circular(15)),
                      //           child: ListTile(
                      //             title: RichText(
                      //                 text: TextSpan(
                      //                     text: "Pesso: ",
                      //                     style: TextStyle(
                      //                         color: Colors.white,
                      //                         fontSize: 20),
                      //                     children: <TextSpan>[
                      //                   TextSpan(
                      //                     text: pessoa.mass,
                      //                     style: TextStyle(
                      //                         color: Colors.white,
                      //                         fontSize: 20.0),
                      //                   ),
                      //                 ])),
                      //           ))),
                      // ]),
                      Padding(
                        padding: EdgeInsets.only(left: 5.0, right: 5.0),
                        child: Card(
                            color: Color.fromRGBO(58, 66, 86, 1.0),
                            shape: RoundedRectangleBorder(
                                borderRadius: BorderRadius.circular(15)),
                            child: ListTile(
                              title: RichText(
                                  text: TextSpan(
                                      text: "Genêro: ",
                                      style: TextStyle(
                                          color: Colors.white, fontSize: 20),
                                      children: <TextSpan>[
                                    TextSpan(
                                      text: pessoa.gender,
                                      style: TextStyle(
                                          color: Colors.white, fontSize: 20),
                                    ),
                                  ])),
                            )),
                      ),
                      // Row(
                      //   children: <Widget>[
                      //     Flexible(
                      //         child: Card(
                      //             color: Color.fromRGBO(58, 66, 86, 1.0),
                      //             shape: RoundedRectangleBorder(
                      //                 borderRadius: BorderRadius.circular(15)),
                      //             child: ListTile(
                      //               title: RichText(
                      //                   text: TextSpan(
                      //                       text: "Genêro: ",
                      //                       style: TextStyle(
                      //                           color: Colors.white,
                      //                           fontSize: 20),
                      //                       children: <TextSpan>[
                      //                     TextSpan(
                      //                       text: pessoa.gender,
                      //                       style: TextStyle(
                      //                           color: Colors.white,
                      //                           fontSize: 20),
                      //                     ),
                      //                   ])),
                      //             ))),
                      //     Flexible(
                      //         child: Card(
                      //             color: Color.fromRGBO(58, 66, 86, 1.0),
                      //             shape: RoundedRectangleBorder(
                      //                 borderRadius: BorderRadius.circular(15)),
                      //             child: ListTile(
                      //                 title: RichText(
                      //                     text: TextSpan(
                      //                         text: "Espécie: ",
                      //                         style: TextStyle(
                      //                             color: Colors.white,
                      //                             fontSize: 20),
                      //                         children: <TextSpan>[
                      //                   TextSpan(
                      //                     text: (pessoa.specie == null ||
                      //                             pessoa.specie.isEmpty)
                      //                         ? "unknown"
                      //                         : pessoa.specie,
                      //                     style: TextStyle(
                      //                         color: Colors.white,
                      //                         fontSize: 20.0),
                      //                   ),
                      //                 ])))))
                      //   ],
                      // ),
                      Padding(
                        padding: EdgeInsets.only(left: 5.0, right: 5.0),
                        child: Card(
                            color: Color.fromRGBO(58, 66, 86, 1.0),
                            shape: RoundedRectangleBorder(
                                borderRadius: BorderRadius.circular(15)),
                            child: ListTile(
                              title: RichText(
                                  text: TextSpan(
                                      text: "Espécie: ",
                                      style: TextStyle(
                                          color: Colors.white, fontSize: 20),
                                      children: <TextSpan>[
                                    TextSpan(
                                      text: (pessoa.specie == null ||
                                              pessoa.specie.isEmpty)
                                          ? "unknown"
                                          : pessoa.specie,
                                      style: TextStyle(
                                          color: Colors.white, fontSize: 20),
                                    ),
                                  ])),
                            )),
                      ),
                      Padding(
                        padding: EdgeInsets.only(left: 5.0, right: 5.0),
                        child: Card(
                            color: Color.fromRGBO(58, 66, 86, 1.0),
                            shape: RoundedRectangleBorder(
                                borderRadius: BorderRadius.circular(15)),
                            child: ListTile(
                              title: RichText(
                                  text: TextSpan(
                                      text: "Cor do Cabelo: ",
                                      style: TextStyle(
                                          color: Colors.white, fontSize: 20),
                                      children: <TextSpan>[
                                    TextSpan(
                                      text: pessoa.hairColor,
                                      style: TextStyle(
                                          color: Colors.white, fontSize: 20),
                                    ),
                                  ])),
                            )),
                      ),
                      Padding(
                        padding: EdgeInsets.only(left: 5.0, right: 5.0),
                        child: Card(
                            color: Color.fromRGBO(58, 66, 86, 1.0),
                            shape: RoundedRectangleBorder(
                                borderRadius: BorderRadius.circular(15)),
                            child: ListTile(
                              title: RichText(
                                  text: TextSpan(
                                      text: "Cor da Pele: ",
                                      style: TextStyle(
                                          color: Colors.white, fontSize: 20),
                                      children: <TextSpan>[
                                    TextSpan(
                                      text: pessoa.skinColor,
                                      style: TextStyle(
                                          color: Colors.white, fontSize: 20),
                                    ),
                                  ])),
                            )),
                      ),
                      Padding(
                        padding: EdgeInsets.only(left: 5.0, right: 5.0),
                        child: Card(
                            color: Color.fromRGBO(58, 66, 86, 1.0),
                            shape: RoundedRectangleBorder(
                                borderRadius: BorderRadius.circular(15)),
                            child: ListTile(
                              title: RichText(
                                  text: TextSpan(
                                      text: "Cor dos Olhos: ",
                                      style: TextStyle(
                                          color: Colors.white, fontSize: 20),
                                      children: <TextSpan>[
                                    TextSpan(
                                      text: pessoa.eyeColor,
                                      style: TextStyle(
                                          color: Colors.white, fontSize: 20),
                                    ),
                                  ])),
                            )),
                      ),
                      Padding(
                        padding: EdgeInsets.only(left: 5.0, right: 5.0),
                        child: Card(
                            color: Color.fromRGBO(58, 66, 86, 1.0),
                            shape: RoundedRectangleBorder(
                                borderRadius: BorderRadius.circular(15)),
                            child: ListTile(
                              title: RichText(
                                  text: TextSpan(
                                      text: "Planeta: ",
                                      style: TextStyle(
                                          color: Colors.white, fontSize: 20),
                                      children: <TextSpan>[
                                    TextSpan(
                                      text: pessoa.homeWorld,
                                      style: TextStyle(
                                          color: Colors.white, fontSize: 20),
                                    ),
                                  ])),
                            )),
                      ),
                      // Row(
                      //   children: <Widget>[
                      //     Flexible(
                      //         child: Card(
                      //             color: Color.fromRGBO(58, 66, 86, 1.0),
                      //             shape: RoundedRectangleBorder(
                      //                 borderRadius: BorderRadius.circular(15)),
                      //             child: ListTile(
                      //               title: RichText(
                      //                   text: TextSpan(
                      //                       text: "Cor dos Olhos: ",
                      //                       style: TextStyle(
                      //                           color: Colors.white,
                      //                           fontSize: 20),
                      //                       children: <TextSpan>[
                      //                     TextSpan(
                      //                       text: pessoa.eyeColor,
                      //                       style: TextStyle(
                      //                           color: Colors.white,
                      //                           fontSize: 20),
                      //                     ),
                      //                   ])),
                      //             ))),
                      //     Flexible(
                      //         child: Card(
                      //             color: Color.fromRGBO(58, 66, 86, 1.0),
                      //             shape: RoundedRectangleBorder(
                      //                 borderRadius: BorderRadius.circular(15)),
                      //             child: ListTile(
                      //                 title: RichText(
                      //                     text: TextSpan(
                      //                         text: "Planeta: ",
                      //                         style: TextStyle(
                      //                             color: Colors.white,
                      //                             fontSize: 20),
                      //                         children: <TextSpan>[
                      //                   TextSpan(
                      //                     text: pessoa.homeWorld,
                      //                     style: TextStyle(
                      //                         color: Colors.white,
                      //                         fontSize: 20.0),
                      //                   ),
                      //                 ])))))
                      //   ],
                      // ),
                      // Padding(
                      //   padding: EdgeInsets.only(left: 10.0, right: 5.0),
                      //   child: Card(
                      //       color: Color.fromRGBO(58, 66, 86, 1.0),
                      //       shape: RoundedRectangleBorder(
                      //           borderRadius: BorderRadius.circular(15)),
                      //       child: ListTile(
                      //         title: RichText(
                      //             text: TextSpan(
                      //                 text: "Espécie: ",
                      //                 style: TextStyle(
                      //                     color: Colors.white, fontSize: 20),
                      //                 children: <TextSpan>[
                      //               TextSpan(
                      //                 text: (pessoa.specie == null ||
                      //                         pessoa.specie.isEmpty)
                      //                     ? "unknown"
                      //                     : pessoa.specie,
                      //                 style: TextStyle(
                      //                     color: Colors.white, fontSize: 20),
                      //               ),
                      //             ])),
                      //       )),
                      // )

                      // new Text(
                      //   "Altura: " + pessoa.height,
                      //   style: new TextStyle(
                      //     color: Colors.grey[500],
                      //   ),
                      // ),
                      // new Text(
                      //   "Peso: " + pessoa.mass,
                      //   style: new TextStyle(
                      //     color: Colors.grey[500],
                      //   ),
                      // ),
                      // new Text(
                      //   "Cor do cabelo: " + pessoa.hairColor,
                      //   style: new TextStyle(
                      //     color: Colors.grey[500],
                      //   ),
                      // )
                    ],
                  ),
                ),
              ],
            ),
          ),
        ]));
  }
}
