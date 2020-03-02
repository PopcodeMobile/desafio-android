import 'package:dio/dio.dart';
import 'package:star_wars_wiki/shared/custom_dio/interceptors.dart';

import 'interceptor_cache.dart';

class CustomDio extends Dio {
  CustomDio() {
    interceptors.add(CustomInterceptors());
    interceptors.add(CacheInterceptor());
    options.connectTimeout = 10000;
  }
}
