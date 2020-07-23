class Pessoa {
  int id;
  String name;
  String height;
  String mass;
  String hairColor;
  String skinColor;
  String eyeColor;
  String birthYear;
  String gender;
  String homeWorld;
  String specie;
  List<String> species;

  Pessoa(
      {this.name,
      this.height,
      this.mass,
      this.hairColor,
      this.skinColor,
      this.eyeColor,
      this.birthYear,
      this.gender,
      this.homeWorld,
      this.specie,
      this.species});

  Pessoa.fromJson(Map<String, dynamic> json, bool bancoLocal) {
    if (bancoLocal) {
      name = json['nameColumn'];
      height = json['heightColumn'];
      mass = json['massColumn'];
      hairColor = json['hairColorColumn'];
      skinColor = json['skinColorColumn'];
      eyeColor = json['eyeColorColumn'];
      birthYear = json['birthYearColumn'];
      gender = json['genderColumn'];
      homeWorld = json['homeWorldColumn'];
      specie = json['specieColumn'];
    } else {
      name = json['name'];
      height = json['height'];
      mass = json['mass'];
      hairColor = json['hair_color'];
      skinColor = json['skin_color'];
      eyeColor = json['eye_color'];
      birthYear = json['birth_year'];
      gender = json['gender'];
      homeWorld = json['homeworld'];
      if (json['species'] != null) {
        species = json['species'].cast<String>();
      }
    }
  }

  Map<String, dynamic> toJson() {
    return {
      'name': this.name,
      'height': this.name,
      'mass': this.name,
      'hair_color': this.hairColor,
      'skin_color': this.skinColor,
      'eye_color': this.eyeColor,
      'birth_year': this.birthYear,
      'gender': this.gender,
      'homeworld': this.homeWorld,
      'species': this.specie
    };
  }
}
