class Character {
  int id;
  String name;
  int height;
  int mass;
  String hair;
  String skin;
  String eye;
  String year;
  String gender;
  String planet;
  String species;
  int fav;

  Character({this.id, this.name, this.height, this.mass, this.hair, this.skin,
      this.eye, this.year, this.gender, this.planet, this.species, this.fav});

  factory Character.fromMap(Map<String, dynamic> json) => new Character(
    id: json["id"],
    name: json["name"],
    height: json["height"],
    mass: json["mass"],
    hair: json["hair"],
    skin: json["skin"],
    eye: json["eye"],
    year: json["year"],
    gender: json["gender"],
    planet: json["planet"],
    species: json["species"],
    fav: json["fav"]);

  Map<String, dynamic> toMap() => {
    "id": id,
    "name": name,
    "height": height,
    "mass": mass,
    "hair": hair,
    "skin": skin,
    "eye": eye,
    "year": year,
    "gender": gender,
    "planet": planet,
    "species": species,
    "fav": fav
  };

}