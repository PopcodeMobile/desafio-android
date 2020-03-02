class PersonModel {

  String _name;
  String _height;
  String _mass;
  String _hairColor;
  String _skinColor;
  String _eyeColor;
  String _birthYear;
  String _gender;
  String _homeWorld;
  String _species;
  bool _favorite;

  PersonModel();

  String get name => _name;
  set name(String name) => _name = name;

  get height => _height;
  set height(String height) => _height = height;

  get mass => _mass;
  set mass(String mass) => _mass = mass;

  String get hairColor => _hairColor;
  set hairColor(String hairColor) => _hairColor = hairColor;

  String get skinColor => _skinColor;
  set skinColor(String skinColor) => _skinColor = skinColor;

  String get eyeColor => _eyeColor;
  set eyeColor(String eyeColor) => _eyeColor = eyeColor;

  String get birthYear => _birthYear;
  set birthYear(String birthYear) => _birthYear = birthYear;

  String get gender => _gender;
  set gender(String gender) => _gender = gender;

  String get homeWorld => _homeWorld;
  set homeWorld(String homeWorld) => _homeWorld = homeWorld;

  String get species => _species;
  set species(String species) => _species = species;

  bool get favorite => _favorite;
  set favorite(bool favorite) => _favorite = favorite;

  PersonModel.toMap(Map<String, dynamic> json) {
    _name = json['name'];
    _height = json['height'];
    _mass = json['mass'];
    _hairColor = json['hair_color'];
    _skinColor = json['skin_color'];
    _eyeColor = json['eye_color'];
    _birthYear = json['birth_year'];
    _gender = json['gender'];
    _homeWorld = json['homeWorld'];
    _species = json['species'];
    _favorite = json['favorite'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['name'] = this._name;
    data['height'] = this._height;
    data['mass'] = this._mass;
    data['hair_color'] = this._hairColor;
    data['skin_color'] = this._skinColor;
    data['eye_color'] = this._eyeColor;
    data['birth_year'] = this._birthYear;
    data['gender'] = this._gender;
    data['homeWorld'] = this._homeWorld;
    data['species'] = this._species;
    data['favorite'] = this._favorite;
    return data;
  }
}
