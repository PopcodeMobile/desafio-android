import 'package:mobx/mobx.dart';
import 'package:dio/dio.dart';
import 'package:star_wars_wiki/models/character.dart';
part 'character_controller.g.dart';

class CharacterController = _CharacterControllerBase with _$CharacterController;

abstract class _CharacterControllerBase with Store {
  
  final _dio = Dio();

  @observable
  String _nextPage = 'https://swapi.co/api/people/';

  @computed
  bool get hasNextPage => _nextPage != null;

  @observable
  ObservableList<Character> charList = ObservableList<Character>().asObservable();

  @action
  getMoreData () async {
    if (hasNextPage) {
      final response = await _dio.get(_nextPage);
      _nextPage = response.data['next'];
      print(_nextPage);
      List<Character> tempList = List<Character>();
      for (int i = 0; i < response.data['results'].length; i++) {
        var char = response.data['results'][i];
        tempList.add(
          Character(
            isFavorite: false,
            name: char['name'],
            gender: char['gender'],
            height: char['height'],
            mass: char['mass'],
            hairColor: char['hair_color'],
            skinColor: char['skin_color'],
            eyeColor: char['eye_color'],
            birthYear: char['birth_year'],
            homeworldReference: char['homeworld'],
            speciesReference: char['species'],
          ),
        );
      }
      charList.addAll(tempList);
    }
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
  String formatSubtitle(index) {
    String gender = charList[index].gender;
    String height = charList[index].height + 'cm';
    String mass = charList[index].mass + 'kg';
    return '$gender  •  $height  •  $mass';
  }
}