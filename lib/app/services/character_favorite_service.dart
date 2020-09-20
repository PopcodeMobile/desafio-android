import 'dart:convert';
import 'dart:math';

import 'package:bloc_pattern/bloc_pattern.dart';
import 'package:http/http.dart' as http;

class CharacterFavoriteService extends Disposable {
  Future<Map<String, String>> add(String id) async {
    try {
      final String url =
          "http://private-782d3-starwarsfavorites.apiary-mock.com/favorite/$id";

      final Map<String, String> customHeaders = {};

      var random = new Random();
      if (random.nextInt(100) > 50) {
        customHeaders.addAll({"Prefer": "status=400"});
      }

      http.Response response = await http.post(url, headers: customHeaders);

      var json = jsonDecode(response.body);
      Map<String, String> result;
      if (response.statusCode == 201) {
        result = {"status": json["status"], "message": json["message"]};
        return result;
      } else {
        result = {"status": json["error"], "message": json["error_message"]};
      }
      return result;
    } catch (e) {
      print(e.toString());
      Map<String, String> result = {
        "status": "error",
        "message": "Only at the end do you realize the power of the Dark Side."
      };
      return result;
    }
  }

  @override
  void dispose() {}
}
