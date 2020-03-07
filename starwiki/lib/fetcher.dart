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