import 'package:dio/dio.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:star_wars_wiki/shared/constants.dart';

import '../constants.dart';

class CustomInterceptors extends InterceptorsWrapper {
  static int count = 0;
  @override
  onRequest(RequestOptions options) async {
    print('REQUEST(${options.method}) => PATH: ${options.path}');
    if (options.uri.toString().contains('$FAVS_BASE_URL')) {
      // Modifica o header Prefer para o valor status=400 em metade das requisições
      if (!options.extra['retry']) {
        if ((count++) % 2 == 1) {
          options.headers = {"Prefer": 'status=400'};
        }
      }
    }
    return options;
  }

  @override
  onResponse(Response response) async {
    print('RESPONSE(${response.statusCode}) => PATH: ${response.request.path}');
    if (response.request.path.contains(FAVS_BASE_URL)) {
      SharedPreferences prefs = await SharedPreferences.getInstance();
      List<String> pendingFavs =
          prefs.getStringList('pending_favorites') ?? [''];

      String id = response.request.path.substring(
          '$FAVS_BASE_URL/favorite/'.length, response.request.path.length);

      //Remove da lista de favoritos pendentes, caso esteja nela
      if (pendingFavs.remove(id)) {
        await prefs.setStringList('pending_favorites', pendingFavs);
      }
    }
    return response;
  }

  @override
  onError(DioError e) async {
    print('REQUEST(${e.message}) => PATH: ${e.request.path}');
    if (e.request.path.contains('$FAVS_BASE_URL')) {
      SharedPreferences prefs = await SharedPreferences.getInstance();
      // prefs.setString('pending_favorites', e.request.uri.toString());
      var id = e.request.path
          .substring('$FAVS_BASE_URL/favorite/'.length, e.request.path.length);
      if (prefs.getStringList('pending_favorites') == null) {
        prefs.setStringList('pending_favorites', [id]);
      } else {
        List<String> list = prefs.getStringList('pending_favorites');

        if (!list.contains(id)) {
          list.add(id);

          prefs.setStringList('pending_favorites', list);
        }
        print(list);
      }

      print('Erro: Será feita outra tentativa mais tarde');
    }
    return e;
  }
}
