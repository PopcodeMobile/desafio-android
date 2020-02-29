import 'package:mobx/mobx.dart';
part 'character.g.dart';

class Character = _CharacterBase with _$Character;

abstract class _CharacterBase with Store {
  @observable
  String name;
  @observable
  int height;
  @observable
  int mass;
  @observable
  String hairColor;
  @observable
  String skinColor;
  @observable
  String eyeColor;
  @observable
  int birthYear;
  @observable
  String gender;
  @observable
  String homeworld;
  @observable
  String species;
}