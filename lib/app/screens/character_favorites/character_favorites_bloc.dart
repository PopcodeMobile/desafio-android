import 'package:bloc_pattern/bloc_pattern.dart';
import 'package:rxdart/rxdart.dart';
import 'package:wiki_star_wars/app/models/character_model.dart';
import 'package:wiki_star_wars/app/services/character_local_service.dart';

class CharacterFavoritesBloc extends BlocBase {
  final _charactersController = BehaviorSubject<List<CharacterModel>>();
  Stream<List<CharacterModel>> get outCharacters =>
      _charactersController.stream;

  List<CharacterModel> _characters;

  void getCharacterFavorites() async {
    CharacterLocalService characterLS = CharacterLocalService();

    await characterLS.getCharacterFavorites().then((value) {
      _characters = value;
      _charactersController.sink.add(_characters);
    }).catchError(
        (e) => _charactersController.sink.addError("An error has occurred"));
  }

  Future<bool> removeFavorite(CharacterModel character) async {
    CharacterLocalService characterLS = CharacterLocalService();

    character.favorite = 'false';

    await characterLS.add(character); // update
    getCharacterFavorites();
    return true;
  }

  @override
  void dispose() {
    _charactersController.close();
    super.dispose();
  }
}
