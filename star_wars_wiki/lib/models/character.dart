import 'package:mobx/mobx.dart';
import 'package:star_wars_wiki/database/database_provider.dart';
part 'character.g.dart';

class Character = _CharacterBase with _$Character;

abstract class _CharacterBase with Store {
  
  _CharacterBase(
    {
      this.isFavorite = false,
      this.name,
      this.gender,
      this.height, 
      this.mass,
      this.hairColor,
      this.skinColor,
      this.eyeColor,
      this.birthYear, 
      this.homeworldReference,
      this.speciesReference,
    }
  );

  _CharacterBase.fromMap(Map map) {
    id = map[idColumn];
    isFavorite = (map[isFavoriteColumn] == 1) ? true : false;
    name = map[nameColumn];
    height = map[heightColumn];
    mass = map[massColumn];
    hairColor = map[hairColorColumn];
    skinColor = map[skinColorColumn];
    eyeColor = map[eyeColorColumn];
    birthYear = map[birthYearColumn];
    gender = map[genderColumn];
    homeworldReference = map[homeworldReferenceColumn];
    speciesReference.add(map[speciesReferenceColumn]); // bugado
    homeworld = map[homeworldColumn];
    species.add(map[speciesColumn]);
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
      isFavoriteColumn: (isFavorite) ? 1 : 0,
      nameColumn: name,
      heightColumn: height,
      massColumn:  mass,
      hairColorColumn: hairColor,
      skinColorColumn: skinColor,
      eyeColorColumn: eyeColor,
      birthYearColumn: birthYear,
      genderColumn: gender,
      homeworldColumn: homeworld,
      speciesColumn: species.isEmpty ? 'unknown' : species[0],
      homeworldReferenceColumn: homeworldReference,
      speciesReferenceColumn: speciesReference.isEmpty ? null : speciesReference[0],
    };
    if (id != null)
      map[idColumn] = id;
    return map;
  }

}
