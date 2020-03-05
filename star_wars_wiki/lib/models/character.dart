import 'package:mobx/mobx.dart';
part 'character.g.dart';

class Character = _CharacterBase with _$Character;

abstract class _CharacterBase with Store {
  
  _CharacterBase(
    { 
      this.isFavorite,
      this.name,
      this.gender,
      this.height, 
      this.mass,
      this.hairColor,
      this.skinColor,
      this.eyeColor,
      this.birthYear, 
      this.homeworldReference,
      this.speciesReference
    }
  );

  _CharacterBase.fromMap(Map map) {
    id = map['idColumn'];
    isFavorite = (map['isFavoriteColumn'] == 1) ? true : false;
    name = map['nameColumn'];
    height = map['heightColumn'];
  }

  @observable
  int id;
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
  String homeworldReference;
  @observable
  String homeworld;
  @observable
  List speciesReference;
  @observable
  List species = List<String>();

  Map toMap () {
    Map<String, dynamic> map = {
      'isFavoriteColumn': (isFavorite) ? 1 : 0,
      'nameColumn': name,
      'heightColumn': height,
    };
    if (id != null) {
      map['idColumn'] = id;
    }
    return map;
  }

}
