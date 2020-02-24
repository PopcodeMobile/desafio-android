class Character {
  String _name,
      _height,
      _mass,
      _hairColor,
      _skinColor,
      _eyeColor,
      _birthYear,
      _gender,
      _homeworld,
      _url;
  List<String> _films, _species, _vehicles, _starships;

  Character.fromJson(Map<String, dynamic> json) {
    _name = json['name'];
    _height = json['height'];
    _mass = json['mass'];
    _hairColor = json['hair_color'];
    _skinColor = json['skin_color'];
    _eyeColor = json['eye_color'];
    _birthYear = json['birth_year'];
    _gender = json['gender'];
    _homeworld = json['homeworld'];
    _films = json['films'].cast<String>();
    _species = json['species'].cast<String>();
    _vehicles = json['vehicles'].cast<String>();
    _starships = json['starships'].cast<String>();
    _url = json['url'];
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
    data['homeworld'] = this._homeworld;
    data['films'] = this._films;
    data['species'] = this._species;
    data['vehicles'] = this._vehicles;
    data['starships'] = this._starships;
    data['url'] = this._url;
    return data;
  }
}
