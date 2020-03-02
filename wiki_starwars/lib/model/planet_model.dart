class PlanetModel {
  String _name;

  PlanetModel({String name});

  String get name => _name;
  set name(String name) => _name = name;

  PlanetModel.toMap(Map<String, dynamic> json) {
    _name = json['name'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['name'] = this._name;
    return data;
  }
}