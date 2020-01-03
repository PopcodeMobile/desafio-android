class Character {
  final int id;
  final String name;
  final String height;
  final String gender;
  final String mass;
  final String hair_color;
  final String skin_color;
  final String eye_color;
  final String birth_year;
  List<dynamic> specie;
  String birth_planet;
  bool isFavorite;

  Character(
      {
      this.id,
      this.name,
      this.height,
      this.gender,
      this.mass,
      this.hair_color,
      this.skin_color,
      this.eye_color,
      this.birth_year,
      this.specie,
      this.birth_planet,
      this.isFavorite = false});

  factory Character.fromJson(Map<String, dynamic> json) {
    return Character(
      //
      name: json['name'],
      height: json['height'],
      mass: json['mass'],
      hair_color: json['hair_color'],
      skin_color: json['skin_color'],
      eye_color: json['eye_color'],
      birth_year: json['birth_year'],
      gender: json['gender'],
      specie: json['species'],
      birth_planet: json['homeworld'],
      isFavorite: false,
    );
  }



  Map<String, dynamic> toJson() => {
      "name":   name,
      "height": height,
      "mass":   mass,
      "hair_color": hair_color,
      "skin_color": skin_color,
      "eye_color": eye_color,
      "birth_year": birth_year,
      "species": specie.isEmpty ? '' :  specie[0],
      "homeworld": birth_planet,
      "gender": gender,
      "isFavorite": isFavorite,
  };

  @override
  String toString() {
    return '$id - $name';
  }

  bool operator ==(o) => o is Character && name == o.name;

  factory Character.fromDb(Map<String, dynamic> map) {
    return Character(
      //
      id: map['id'],
      name: map['name'],
      height: map['height'],
      mass: map['mass'],
      hair_color: map['hair_color'],
      skin_color: map['skin_color'],
      eye_color: map['eye_color'],
      birth_year: map['birth_year'],
      gender: map['gender'],
      specie: [map['species']],
      birth_planet: map['homeworld'],
      isFavorite: map['isFavorite'] == 0 ? false : true,
    );
  }
  Map<String, dynamic> toDb() => {
      "name":   name,
      "height": height,
      "mass":   mass,
      "hair_color": hair_color,
      "skin_color": skin_color,
      "eye_color": eye_color,
      "birth_year": birth_year,
      "species": specie.isEmpty ? '' :  specie[0],
      "homeworld": birth_planet,
      "gender": gender,
      "isFavorite": isFavorite == true ? 1 : 0,
  };

// DATABASE NAMING
   static final String tableName = 'character';
   static final String columnId = 'id';
   static final String columnName = 'name';
   static final String columnHeight = 'height';
   static final String columnGender = 'gender';
   static final String columnMass = 'mass';
   static final String columnHair_color = 'hair_color';
   static final String columnSkin_color = 'skin_color';
   static final String columnEye_color = 'eye_color';
   static final String columnBirth_year = 'birth_year';
   static final String columnIsFavorite = 'isFavorite' ;
   static final String columnSpeciesUrl = 'species';
   static final String columnBirthPlanetUrl = 'homeworld';
}
