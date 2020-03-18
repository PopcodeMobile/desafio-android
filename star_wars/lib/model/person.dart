class Person { // Somente alguns itens para teste

  final String id;
  final String name;
  final String height;
  final String mass;
  final String gender;

  Person({this.id, this.name, this.height, this.mass, this.gender});

  factory Person.fromJson(Map<String, dynamic> json, int identifier) {
    return Person(
        id: identifier.toString(),
        name: json["name"],
        mass: json["mass"],
        height: json["height"],
        gender: json["gender"]);
  }
}
