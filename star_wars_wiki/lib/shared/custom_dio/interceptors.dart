import 'dart:convert';

import 'package:dio/dio.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:star_wars_wiki/shared/constants.dart';

class CustomInterceptors extends InterceptorsWrapper {
  static int count = 0;
  @override
  onRequest(RequestOptions options) async {
    print('REQUEST(${options.method}) => PATH: ${options.path}');
    if (options.uri
        .toString()
        .contains('http://private-782d3-starwarsfavorites.apiary-mock.com')) {
      SharedPreferences prefs = await SharedPreferences.getInstance();

      List<String> pendingFavs =
          prefs.getStringList('pending_favorites') ?? [''];

      String id = options.path
          .substring('$FAVS_BASE_URL/favorite/'.length, options.path.length);
      //Remove da lista de favoritos pendentes, caso esteja nela
      if (pendingFavs.remove(id)) {
        await prefs.setStringList('pending_favorites', pendingFavs);
      }
      // Modifica o header Prefer para o valor status=400 em metade das requisições
      if ((count++) % 2 == 1) {
        options.headers = {"Prefer": 'status=400'};
      }
    }
    return options;
  }

  @override
  onResponse(Response response) {
    print('RESPONSE(${response.statusCode}) => PATH: ${response.request.path}');
    //print('RESPONSE => ${response.data}');
    return response;
  }

  @override
  onError(DioError e) async {
    print('REQUEST(${e.message}) => PATH: ${e.request.path}');
    if (e.request.path
        .contains('http://private-782d3-starwarsfavorites.apiary-mock.com')) {
      SharedPreferences prefs = await SharedPreferences.getInstance();
      // prefs.setString('pending_favorites', e.request.uri.toString());
      var id = e.request.path
          .substring('$FAVS_BASE_URL/favorite/'.length, e.request.path.length);
      if (prefs.getStringList('pending_favorites') == null) {
        prefs.setStringList('pending_favorites', [id]);
      } else {
        List<String> list = prefs.getStringList('pending_favorites');
        list.add(id);
        prefs.setStringList('pending_favorites', list);
      }
      print(id);
      print('Erro: Será feita outra tentativa mais tarde');
    }
    return e;
  }
}
