import 'package:mobx/mobx.dart';
import 'package:dio/dio.dart';
import 'package:star_wars_wiki/database/database_provider.dart';
import 'package:star_wars_wiki/models/character.dart';
part 'character_controller.g.dart';

class CharacterController = _CharacterControllerBase with _$CharacterController;

abstract class _CharacterControllerBase with Store {
  
  final _dio = Dio();

  int _numLoads = 0;

  @observable
  String _nextPage = 'https://swapi.co/api/people/?page=1';

  @computed
  bool get hasNextPage => _nextPage != null;

  @observable
  ObservableList<Character> charList = ObservableList<Character>().asObservable();

  @action
  getMoreData () async {
    if(hasNextPage) {
      _numLoads++;
      _nextPage = 'https://swapi.co/api/people/?page=$_numLoads';
      List<Character> tempList = await _loadDataFromApi();
      if (tempList.isEmpty)
        tempList = await Future.delayed(Duration(seconds: 1), _loadDataFromDB);
      charList.addAll(tempList);
    }
  }

  @action
  refresh () {
    charList.sort((a, b) {
      if(a.isFavorite && !b.isFavorite) return -1;
      else if (!a.isFavorite && b.isFavorite) return 1;
      else return 0;
    });
  }

  @action
  Future<List<Character>> _loadDataFromDB() async {
    List<Character> tempList = List<Character>();
    int initialIdToLoad = ((_numLoads - 1) * 10) + 1;
    for (int i = initialIdToLoad; i < (initialIdToLoad + 10); i++) {
      Character tempChar = await DatabaseProvider.db.getCharacter(i);
      if (tempChar == null) {
        _nextPage = null;
        break;
      }
      tempList.add(tempChar);
    }
    return tempList;
  }

  @action
  Future<List<Character>> _loadDataFromApi() async {
    List tempList = List<Character>();
    try {
      final response = await _dio.get(_nextPage);
      _nextPage = response.data['next'];
      for (int i = 0; i < response.data['results'].length; i++) {
        var info = response.data['results'][i];
        Character tempChar = Character(
          name: info['name'],
          gender: info['gender'],
          height: info['height'],
          mass: info['mass'],
          hairColor: info['hair_color'],
          skinColor: info['skin_color'],
          eyeColor: info['eye_color'],
          birthYear: info['birth_year'],
          homeworldReference: info['homeworld'],
          speciesReference: info['species'].isNotEmpty ? info['species'][0] : null,
        );
        tempChar.id = await DatabaseProvider.db.replaceCharacter(tempChar);
        tempChar.isFavorite = (
          await DatabaseProvider.db.getIsFavorite(tempChar) == 1
          ) ? true : false;
        tempList.add(tempChar);
      }
    } catch (e) {}
    return tempList;
  }

  @action
  loadHomeworldData (index) async {
    if (charList[index].homeworld == null) {
      try {
        Response response = await Dio().get(charList[index].homeworldReference);
        charList[index].homeworld = response.data['name'];
      } catch (e) {}
    }
  }

  @action
  loadSpeciesData (index) async {
    if (charList[index].species == null) {
      try {
        Response response = await Dio().get(charList[index].speciesReference);
        charList[index].species = response.data['name'];
      } catch (e) {}
    }
  }

  @action
  Future<String> setFavorite (char, index) async {
    String favoriteMessage = 'Removed from Favorites';
    charList[index].isFavorite = !charList[index].isFavorite;
    DatabaseProvider.db.updateIsFavorite(charList[index].isFavorite ? 1 : 0, charList[index].id);
    try {
      if(charList[index].isFavorite) {
        Response response = await Dio().post('http://private-782d3-starwarsfavorites.apiary-mock.com/favorite/${char.id}');
        favoriteMessage = response.data['message'];
      }
    } catch (e) {
      favoriteMessage = 'No connection. Saved locally.';
    }
    return favoriteMessage;
  }

  @action
  String formatSubtitle(index) {
    String gender = charList[index].gender;
    String height = charList[index].height != 'unknown' ?
      charList[index].height + 'cm' : charList[index].height;
    String mass = charList[index].mass != 'unknown' ?
      charList[index].mass + 'kg' : charList[index].mass;

    return '$gender  •  $height  •  $mass';
  }
}