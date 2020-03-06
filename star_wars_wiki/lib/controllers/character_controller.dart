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
    List<Character> tempList = await _loadDataFromApi();
    if (tempList.isEmpty)
      tempList = await Future.delayed(Duration(seconds: 1), _loadDataFromDB);
    _numLoads++;
    _nextPage = 'https://swapi.co/api/people/?page=${_numLoads+1}';
    print(await DatabaseProvider.db.readAllCharacters());
    charList.addAll(tempList);
  }

  Future<List<Character>> _loadDataFromDB() async {
    List<Character> tempList = List<Character>();
    int initialIdToLoad = (_numLoads * 10) + 1;
    for (int i = initialIdToLoad; i < (initialIdToLoad + 10); i++) {
      Character tempChar = await DatabaseProvider.db.getCharacter(i);
      if (tempChar == null)
        continue;
      tempList.add(tempChar);
    }
    return tempList;
  }

  Future<List<Character>> _loadDataFromApi() async {
    List tempList = List<Character>();
    if (hasNextPage) {
      try {
        final response = await _dio.get(_nextPage);
        print('>>> ${response.statusCode}');
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
          tempList.add(tempChar);
        }
      } catch (e) {
        return tempList;
      }
    }
    return tempList;
  }

  @action
  loadHomeworldData (index) async {
    if (charList[index].homeworld == null) {
      Response response = await Dio().get(charList[index].homeworldReference);
      charList[index].homeworld = response.data['name'];
    }
  }

  @action
  loadSpeciesData (index) async {
    if (charList[index].species == null) {
      Response response = await Dio().get(charList[index].speciesReference);
      charList[index].species = response.data['name'];
    }
  }

  @action
  setFavorite (index) {
    charList[index].isFavorite = !charList[index].isFavorite;
    DatabaseProvider.db.updateIsFavorite(charList[index].isFavorite ? 1 : 0, charList[index].id);
  }

  @action
  String formatSubtitle(index) {
    String gender = charList[index].gender;
    String height = charList[index].height + 'cm';
    String mass = charList[index].mass + 'kg';
    return '$gender  •  $height  •  $mass';
  }
}