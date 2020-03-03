import 'package:http/http.dart' as HTTP;
import 'dart:convert';
import 'package:wikistarwars/model/person_model.dart';

class SW_FAV{
  String _urlBase = "http://private-782d3-starwarsfavorites.apiary-mock.com";

  favorite(PersonModel personModel) async {
    HTTP.Response response = await HTTP.post(
        _urlBase + "/favorite/${personModel.id}",
        headers: {
          "Content-type": "application/json; charset=UTF-8"
        },
    );
    var body = jsonDecode(response.body);
    return body['message'];
  }

}