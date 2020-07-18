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

  factory Pessoa.fromJson(Map<String, dynamic> json) => Pessoa(
        name: json['name'],
        height: json['height'],
        mass: json['mass'],
        hairColor: json['hair_color'],
        skinColor: json['skin_color'],
        eyeColor: json['eye_color'],
        birthYear: json['birth_year'],
        gender: json['gender'],
        homeWorld: json['homeworld'],
        species: json['species'].cast<String>(),
      );

  Map<String, dynamic> toMap() {
    return {
      'name': this.name,
      'height': this.name,
      'mass': this.name,
      'hairColor': this.hairColor,
      'skinColor': this.skinColor,
      'eyeColor': this.eyeColor,
      'birthYear': this.birthYear,
      'gender': this.gender,
      'homeWorld': this.homeWorld,
      'specie': this.specie
    };
  }
}
