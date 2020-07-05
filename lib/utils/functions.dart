import 'package:entrevista_pop/utils/urls.dart';
import 'package:hive/hive.dart';
import 'package:http/http.dart' as http;

import 'package:entrevista_pop/providers/character.dart';
import 'package:entrevista_pop/utils/constants.dart';

String capitalize(String string) {
  return "${string[0].toUpperCase()}${string.substring(1)}";
}

Future<void> resendFaieldRequests() async {
  Box<Character> favoritesApiFaieldRequestsBox =
      Hive.box(Constants.favoritesApiFaieldRequestsBox);

  final Box<Character> favoritesBox = Hive.box(Constants.favoritesBox);

  final Box<String> favoritesApiRequestCountBox =
      Hive.box(Constants.favoritesApiRequestCountBox);

  for (String key in favoritesApiFaieldRequestsBox.keys) {
    if (!favoritesBox.containsKey(key)) {
      final apiRequestQuantity = favoritesApiRequestCountBox.length;

      try {
        if (apiRequestQuantity % 2 == 0) {
          await http.post(
            "${AppUrls.FAVORITE_URL}/$key",
            headers: {'Prefer': 'status=400'},
          );
        } else {
          await http.post("${AppUrls.FAVORITE_URL}/$key");
        }
        favoritesApiRequestCountBox.add(key);
        favoritesBox.put(key, favoritesApiFaieldRequestsBox.get(key));
        favoritesApiFaieldRequestsBox.delete(key);
      } catch (e) {
        return;
      }
    }
  }
}
