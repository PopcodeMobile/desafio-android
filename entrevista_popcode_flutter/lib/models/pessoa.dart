class Pessoa {
  int idPessoa;
  int isFavorite;
  int requestFailed;
  String name;
  String height;
  String mass;
  String hairColor;
  String skinColor;
  String eyeColor;
  String birthYear;
  String gender;
  String homeworld;
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
      this.homeworld,
      this.species,
      this.isFavorite,
      this.requestFailed,
      this.specie});

  Pessoa.fromJson(Map<String, dynamic> json, bool localDatabase) {
    if (localDatabase) { //CASO A CONVERSÃO SEJA FEITA ATRAVÉS DO BANCO DE DADOS LOCAL
      name = json['nameColumn'];
      height = json['heightColumn'];
      mass = json['massColumn'];
      hairColor = json['hairColorColumn'];
      skinColor = json['skinColorColumn'];
      eyeColor = json['eyeColorColumn'];
      birthYear = json['birthYearColumn'];
      gender = json['genderColumn'];
      homeworld = json['homeWorldColumn'];
      specie = json['specieColumn'];
      isFavorite = json['isFavoriteColumn'];
      requestFailed = json['requestFailedColumn'];
    } else { //CASO SEJA FEITA ATRAVÉS DA 'RESPONSE' DO JSON
      name = json['name'];
      height = json['height'];
      mass = json['mass'];
      hairColor = json['hair_color'];
      skinColor = json['skin_color'];
      eyeColor = json['eye_color'];
      birthYear = json['birth_year'];
      gender = json['gender'];
      homeworld = json['homeworld'];
      species = json['species'].cast<String>();
    }
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
    data['specieColumn'] = this.specie;
    data['isFavoriteColumn'] = this.isFavorite;
    data['requestFailedColumn'] = this.requestFailed;

    return data;
  }
}
