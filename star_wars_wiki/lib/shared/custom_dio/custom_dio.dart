import 'package:dio/dio.dart';
import 'package:star_wars_wiki/shared/constants.dart';
import 'package:star_wars_wiki/shared/custom_dio/interceptors.dart';

import 'interceptor_cache.dart';

class CustomDio extends Dio {
  CustomDio() {
    options.baseUrl = BASE_URL;
    interceptors.add(CustomInterceptors());
    interceptors.add(CacheInterceptor());
    options.connectTimeout = 5000;
  }
}
