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

class FavoriteHandler {
  final String _baseURL = "http://private-782d3-starwarsfavorites.apiary-mock.com/favorite/";

  Future<Map<String, String>> createFavorite(People person, String id) async {
    var res = await http.post(_baseURL + id, body: person.toMap());    
    var resBody = jsonDecode(res.body);
    Map<String, String> result;
    
    if (res.statusCode == 201) {
      result = {"status": resBody["status"],
                "message": resBody["message"]};
    } else if (res.statusCode == 400) {
      result = {"status": resBody["error"],
                "message": resBody["error_message"]};
      //Save request for later
    }

    return result;
  }
}