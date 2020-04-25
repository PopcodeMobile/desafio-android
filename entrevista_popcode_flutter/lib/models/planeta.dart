class ColecaoPlanetas {
  int count;
  String next;
  Null previous;
  List<Planeta> results;

  ColecaoPlanetas({this.count, this.next, this.previous, this.results});

  ColecaoPlanetas.fromJson(Map<String, dynamic> json) {
    count = json['count'];
    next = json['next'];
    previous = json['previous'];
    if (json['results'] != null) {
      results = new List<Planeta>();
      json['results'].forEach((v) {
        results.add(new Planeta.fromJson(v));
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

class Planeta {
  String name;
  String rotationPeriod;
  String orbitalPeriod;
  String diameter;
  String climate;
  String gravity;
  String terrain;
  String surfaceWater;
  String population;
  List<String> residents;
  List<String> films;
  String created;
  String edited;
  String url;

  Planeta(
      {this.name,
      this.rotationPeriod,
      this.orbitalPeriod,
      this.diameter,
      this.climate,
      this.gravity,
      this.terrain,
      this.surfaceWater,
      this.population,
      this.residents,
      this.films,
      this.created,
      this.edited,
      this.url});

  Planeta.fromJson(Map<String, dynamic> json) {
    name = json['name'];
    rotationPeriod = json['rotation_period'];
    orbitalPeriod = json['orbital_period'];
    diameter = json['diameter'];
    climate = json['climate'];
    gravity = json['gravity'];
    terrain = json['terrain'];
    surfaceWater = json['surface_water'];
    population = json['population'];
    residents = json['residents'].cast<String>();
    films = json['films'].cast<String>();
    created = json['created'];
    edited = json['edited'];
    url = json['url'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['name'] = this.name;
    data['rotation_period'] = this.rotationPeriod;
    data['orbital_period'] = this.orbitalPeriod;
    data['diameter'] = this.diameter;
    data['climate'] = this.climate;
    data['gravity'] = this.gravity;
    data['terrain'] = this.terrain;
    data['surface_water'] = this.surfaceWater;
    data['population'] = this.population;
    data['residents'] = this.residents;
    data['films'] = this.films;
    data['created'] = this.created;
    data['edited'] = this.edited;
    data['url'] = this.url;
    return data;
  }
}