import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:starwiki/database_helper.dart';
import 'package:starwiki/fetcher.dart';

class PersonDetailScreen extends StatefulWidget {
  final People person;
  
  PersonDetailScreen({Key key, @required this.person}) : super(key: key);

  @override
  _PersonDetailScreenState createState() => _PersonDetailScreenState();
}

class _PersonDetailScreenState extends State<PersonDetailScreen> {
  final _planetFetcher = PlanetFetcher();
  final _speciesFetcher = SpeciesFetcher();
  String planetName;
  String speciesName;
  bool isLoading = true;

  @override
  void initState() {
    isLoading = true;
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