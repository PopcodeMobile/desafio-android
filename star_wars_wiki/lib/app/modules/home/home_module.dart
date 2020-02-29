import 'package:star_wars_wiki/app/modules/detail/detail_bloc.dart';
import 'package:star_wars_wiki/app/modules/home/home_bloc.dart';
import 'package:bloc_pattern/bloc_pattern.dart';
import 'package:flutter/material.dart';
import 'package:star_wars_wiki/app/modules/home/home_page.dart';
import 'package:star_wars_wiki/app/modules/home/home_repository.dart';
import 'package:star_wars_wiki/shared/custom_dio/custom_dio.dart';

import '../../app_module.dart';

class HomeModule extends ModuleWidget {
  @override
  List<Bloc> get blocs => [
        Bloc((i) => HomeBloc(HomeModule.to.getDependency<HomeRepository>())),
        Bloc((i) => DetailBloc(HomeModule.to.getDependency<HomeRepository>())),
      ];

  @override
  List<Dependency> get dependencies => [
        Dependency(
            (i) => HomeRepository(AppModule.to.getDependency<CustomDio>())),
      ];

  @override
  Widget get view => HomePage();

  static Inject get to => Inject<HomeModule>.of();
}
