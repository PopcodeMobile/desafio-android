import 'dart:convert';
import 'package:starwiki/database_helper.dart';
import 'package:http/http.dart' as http;

class PageFetcher {
  int _currentPage = 1;
  int _maxPages = 9;

  Future<List<People>> fetch() async {
    List peopleList = new List<People>();
    Page peoplePage = new Page();
    
    while (_currentPage <= _maxPages) {
      var res = await http.get("https://swapi.co/api/people/?page=$_currentPage");
      peoplePage = Page.fromJson(jsonDecode(res.body));
      //for (People person in peoplePage.results) {

      //}
      peopleList.addAll(peoplePage.results);
      _currentPage++;
    }

    return peopleList;
  }
}

class PlanetFetcher {
  Future<Planet> fetch(url) async {
    var res = await http.get(url);
    Planet planet = Planet.fromJson(jsonDecode(res.body));
    return planet;
  }
}

class SpeciesFetcher {
  Future<Species> fetch(url) async {
    var res = await http.get(url);
    Species species = Species.fromJson(jsonDecode(res.body));
    return species;
  }
}