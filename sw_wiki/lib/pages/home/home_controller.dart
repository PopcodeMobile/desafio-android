import 'package:mobx/mobx.dart';
import 'package:sw_wiki/app/shared/repositories/character_repository.dart';
part 'home_controller.g.dart';

class HomeController = _HomeControllerBase with _$HomeController;

abstract class _HomeControllerBase with Store {
  final CharacterRepository repository;

  @observable
  ObservableFuture characters;

  _HomeControllerBase(this.repository) {
    fetchCharacters();
  }

  @action
  fetchCharacters() {
    characters = repository.getAllCharacters().asObservable();
  }
}
