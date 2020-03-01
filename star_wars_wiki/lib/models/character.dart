import 'package:mobx/mobx.dart';
part 'character.g.dart';

class Character = _CharacterBase with _$Character;

abstract class _CharacterBase with Store {
  
  _CharacterBase({this.isFavorite, this.name, this.gender, this.height, this.mass});

  @observable
  bool isFavorite;
  @observable
  String name;
  @observable
  String height;
  @observable
  String mass;
  @observable
  String hairColor;
  @observable
  String skinColor;
  @observable
  String eyeColor;
  @observable
  String birthYear;
  @observable
  String gender;
  @observable
  String homeworld;
  @observable
  String species;

  // TRAZER A MUDANÇA PARA CÁ
}