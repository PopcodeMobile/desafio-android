import 'package:starwiki/database_helper.dart';
import 'package:dio/dio.dart';

class PageFetcher {
  int _currentPage = 1;
  int _maxPages = 9;
  Dio dio = new Dio();

  Future<List<People>> fetch() async {
    List peopleList = new List<People>();
    Page peoplePage = new Page();
    
    try {
      while (_currentPage <= _maxPages) {
        Response res = await dio.get("https://swapi.co/api/people/?page=$_currentPage");
        peoplePage = Page.fromJson(res.data);
        peopleList.addAll(peoplePage.results);
        _currentPage++;
      }
    } catch (e) {
      print(e);
    }

    return peopleList;
  }
}

class PlanetFetcher {
  Dio dio = new Dio();

  Future<Planet> fetch(url) async {
    Planet planet;
    try {
      Response res = await dio.get(url);
      planet = Planet.fromJson(res.data);
    } catch (e) {
      planet.name = "Unknown";
    }
    return planet;
  }
}

class SpeciesFetcher {
  Dio dio = new Dio();

  Future<Species> fetch(url) async {
    Species species;
    try {
      Response res = await dio.get(url);
      species = Species.fromJson(res.data);      
    } catch (e) {
      species.name = "Unknown";
    }
    return species;
  }
}

class FavoriteHandler {
  final String _baseURL = "http://private-782d3-starwarsfavorites.apiary-mock.com/favorite/";
  bool _useHeader = false;
  Dio dio = new Dio();

  Future<Map<String, String>> createFavorite(People person, String id) async {
    Map<String, String> result;    
    Response res;
    if (_useHeader) {      
      _useHeader = false;
      //headers = {"Prefer": "status=400"};
      //res = await http.post(_baseURL + id, body: person.toMap(), headers: headers);
      try {
        res = await dio.post(
          _baseURL + id,
          data: person.toMap(),
          options: Options(
            headers: {
              "Prefer": "status=400"
            },
            validateStatus: (status) { return status < 500;}
          )
        );
      } catch (_) {
        return {"status": "connection error",
                "message": "I felt a great disturbance in the Force."};
      }
    } else {
      _useHeader = true;
      try {
        res = await dio.post(
          _baseURL + id,
          data: person.toMap(),
        );
      } catch (_) {
        return {"status": "connection error",
                "message": "I felt a great disturbance in the Force."};
      }
    }

    /*resBody = jsonDecode(res.body);
    if (res.statusCode == 201) {
      result = {"status": resBody["status"],
                "message": resBody["message"]};
    } else if (res.statusCode == 400) {
      result = {"status": resBody["error"],
                "message": resBody["error_message"]};
    }*/

    //
    if (res.statusCode == 201) {
      result = {"status": res.data["status"],
                "message": res.data["message"]};
    } else if (res.statusCode == 400) {
      result = {"status": res.data["error"],
                "message": res.data["error_message"]};
    }

    return result;
  }
}