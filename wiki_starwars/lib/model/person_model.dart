class PersonModel {
  int _id;
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
  String _favorite;
  String _url;

  PersonModel();

  int get id => _id;
  set id(int id) => _id = id;

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

  String get favorite => _favorite;
  set favorite(String favorite) => _favorite = favorite;

  String get url => _url;
  set url(String url) => _url = url;

  PersonModel.fromMap(Map<String, dynamic> map) {
    this._id = map['id'];
    this._name = map['name'];
    this._height = map['height'];
    this._mass = map['mass'];
    this._hairColor = map['hair_color'];
    this._skinColor = map['skin_color'];
    this._eyeColor = map['eye_color'];
    this._birthYear = map['birth_year'];
    this._gender = map['gender'];
    this._homeWorld = map['homeworld'];
    this._species = map['species'];
    this._favorite = map['favorite'];
    this._url = map['url'];
  }

  Map toMap(){
    Map<String, dynamic> newPerson = {
      "name": this._name,
      "height": this._height,
      "mass": this._mass,
      "hair_color": this._hairColor,
      "skin_color": this._skinColor,
      "eye_color": this._eyeColor,
      "birth_year": this._birthYear,
      "gender": this._gender,
      "homeworld": this._homeWorld,
      "species": this._species,
      "favorite": this._favorite,
      "url": this._url,
    };

    if (this.id != null){
      newPerson["id"] = this._id;
    }

    return newPerson;
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
    data['homeworld'] = this._homeWorld;
    data['species'] = this._species;
    data['favorite'] = this._favorite;
    data['url'] = this._url;
    return data;
  }
}
