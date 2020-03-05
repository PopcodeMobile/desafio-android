import 'package:mobx/mobx.dart';
import 'package:dio/dio.dart';
import 'package:star_wars_wiki/database/database_provider.dart';
import 'package:star_wars_wiki/models/character.dart';
part 'character_controller.g.dart';

class CharacterController = _CharacterControllerBase with _$CharacterController;

abstract class _CharacterControllerBase with Store {
  
  final _dio = Dio();

  //@observable
  int _numLoads = 0;

  @observable
  String _nextPage = 'https://swapi.co/api/people/?page=1';

  @computed
  bool get hasNextPage => _nextPage != null;

  @observable
  ObservableList<Character> charList = ObservableList<Character>().asObservable();

  @action
  getMoreData () async {
    List<Character> tempList = await _loadDataFromDB();
    if (tempList.isEmpty)
      tempList = await _loadDataFromApi();
    _numLoads++;
    _nextPage = 'https://swapi.co/api/people/?page=${_numLoads + 1}';
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
      print('deu bom: $i');
    }
    return tempList;
  }

  Future<List<Character>> _loadDataFromApi() async {
    List<Character> tempList = List<Character>();
    if (hasNextPage) {
      final response = await _dio.get(_nextPage);
      if (response.statusCode == 200) {
        //_nextPage = response.data['next'];
        for (int i = 0; i < response.data['results'].length; i++) {
          var info = response.data['results'][i];
          Character tempChar = Character(
            isFavorite: false,
            name: info['name'],
            gender: info['gender'],
            height: info['height'],
            mass: info['mass'],
            hairColor: info['hair_color'],
            skinColor: info['skin_color'],
            eyeColor: info['eye_color'],
            birthYear: info['birth_year'],
            homeworldReference: info['homeworld'],
            speciesReference: info['species'],
          );
          tempChar.id = await DatabaseProvider.db.saveCharacter(tempChar);
          tempList.add(tempChar);
        }
      }
    }
    return tempList; // talvez subir
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
    if (charList[index].species.isEmpty) {
      for (String s in charList[index].speciesReference) {
        Response response = await Dio().get(s);
        charList[index].species.add(response.data['name']);
      }
      if (charList[index].species.isEmpty) {
        charList[index].species.add('none');
      }
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