import 'package:wiki_star_wars/app/models/character_model.dart';

class PeopleModel {
  int count;
  String next;
  String previous;
  List<CharacterModel> results;

  PeopleModel({this.count, this.next, this.previous, this.results});

  PeopleModel.fromJson(Map<String, dynamic> json) {
    count = json['count'];
    next = json['next'];
    previous = json['previous'];
    results = null;
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
