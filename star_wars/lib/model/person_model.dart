class Person {

  final String id;
  final String name;
  final String height;
  final String mass;
  final String hairColor;
  final String skinColor;
  final String eyeColor;
  final String birthYear;
  final String gender;
  final String nomePlaneta;
  final String nomeEspecie;

  Person({this.id, this.name, this.height, this.mass, this.hairColor, this.skinColor, this.eyeColor, this.birthYear, this.gender, this.nomePlaneta, this.nomeEspecie});

  factory Person.fromJson(Map<String, dynamic> json, int identifier) {
    return Person(
        id: identifier.toString(),
        name: json["name"],
        mass: json["mass"],
        height: json["height"],
        hairColor: json["hair_color"],
        skinColor: json["skin_color"],
        eyeColor: json["eye_color"],
        birthYear: json["birth_year"],
        nomePlaneta: json["homeworld"],
        nomeEspecie: json["species"],
        gender: json["gender"]);
  }
}
