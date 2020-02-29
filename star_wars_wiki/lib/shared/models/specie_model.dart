class SpecieModel {
  String name;

  SpecieModel.fromJson(Map<String, dynamic> json) {
    this.name = json['name'];
  }

  Map<String, dynamic> toJson(SpecieModel specie) {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['name'] = specie.name;
    return data;
  }
}
