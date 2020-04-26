class ColecaoFilmes {
  int count;
  Null next;
  Null previous;
  List<Filme> results;

  ColecaoFilmes({this.count, this.next, this.previous, this.results});

  ColecaoFilmes.fromJson(Map<String, dynamic> json) {
    count = json['count'];
    next = json['next'];
    previous = json['previous'];
    if (json['results'] != null) {
      results = new List<Filme>();
      json['results'].forEach((v) {
        results.add(new Filme.fromJson(v));
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

class Filme {
  String title;
  int episodeId;
  String openingCrawl;
  String director;
  String producer;
  String releaseDate;
  List<String> characters;
  List<String> planets;
  List<String> starships;
  List<String> vehicles;
  List<String> species;
  String created;
  String edited;
  String url;

  Filme(
      {this.title,
      this.episodeId,
      this.openingCrawl,
      this.director,
      this.producer,
      this.releaseDate,
      this.characters,
      this.planets,
      this.starships,
      this.vehicles,
      this.species,
      this.created,
      this.edited,
      this.url});

  Filme.fromJson(Map<String, dynamic> json) {
    title = json['title'];
    episodeId = json['episode_id'];
    openingCrawl = json['opening_crawl'];
    director = json['director'];
    producer = json['producer'];
    releaseDate = json['release_date'];
    characters = json['characters'].cast<String>();
    planets = json['planets'].cast<String>();
    starships = json['starships'].cast<String>();
    vehicles = json['vehicles'].cast<String>();
    species = json['species'].cast<String>();
    created = json['created'];
    edited = json['edited'];
    url = json['url'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['title'] = this.title;
    data['episode_id'] = this.episodeId;
    data['opening_crawl'] = this.openingCrawl;
    data['director'] = this.director;
    data['producer'] = this.producer;
    data['release_date'] = this.releaseDate;
    data['characters'] = this.characters;
    data['planets'] = this.planets;
    data['starships'] = this.starships;
    data['vehicles'] = this.vehicles;
    data['species'] = this.species;
    data['created'] = this.created;
    data['edited'] = this.edited;
    data['url'] = this.url;
    return data;
  }
}