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
    SharedPreferences prefs = await SharedPreferences.getInstance();
    prefs.setString("${response.request.uri}", jsonEncode(response.data));
    return response;
  }

  @override
  onError(DioError error) async {
    if (error.type == DioErrorType.CONNECT_TIMEOUT ||
        error.type == DioErrorType.DEFAULT) {
      SharedPreferences prefs = await SharedPreferences.getInstance();
      var res = jsonDecode(prefs.getString('${error.request.uri}'));
      return Response(data: res, statusCode: 200);
    } else {
      return error;
    }
  }
}
