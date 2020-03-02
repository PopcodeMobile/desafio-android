import 'package:mobx/mobx.dart';
import 'package:dio/dio.dart';
import 'package:star_wars_wiki/models/character.dart';
part 'character_controller.g.dart';

class CharacterController = _CharacterControllerBase with _$CharacterController;

abstract class _CharacterControllerBase with Store {
  
  final _dio = Dio();
  String _nextPage = 'https://swapi.co/api/people/';

  @observable
  ObservableList<Character> charList = ObservableList<Character>().asObservable();

  @action
  getMoreData () async {
    final response = await _dio.get(_nextPage);
    if (response.statusCode == 200) {
      _nextPage = response.data['next'];
      print(_nextPage);
      List<Character> tempList = List<Character>();
      for (int i = 0; i < response.data['results'].length; i++) {
        tempList.add(
          Character(
            name: response.data['results'][i]['name'],
            gender: response.data['results'][i]['gender'],
            height: response.data['results'][i]['height'],
            mass: response.data['results'][i]['mass'],
          ),
        );
      }
      charList.addAll(tempList);
      //print(response.data['results'][0]['name']);
      //for (Character c in charList) {
      //  print(c.name);
      //}
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