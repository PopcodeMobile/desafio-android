import 'package:bloc_pattern/bloc_pattern.dart';
import 'package:rxdart/rxdart.dart';
import 'package:wiki_star_wars/app/models/character_model.dart';
import 'package:wiki_star_wars/app/services/character_favorite_service.dart';
import 'package:wiki_star_wars/app/services/character_local_service.dart';
import 'package:wiki_star_wars/app/services/character_service.dart';
import 'package:wiki_star_wars/app/services/connection_service.dart';
import 'package:wiki_star_wars/app/services/favorites_error_service.dart';
import 'package:wiki_star_wars/app/shared/constants.dart';

class HomeBloc extends BlocBase {
  ConnectionService conn = ConnectionService();

  init() async {
    if (await conn.verifyConnection()) {
      await verifyFavErrors();
      await getCharacters(null);
    } else
      await getLocalCharacters();
  }

  final _loadingController = BehaviorSubject<bool>();
  Stream<bool> get outLoading => _loadingController.stream;

  final _charactersController = BehaviorSubject<List<CharacterModel>>();
  Stream<List<CharacterModel>> get outCharacters =>
      _charactersController.stream;
  Function(List<CharacterModel>) get changeCharacters =>
      _charactersController.sink.add;

  List<CharacterModel> _characters;
  String _nextPage;
  Future<void> getLocalCharacters() async {
    CharacterLocalService characterLS = CharacterLocalService();
    await characterLS.getCharacters().then((value) {
      _characters = value;
      if (_characters != null)
        changeCharacters(_characters);
      else
        _charactersController.sink.addError("No characters found");
    }).catchError(
        (e) => _charactersController.sink.addError("An error has occurred"));
  }

  Future<void> getCharacters(String url) async {
    CharacterService characterService = CharacterService();

    if (url == null) {
      await characterService.getCharacters(url).then((value) {
        _nextPage = value.next;
        _characters = value.results;
        changeCharacters(_characters);
      }).catchError((e) => null);
    } else {
      await characterService.getCharacters(url).then((value) {
        _nextPage = value.next;
        _characters += value.results;
        changeCharacters(_characters);
      }).catchError((e) => null);
    }

    characterService.dispose();
  }

  bool nextPage() {
    if (_nextPage != null) {
      getCharacters(_nextPage);
      return true;
    }
    return false;
  }

  Future<Map<String, String>> addFavorite(CharacterModel character) async {
    _loadingController.sink.add(true);
    // Declare services

    CharacterLocalService characterLS = CharacterLocalService();
    CharacterFavoriteService characterFavS = CharacterFavoriteService();
    FavoritesErrorService favES = FavoritesErrorService();

    character.favorite = 'true'; // set character favorite with true

    await characterLS.add(character); // update from local characters list

    changeCharacters(_characters); // update from stream

    // Declare result
    Map<String, String> result;
    // If have a connection
    if (await conn.verifyConnection()) {
      // Request api starwarsfavorites
      result = await characterFavS.add(getID(character.url));

      if (result["status"] != "success") {
        // add to list errors
        await favES.add(character);
      }
    } else {
      // else have a connection
      result = {"status": "success", "message": "May the force be with you"};
      // add to list errors
      await favES.add(character);
    }

    // Dispose
    characterLS.dispose();
    characterFavS.dispose();
    favES.dispose();

    _loadingController.sink.add(false);

    return result;
  }

  Future<bool> removeFavorite(CharacterModel character) async {
    // Declare services
    CharacterLocalService characterLS = CharacterLocalService();

    character.favorite = 'false'; // set character favorite with false
    await characterLS.add(character); // update from local characters list

    _characters.forEach((element) {
      if (element.url == character.url) element.favorite = 'false';
    });

    changeCharacters(_characters); // update from stream

    // Dispose
    characterLS.dispose();

    return true;
  }

  Future<void> verifyFavErrors() async {
    // Declare services
    FavoritesErrorService favES = FavoritesErrorService();
    CharacterFavoriteService characterFavS = CharacterFavoriteService();

    // Declare list characters = set favorites errors
    List<CharacterModel> _characters = await favES.getAll();

    // If contains error
    if (_characters.length > 0) {
      print("${_characters.length} character for send");
      FavoritesErrorService favES = FavoritesErrorService();

      _characters.forEach((character) async {
        Map<String, String> result =
            await characterFavS.add(getID(character.url));

        if (result["status"] != "success") {
          await favES.add(character);
        } else {
          await favES.remove(character);
        }
      });
    }
  }

  @override
  void dispose() {
    _loadingController.close();
    _charactersController.close();
    super.dispose();
  }
}
