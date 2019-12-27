class Character {
  final String name;
  final String height;
  final String gender;
  final String mass;
  final String hair_color;
  final String skin_color;
  final String eye_color;
  final String birth_year;
  List<dynamic> species;
  String birth_planet;
  bool isFavorite;

  Character(
      {this.name,
      this.height,
      this.gender,
      this.mass,
      this.hair_color,
      this.skin_color,
      this.eye_color,
      this.birth_year,
      this.species,
      this.birth_planet,
      this.isFavorite = false});

  factory Character.fromJson(Map<String, dynamic> json) {
    return Character(
      name: json['name'],
      height: json['height'],
      mass: json['mass'],
      hair_color: json['hair_color'],
      skin_color: json['skin_color'],
      eye_color: json['eye_color'],
      birth_year: json['birth_year'],
      gender: json['gender'],
      birth_planet: json['homeworld'],
      species: json['species'],
      isFavorite: false,

    );
  }

  @override
  String toString() {
    return '$name';
  }

bool operator ==(o) => o is Character && name == o.name;



}
