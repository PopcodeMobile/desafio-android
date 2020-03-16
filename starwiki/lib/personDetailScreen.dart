import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:starwiki/database_helper.dart';
import 'package:starwiki/fetcher.dart';

class PersonDetailScreen extends StatefulWidget {
  final People person;
  final Function(String) onFavorite;
  final Function(String) onFavoriteFail;
  
  PersonDetailScreen({
    Key key,
    @required this.person,
    @required this.onFavorite,
    @required this.onFavoriteFail,}) : super(key: key);

  @override
  _PersonDetailScreenState createState() => _PersonDetailScreenState();
}

class _PersonDetailScreenState extends State<PersonDetailScreen> {
  final _planetFetcher = PlanetFetcher();
  final _speciesFetcher = SpeciesFetcher();
  final _favoriteHandler = FavoriteHandler();
  DatabaseHelper databaseHelper = DatabaseHelper();
  String planetName;
  String speciesName;
  bool isLoading;
  String buttonText;

  @override
  void initState() {
    isLoading = true;
    buttonText = widget.person.isFav == 'false' ? "Favorite" : "Unfavorite";
    searchDetails();
  }

  void searchDetails () async {
    String newPlanetName;
    String newSpeciesName;

    await _planetFetcher.fetch(widget.person.planet).then((Planet planet) {
        newPlanetName = planet.name;
    });

    if (widget.person.species.contains("species")) {
      await _speciesFetcher.fetch(widget.person.species).then((Species species) {
        newSpeciesName = species.name;
      });
    } else {
      newSpeciesName = "Unknown";
    }

    setState(() {
      planetName = newPlanetName;
      speciesName = newSpeciesName;
      isLoading = false;
    });
  }

  Future<Map<String, String>> _addFavorite(People person, String id) async {
    Map<String, String> message;
    await _favoriteHandler.createFavorite(person, id).then((Map<String, String> response) async {
      message = response;
      if (response["status"] == "success") {
        await databaseHelper.addFavoritePeople(person);
        widget.onFavorite('true');
      } else if (response["status"] == "connection error") {
        //do nothing
      } else {
        await databaseHelper.saveFavoriteForLater(person, id);
        widget.onFavoriteFail(id);
      }
    });
    return message;
  }

  void _removeFavorite(People person) async {
    await databaseHelper.removeFavoritePeople(person);
    widget.onFavorite('false');
  }

  Future<String> _inputDialog(BuildContext context) async {
    String favId = '';
    return showDialog(
      context: context,
      barrierDismissible: false,
      builder: (BuildContext context) {
        return AlertDialog(
          title: Text('Enter favorite ID'),
          content: new Row(
            children: <Widget>[
              new Expanded(
                child: new TextField(
                  autofocus: true,
                  decoration: new InputDecoration(
                    labelText: 'Favorite ID', hintText: 'eg. 1'),
                  onChanged: (value) {
                    favId = value;
                  },
                ),
              )
            ]
          ),
          actions: <Widget>[
            FlatButton(
              child: Text('Ok'),
              onPressed: () {
                Navigator.of(context).pop(favId);
              },
            ),
          ],
        );
      }
    );
  }

  Future<void> _favoriteResult(BuildContext context, Map<String, String> message) async {
    return showDialog(
      context: context,
      barrierDismissible: false,
      builder: (BuildContext context) {
        return AlertDialog(
          title: Text("Status: " + message["status"]),
          content: Text(message["message"]),
          actions: <Widget>[
            FlatButton(
              child: Text('OK'),
              onPressed: () {
                Navigator.of(context).pop();
              },
            ),
          ],
        );
      }
    );
  }

  Widget build(BuildContext context) {

    ListView detailsList() {
      return ListView(
          padding: const EdgeInsets.all(8.0),
          children: <Widget>[
            Container(
              height: 50,
              child: Text("Height (cm): " + widget.person.height),
            ),
            Container(
              height: 50,
              child: Text("Mass (kg): " + widget.person.mass),
            ),
            Container(
              height: 50,
              child: Text("Hair color: " + widget.person.hairColor),
            ),
            Container(
              height: 50,
              child: Text("Skin color: " + widget.person.skinColor),
            ),
            Container(
              height: 50,
              child: Text("Eye color: " + widget.person.eyeColor),
            ),
            Container(
              height: 50,
              child: Text("Birth year: " + widget.person.birthYear),
            ),
            Container(
              height: 50,
              child: Text("Gender: " + widget.person.gender),
            ),
            Container(
              height: 50,
              child: Text("Home planet: " + planetName),
            ),
            Container(
              height: 50,
              child: Text("Species: " + speciesName),
            ),
            Center(
              child: FlatButton(
                color: Colors.grey,
                padding: EdgeInsets.all(8.0),
                child: Text(buttonText),
                onPressed: () async {
                  if (widget.person.isFav == 'true') {
                    _removeFavorite(widget.person);
                    setState(() {
                      buttonText = "Favorite";
                    });
                  } else {
                    final String favId = await _inputDialog(context);
                    final Map<String, String> message = await _addFavorite(widget.person, favId);
                    _favoriteResult(context, message);
                    setState(() {
                      buttonText = "Unfavorite";
                    });
                  }
                },
              )),
          ],
      );
    }

    Widget buildProgressIndicator () {
      return Center(
              child: SizedBox(
                child: CircularProgressIndicator(),
                height: 24,
                width: 24,
              ),
            );
    }

    return Scaffold(
      appBar: AppBar(
        title: Text(widget.person.name),
      ),
      body: Padding(
        padding: EdgeInsets.all(16.0),
        child: isLoading ? buildProgressIndicator(): detailsList(),
      ),
    );
  }
}