import 'dart:convert';
import 'package:starwiki/people.dart';
import 'package:http/http.dart' as http;

class PageFetcher {
  int _currentPage = 1;
  int _maxPages = 9;

  Future<List<People>> fetch() async {
    List list = new List<People>();

    if (_currentPage <= _maxPages) {
      Page page = new Page();
      var res = await http.get("https://swapi.co/api/people/?page=$_currentPage");
      page = Page.fromJson(jsonDecode(res.body));
      list = page.results;
      _currentPage++;
    }
    
    return list;
  }
}