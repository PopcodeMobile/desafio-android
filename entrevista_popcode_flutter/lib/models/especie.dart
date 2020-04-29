class Especie {
  String name;
  String classification;
  String designation;
  String averageHeight;
  String skinColors;
  String hairColors;
  String eyeColors;
  String averageLifespan;
  String homeworld;
  String language;
  List<String> people;
  List<String> films;
  String created;
  String edited;
  String url;

  Especie(
      {this.name,
      this.classification,
      this.designation,
      this.averageHeight,
      this.skinColors,
      this.hairColors,
      this.eyeColors,
      this.averageLifespan,
      this.homeworld,
      this.language,
      this.people,
      this.films,
      this.created,
      this.edited,
      this.url});

  Especie.fromJson(Map<String, dynamic> json) {
    name = json['name'];
    classification = json['classification'];
    designation = json['designation'];
    averageHeight = json['average_height'];
    skinColors = json['skin_colors'];
    hairColors = json['hair_colors'];
    eyeColors = json['eye_colors'];
    averageLifespan = json['average_lifespan'];
    homeworld = json['homeworld'];
    language = json['language'];
    people = json['people'].cast<String>();
    films = json['films'].cast<String>();
    created = json['created'];
    edited = json['edited'];
    url = json['url'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['name'] = this.name;
    data['classification'] = this.classification;
    data['designation'] = this.designation;
    data['average_height'] = this.averageHeight;
    data['skin_colors'] = this.skinColors;
    data['hair_colors'] = this.hairColors;
    data['eye_colors'] = this.eyeColors;
    data['average_lifespan'] = this.averageLifespan;
    data['homeworld'] = this.homeworld;
    data['language'] = this.language;
    data['people'] = this.people;
    data['films'] = this.films;
    data['created'] = this.created;
    data['edited'] = this.edited;
    data['url'] = this.url;
    return data;
  }
}