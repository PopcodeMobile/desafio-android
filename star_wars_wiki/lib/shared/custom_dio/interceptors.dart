import 'package:dio/dio.dart';

class CustomInterceptors extends InterceptorsWrapper {
  @override
  onRequest(RequestOptions options) {
    print('REQUEST(${options.method}) => PATH: ${options.path}');
    return options;
  }

  @override
  onResponse(Response response) {
    print('RESPONSE(${response.statusCode}) => PATH: ${response.request.path}');
    return response;
  }

  @override
  onError(DioError e) {
    print('REQUEST(${e.message}) => PATH: ${e.request.path}');
    return e;
  }
}
