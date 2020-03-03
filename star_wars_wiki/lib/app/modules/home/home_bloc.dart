import 'package:bloc_pattern/bloc_pattern.dart';
import 'package:rxdart/rxdart.dart';

import 'package:star_wars_wiki/app/modules/home/home_repository.dart';
import 'package:star_wars_wiki/shared/models/character_model.dart';

class HomeBloc extends BlocBase {
  final HomeRepository repo;

  HomeBloc(this.repo);

  var listChar = BehaviorSubject<List<CharacterModel>>();
  Sink<List<CharacterModel>> get responseIn => listChar.sink;
  Observable<List<CharacterModel>> get responseOut => listChar.stream;

  List<CharacterModel> list = [];
  CharacterModel selectedChar;

  Future fetchCharacters({int page: 1}) async {
    responseIn.add(null);
    try {
      var res = await repo.getCharacters(page: page);

      responseIn.add(res);
      list.addAll(res);
    } catch (e) {
      listChar.addError(e);
    }
  }

  Future<String> favoriteCharacter(CharacterModel char) async {
    String response;
    if (!char.fav) {
      response = await repo.setFavorite(char.name);
      if (response.contains('force')) {
        char.fav = !char.fav;
        list.where((item) => item.name == char.name).toList()[0].fav = char.fav;
      }
      return response;
    } else {
      response = await repo.removeFavorite(char.name);
      if (response.contains('Removed')) {
        char.fav = !char.fav;
        list.where((item) => item.name == char.name).toList()[0].fav = char.fav;
      }
      return response;
    }
  }

  retryFavorites() async {
    await repo.retryFavorites();
  }

  //dispose will be called automatically by closing its streams
  @override
  void dispose() {
    listChar.close();
    super.dispose();
  }
}
