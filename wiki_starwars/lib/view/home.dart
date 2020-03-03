import 'package:flutter/material.dart';
import 'package:wikistarwars/helper/person_helper.dart';
import 'package:wikistarwars/model/person_model.dart';
import 'package:wikistarwars/service/sw_fav.dart';
import 'package:wikistarwars/service/swapi.dart';

import 'package:wikistarwars/view/person_detail.dart';

class Home extends StatefulWidget {
  @override
  _HomeState createState() => _HomeState();
}

class _HomeState extends State<Home> {

  var _db = PersonHelper();
  String _message = "Carregando";
  List<PersonModel> _listCharacters = List<PersonModel>();

  _setFavorite(PersonModel personModel) async {

    String message = await SW_FAV().favorite(personModel);
    setState(() {
      _message = message;
    });
    await _db.setFavorite(personModel);
    _getCharacters();

  }

  _getFavorites() async {
    List peoples = await _db.getCharFav();
    List<PersonModel> listTemp = List<PersonModel>();
    for (var person in peoples){
      PersonModel personModel = PersonModel.fromMap(person);
      listTemp.add(personModel);
    }
    setState(() {
      _listCharacters = listTemp;
    });
    listTemp = null;
  }

  _getCharacters() async {
    List peoples = await _db.getChar();
    List<PersonModel> listTemp = List<PersonModel>();
    for (var person in peoples){
      PersonModel personModel = PersonModel.fromMap(person);
      listTemp.add(personModel);
    }
    setState(() {
      _listCharacters = listTemp;
    });
    listTemp = null;
    if(_listCharacters.length < 73){
      _getCharacters();
    }
  }

  _searchCharacters(String param) async {
    List peoples = await _db.searchChar(param);

    List<PersonModel> listTemp = List<PersonModel>();
    for (var person in peoples){
      PersonModel personModel = PersonModel.fromMap(person);
      listTemp.add(personModel);
    }
    setState(() {
      _listCharacters = listTemp;
    });
    listTemp = null;
  }


  _openDetails(PersonModel person){
    Navigator.push(context, MaterialPageRoute(builder: (_) => PersonDetail(personModel: person)));
  }

  @override
  void initState() {
    super.initState();
    _getCharacters();
    SWAPI().getAllPersons();
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
                  width: 250,
                  height: 40,
                  child: TextField(
                      style: TextStyle(color: Colors.white),
                      onChanged: (value){
                        _searchCharacters(value);
                      },
                      decoration: InputDecoration(
                          hintStyle: TextStyle(fontSize: 12, color: Colors.white),
                          labelStyle: TextStyle(color: Color(0xFF0387E9)),
                          border: OutlineInputBorder(),
                          labelText: 'Pesquisar',
                          hintText: 'Nome, planeta ou espécie'
                      )
                  ),
                ),

                SizedBox(
                  child: IconButton(
                    tooltip: 'Favoritos',
                    onPressed: (){
                      _getFavorites();
                    },
                    icon: Icon(Icons.star, color: Colors.yellow, size: 32,),
                    hoverColor: Colors.yellowAccent,
                    splashColor: Colors.yellowAccent,
                  ),
                ),

                SizedBox(
                  child: IconButton(
                    tooltip: 'Recarregar',
                    onPressed: (){
                      _getCharacters();
                    },
                    icon: Icon(Icons.cached, color: Color(0xFF0387E9), size: 32,),
                  ),
                )
              ],
            ),

            SizedBox(height: 16,),

            Expanded(
              child: ListView.builder(
                itemCount: _listCharacters.length,
                itemBuilder: (_, index) {
                  final person = _listCharacters[index];
                  return ListTile(
                    onTap: (){
                      _openDetails(person);
                    },
                    leading: Text('${person.id}',
                      style: TextStyle(color: Colors.yellow, fontSize: 24, fontWeight: FontWeight.bold),),
                    title: Text('Nome: ${person.name}\nAltura: ${person.height}',
                      style: TextStyle(color: Colors.white),),
                    subtitle: Text('Genero: ${person.gender}\nPeso: ${person.mass}',
                      style: TextStyle(color: Colors.white),),
                    trailing: IconButton(
                        onPressed: (){
                          _setFavorite(person);
                          return showDialog(
                              context: context,
                              builder: (context) {
                                return SingleChildScrollView(
                                    child: AlertDialog(
                                      shape: RoundedRectangleBorder(
                                          borderRadius: BorderRadius.all(Radius.circular(20.0))
                                      ),
                                      title: Text("MENSAGEM",
                                          style: TextStyle( color: Color(0xFF182E59),)),
                                      content: Text(_message,
                                        style: TextStyle(color: Color(0xFF0387E9)),),
                                      actions: <Widget>[
                                        IconButton(
                                            splashColor: Colors.redAccent,
                                            icon: Icon(Icons.clear),
                                            color: Colors.red,
                                            onPressed: (){
                                              Navigator.pop(context);
                                              _getCharacters();
                                            }
                                        ),
                                      ],
                                    )
                                );
                              }
                          );
                        },
                        icon: person.favorite != 'true'
                            ? Icon(Icons.star_border, color: Colors.yellow,)
                            : Icon(Icons.star, color: Colors.yellow,)
                    ),
                  );
                }
              ),
            ),

          ],
        ),
      ),
    );
  }
}
