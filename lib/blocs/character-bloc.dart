import 'package:entrevista_android/models/character.dart';
import 'package:entrevista_android/services/swapi-client.dart';
import 'package:flutter/widgets.dart';

class CharacterBloc extends ChangeNotifier {
  List<Character> _characterList = [];

  //paginator counter used in api responses
  int _pageIndex = 1;

  //api client
  final SwapiClient _api = SwapiClient();

  void addMultiple(List<Character> list) {
    _characterList.addAll(list);
  }

  List<Character> get listOfCharacters => _characterList;

  load() async {
    if (listOfCharacters.length < 87) {
      List<Character> list = await _api.fetchCharactersByPage(page: _pageIndex);
      addMultiple(list);
      _pageIndex++;

      notifyListeners();
      print("notify");
    }
  }
}
