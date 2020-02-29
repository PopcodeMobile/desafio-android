class PlanetModel {
  String name;

  PlanetModel.fromJson(Map<String, dynamic> json) {
    this.name = json['name'];
  }

  Map<String, dynamic> toJson(PlanetModel planet) {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['name'] = planet.name;
    return data;
  }
}
