import 'package:flutter/material.dart';
import 'package:wikistarwars/model/person_model.dart';
import 'package:wikistarwars/service/swapi.dart';
import 'package:http/http.dart' as HTTP;
import 'dart:convert';
import 'dart:async';

import 'package:wikistarwars/view/person_detail.dart';

class Home extends StatefulWidget {
  @override
  _HomeState createState() => _HomeState();
}

class _HomeState extends State<Home> {
  TextEditingController _search = TextEditingController();

  _openDetails(PersonModel person){
    Navigator.push(context, MaterialPageRoute(builder: (_) => PersonDetail(personModel: person,)));
  }

  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        centerTitle: true,
        title: Text('Wiki Star Wars'),
        backgroundColor: Color(0xFF182E59),
      ),
      body: Container(
        color: Color(0xFF0C1A35),
        padding: EdgeInsets.all(10),
        child: Column(
          children: <Widget>[
            Text('Filtrar por:', style: TextStyle(color: Colors.white),),
            SizedBox(height: 6,),

            Row(
              crossAxisAlignment: CrossAxisAlignment.center,
              mainAxisAlignment: MainAxisAlignment.center,
              children: <Widget>[
                SizedBox(
                  width: 290,
                  height: 40,
                  child: TextField(
                      decoration: InputDecoration(
                          hintStyle: TextStyle(fontSize: 12, color: Colors.white),
                          labelStyle: TextStyle(color: Color(0xFF0387E9)),
                          border: OutlineInputBorder(),
                          suffixIcon: IconButton(
                            onPressed: (){},
                            tooltip: 'Pesquisar',
                            icon: Icon(Icons.search),
                            color: Color(0xFF0387E9),
                          ),
                          labelText: 'Pesquisar',
                          hintText: 'Nome, planeta ou especie'
                      )
                  ),
                ),

                SizedBox(
                  child: IconButton(
                    tooltip: 'Favoritos',
                    onPressed: (){},
                    icon: Icon(Icons.star, color: Colors.yellow, size: 32,),
                    hoverColor: Colors.yellowAccent,
                    splashColor: Colors.yellowAccent,
                  ),
                )
              ],
            ),

            Expanded(
              child: FutureBuilder<List<PersonModel>>(
                future: SWAPI().getAllPersons(),
                builder: (_, snapshot) {
                  switch (snapshot.connectionState) {
                    case ConnectionState.none:
                    case ConnectionState.waiting:
                      return Center(
                        child: CircularProgressIndicator(),
                      );
                      break;
                    case ConnectionState.active :
                    case ConnectionState.done :
                      if (snapshot.hasError) {
                        return Center(
                          child: Text("Problema com os Dados", style: TextStyle(color: Colors.white),),
                        );
                      } else {
                        return ListView.builder(
                            itemCount: snapshot.data.length,
                            itemBuilder: (context, index) {
                              List<PersonModel> lista = snapshot.data;
                              PersonModel person = lista[index];
                              return ListTile(

                                onTap: (){
                                  _openDetails(person);
                                },
                                title: Text('Nome: ${person.name}\nAltura: ${person.height}',
                                            style: TextStyle(color: Colors.white),),
                                subtitle: Text('Genero: ${person.gender}\nPeso: ${person.mass}',
                                            style: TextStyle(color: Colors.white),),
                                trailing: IconButton(
                                  onPressed: (){},
                                  icon: person.favorite == false
                                        ? Icon(Icons.star_border, color: Colors.yellow,)
                                        : Icon(Icons.star, color: Colors.yellow,)
                                ),
                              );
                            }
                        );
                      }
                  }
                }
                ),
            ),

          ],
        ),
      ),
    );
  }
}
