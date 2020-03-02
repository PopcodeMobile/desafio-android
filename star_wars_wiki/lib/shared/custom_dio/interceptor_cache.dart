import 'dart:convert';

import 'package:dio/dio.dart';
import 'package:shared_preferences/shared_preferences.dart';

class CacheInterceptor extends InterceptorsWrapper {
  @override
  onRequest(RequestOptions options) async {
    return options;
  }

  @override
  onResponse(Response response) async {
    if (response.request.uri.toString().contains('https://swapi.co/api')) {
      SharedPreferences prefs = await SharedPreferences.getInstance();
      prefs.setString("${response.request.uri}", jsonEncode(response.data));
    }
    return response;
  }

  @override
  onError(DioError error) async {
    if ((error.type == DioErrorType.CONNECT_TIMEOUT ||
            error.type == DioErrorType.DEFAULT) &&
        error.request.uri.toString().contains('https://swapi.co/api')) {
      SharedPreferences prefs = await SharedPreferences.getInstance();
      if (prefs.containsKey('banana')) {
        var res = jsonDecode(prefs.getString('banana'));
        return Response(data: res, statusCode: 200);
      } else {
        return DioError(error: error);
      }
    } else {
      return error;
    }
  }
}
