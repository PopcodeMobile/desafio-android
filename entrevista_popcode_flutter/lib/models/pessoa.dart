class ColecaoPessoas {
  int count;
  String next;
  String previous;
  List<Pessoa> results;

  ColecaoPessoas({this.count, this.next, this.previous, this.results});

  ColecaoPessoas.fromJson(Map<String, dynamic> json) {
    count = json['count'];
    next = json['next'];
    previous = json['previous'];
    if (json['results'] != null) {
      results = new List<Pessoa>();
      json['results'].forEach((v) {
        results.add(new Pessoa.fromJson(v, false));
      });
    }
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['count'] = this.count;
    data['next'] = this.next;
    data['previous'] = this.previous;
    if (this.results != null) {
      data['results'] = this.results.map((v) => v.toJson()).toList();
    }
    return data;
  }
}

class Pessoa {
  int idPessoa;
  String name;
  String height;
  String mass;
  String hairColor;
  String skinColor;
  String eyeColor;
  String birthYear;
  String gender;
  String homeworld;
  // List<String> films;
  // List<String> species;
  // List<String> vehicles;
  // List<String> starships;
  // String created;
  // String edited;
  // String url;

  Pessoa(
      {this.name,
      this.height,
      this.mass,
      this.hairColor,
      this.skinColor,
      this.eyeColor,
      this.birthYear,
      this.gender,
      this.homeworld});
  // this.films,
  // this.species,
  // this.vehicles,
  // this.starships,
  // this.created,
  // this.edited,
  // this.url});

  Pessoa.fromJson(Map<String, dynamic> json, bool localDatabase) {
    if (localDatabase) {
      name = json['nameColumn'];
      height = json['heightColumn'];
      mass = json['massColumn'];
      hairColor = json['hairColorColumn'];
      skinColor = json['skinColorColumn'];
      eyeColor = json['eyeColorColumn'];
      birthYear = json['birthYearColumn'];
      gender = json['genderColumn'];
      homeworld = json['homeWorldColumn'];
    } else {
      name = json['name'];
      height = json['height'];
      mass = json['mass'];
      hairColor = json['hair_color'];
      skinColor = json['skin_color'];
      eyeColor = json['eye_color'];
      birthYear = json['birth_year'];
      gender = json['gender'];
      homeworld = json['homeworld'];
    }
    // films = json['films'].cast<String>();
    // species = json['species'].cast<String>();
    // vehicles = json['vehicles'].cast<String>();
    // starships = json['starships'].cast<String>();
    // created = json['created'];
    // edited = json['edited'];
    // url = json['url'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();

    data['nameColumn'] = this.name;
    data['heightColumn'] = this.height;
    data['massColumn'] = this.mass;
    data['hairColorColumn'] = this.hairColor;
    data['skinColorColumn'] = this.skinColor;
    data['eyeColorColumn'] = this.eyeColor;
    data['birthYearColumn'] = this.birthYear;
    data['genderColumn'] = this.gender;
    data['homeWorldColumn'] = this.homeworld;

    return data;
  }
}
