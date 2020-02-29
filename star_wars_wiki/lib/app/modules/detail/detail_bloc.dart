import 'package:bloc_pattern/bloc_pattern.dart';
import 'package:flutter/material.dart';
import 'package:rxdart/rxdart.dart';
import 'package:star_wars_wiki/app/modules/home/home_repository.dart';
import 'package:star_wars_wiki/shared/models/planet_model.dart';
import 'package:star_wars_wiki/shared/models/specie_model.dart';

class DetailBloc extends BlocBase {
  final HomeRepository repo;

  DetailBloc(this.repo);

  var subject = BehaviorSubject<PlanetModel>();
  Sink<PlanetModel> get responseIn => subject.sink;

  Observable<PlanetModel> get responseOut => subject.stream;

  PlanetModel planet;
  List<SpecieModel> speciesList = [];

  void fetchInfo(
      {@required String planetPath, @required List<String> species}) async {
    var planetSubPath = planetPath.substring(20, planetPath.length);
    print('PlanetID = $planetSubPath');
    print('Species: ');
    speciesList = [];
    if (species.isNotEmpty) {
      for (var specie in species) {
        specie = specie.substring(20, specie.length);
        print(specie);
      }
    }

    try {
      var res = await repo.getPlanet(planetSubPath);
      planet = res;
      if (species.isNotEmpty) {
        for (var specie in species) {
          specie = specie.substring(20, specie.length);
          var res = await repo.getSpecie(specie);
          speciesList.add(res);
        }
      } else {
        speciesList = [];
      }
      responseIn.add(res);
    } catch (e) {
      subject.addError(e);
    }
  }
}
