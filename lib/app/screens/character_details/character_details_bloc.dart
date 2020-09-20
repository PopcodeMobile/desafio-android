import 'package:bloc_pattern/bloc_pattern.dart';
import 'package:rxdart/rxdart.dart';
import 'package:wiki_star_wars/app/models/character_model.dart';
import 'package:wiki_star_wars/app/services/character_local_service.dart';
import 'package:wiki_star_wars/app/services/character_service.dart';
import 'package:wiki_star_wars/app/services/connection_service.dart';

class CharacterDetailsBloc extends BlocBase {
  final _characterController = BehaviorSubject<CharacterModel>();
  Stream<CharacterModel> get outCharacter => _characterController.stream;
  Function(CharacterModel) get changeCharacter => _characterController.sink.add;

  Future<CharacterModel> changeCharacterData(CharacterModel character) async {
    // verify connection
    ConnectionService conn = ConnectionService();
    if (await conn.verifyConnection()) {
      // Declare services
      CharacterService characterService = CharacterService();
      CharacterLocalService characterLS = CharacterLocalService();

      // Search homeworld
      if (character.homeworld.contains('http://') ||
          character.homeworld.contains('https://'))
        character.homeworld = await characterService.getHomeworld(character);

      // Search specie
      if (character.species.contains('http://') ||
          character.species.contains('https://'))
        character.species = await characterService.getSpecie(character);

      // Update for access offline
      await characterLS.update(character);

      // dispose
      characterService.dispose();
      characterLS.dispose();
    } else {
      // Change homeworld
      if (character.homeworld == null ||
          character.homeworld.contains('http://') ||
          character.homeworld.contains('https://')) character.homeworld = '-';

      // Change specie
      if (character.species == null ||
          character.species.contains('http://') ||
          character.species.contains('https://')) character.species = '-';
    }

    // Add character to stream
    await changeCharacter(character);

    return character;
  }

  @override
  void dispose() {
    _characterController.close();
    super.dispose();
  }
}
