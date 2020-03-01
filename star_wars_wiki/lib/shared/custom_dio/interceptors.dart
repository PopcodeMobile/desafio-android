import 'package:dio/dio.dart';

class CustomInterceptors extends InterceptorsWrapper {
  static int count = 0;
  @override
  onRequest(RequestOptions options) {
    print('REQUEST(${options.method}) => PATH: ${options.path}');
    if ((count++) % 2 == 1) {
      options.headers = {
        "Prefer": {'status': 400}
      };
    }
    print('Request Headers: ${options.headers}');
    return options;
  }

  @override
  onResponse(Response response) {
    print('RESPONSE(${response.statusCode}) => PATH: ${response.request.path}');
    //print('RESPONSE => ${response.data}');
    return response;
  }

  @override
  onError(DioError e) {
    print('REQUEST(${e.message}) => PATH: ${e.request.path}');
    return e;
  }
}
