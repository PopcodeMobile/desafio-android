class ColecaoNaves {
  int count;
  String next;
  Null previous;
  List<NaveEspacial> results;

  ColecaoNaves({this.count, this.next, this.previous, this.results});

  ColecaoNaves.fromJson(Map<String, dynamic> json) {
    count = json['count'];
    next = json['next'];
    previous = json['previous'];
    if (json['results'] != null) {
      results = new List<NaveEspacial>();
      json['results'].forEach((v) {
        results.add(new NaveEspacial.fromJson(v));
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

class NaveEspacial {
  String name;
  String model;
  String manufacturer;
  String costInCredits;
  String length;
  String maxAtmospheringSpeed;
  String crew;
  String passengers;
  String cargoCapacity;
  String consumables;
  String hyperdriveRating;
  String mGLT;
  String starshipClass;
  List<String> pilots;
  List<String> films;
  String created;
  String edited;
  String url;

  NaveEspacial(
      {this.name,
      this.model,
      this.manufacturer,
      this.costInCredits,
      this.length,
      this.maxAtmospheringSpeed,
      this.crew,
      this.passengers,
      this.cargoCapacity,
      this.consumables,
      this.hyperdriveRating,
      this.mGLT,
      this.starshipClass,
      this.pilots,
      this.films,
      this.created,
      this.edited,
      this.url});

  NaveEspacial.fromJson(Map<String, dynamic> json) {
    name = json['name'];
    model = json['model'];
    manufacturer = json['manufacturer'];
    costInCredits = json['cost_in_credits'];
    length = json['length'];
    maxAtmospheringSpeed = json['max_atmosphering_speed'];
    crew = json['crew'];
    passengers = json['passengers'];
    cargoCapacity = json['cargo_capacity'];
    consumables = json['consumables'];
    hyperdriveRating = json['hyperdrive_rating'];
    mGLT = json['MGLT'];
    starshipClass = json['starship_class'];
    pilots = json['pilots'].cast<String>();
    films = json['films'].cast<String>();
    created = json['created'];
    edited = json['edited'];
    url = json['url'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['name'] = this.name;
    data['model'] = this.model;
    data['manufacturer'] = this.manufacturer;
    data['cost_in_credits'] = this.costInCredits;
    data['length'] = this.length;
    data['max_atmosphering_speed'] = this.maxAtmospheringSpeed;
    data['crew'] = this.crew;
    data['passengers'] = this.passengers;
    data['cargo_capacity'] = this.cargoCapacity;
    data['consumables'] = this.consumables;
    data['hyperdrive_rating'] = this.hyperdriveRating;
    data['MGLT'] = this.mGLT;
    data['starship_class'] = this.starshipClass;
    data['pilots'] = this.pilots;
    data['films'] = this.films;
    data['created'] = this.created;
    data['edited'] = this.edited;
    data['url'] = this.url;
    return data;
  }
}