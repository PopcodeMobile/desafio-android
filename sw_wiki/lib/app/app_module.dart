import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:flutter_modular/flutter_modular.dart';
import 'package:sw_wiki/app/app_controller.dart';
import 'package:sw_wiki/app/app_widget.dart';
import 'package:sw_wiki/app/shared/repositories/character_repository.dart';
import 'package:sw_wiki/app/shared/utils/constants.dart';
import 'package:sw_wiki/pages/detail/detail_page.dart';
import 'package:sw_wiki/pages/home/home_controller.dart';
import 'package:sw_wiki/pages/home/home_page.dart';

class AppModule extends MainModule {
  @override
  List<Bind> get binds => [
        Bind((i) => AppController()),
        Bind((i) => HomeController(i.get<CharacterRepository>())),
        Bind((i) => CharacterRepository(i.get<Dio>())),
        Bind((i) => Dio(BaseOptions(baseUrl: URL_BASE))),
      ];

  @override
  List<Router> get routers => [
        Router('/', child: (_, args) => HomePage()),
        Router('/detail', child: (_, args) => DetailPage()),
      ];

  @override
  Widget get bootstrap => AppWidget();
}
