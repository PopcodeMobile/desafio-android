class People{
  String nome;
  String altura;
  String massa;
  String corCabelo;
  String corPele;
  String corOlho;
  String anoNascimento;
  String genero;
  String planeta;
  List<dynamic> especies;

  People({this.nome, this.altura, this.massa, this.corCabelo, this.corPele,
  this.corOlho, this.anoNascimento, this.genero, this.planeta, this.especies});

  People.fromJson(Map<String, dynamic> json)
        : nome = json['name'],
          altura = json['height'],
          massa = json['mass'],
          corCabelo = json['hair_color'],
          corPele = json['skin_color'],
          corOlho = json['eye_color'],
          anoNascimento = json['birth_year'],
          genero = json['gender'],
          planeta = json['homeworld'],
          especies = json['species'];
}

class Page {
  List<People> results;

  Page({this.results});

  Page.fromJson(Map<String, dynamic> json)
      : results = List.from(json['results'])
      .map((people) => People.fromJson(people))
      .toList();
}