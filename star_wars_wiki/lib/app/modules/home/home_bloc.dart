import 'package:bloc_pattern/bloc_pattern.dart';
import 'package:rxdart/rxdart.dart';

import 'package:star_wars_wiki/app/modules/home/home_repository.dart';
import 'package:star_wars_wiki/shared/models/character_model.dart';

class HomeBloc extends BlocBase {
  final HomeRepository repo;

  HomeBloc(this.repo);

  var listPost = BehaviorSubject<List<CharacterModel>>();
  Sink<List<CharacterModel>> get responseIn => listPost.sink;

  Observable<List<CharacterModel>> get responseOut => listPost.stream;

  List<CharacterModel> list = [];

  void fetchCharacters({int page: 1}) async {
    // responseIn.add(null);
    try {
      var res = await repo.getCharacters(page: page);
      responseIn.add(res);
      for (var item in res) list.add(item);
    } catch (e) {
      listPost.addError(e);
    }
  }

  void favoriteCharacter(CharacterModel char) {
    char.fav = !char.fav;
  }

  //dispose will be called automatically by closing its streams
  @override
  void dispose() {
    // listPost.close();
    super.dispose();
  }
}
