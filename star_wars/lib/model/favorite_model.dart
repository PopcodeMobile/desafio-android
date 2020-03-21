import 'dart:convert';

import 'package:starwars/model/person_model.dart';
import 'package:http/http.dart' as http;

class FavoriteModel {
  Future<String> setFav(String id) async {
    PersonDatabase datab = PersonDatabase();
    Person person = await datab.getPerson(id);
    Map<String, dynamic> tempPerson = person.toJson();
    String message;

    if (tempPerson["favorito"] == null || tempPerson["favorito"] == "0") {
      tempPerson["favorito"] = "1";

      try {
        http.Response response = await http.post(
            "http://private-782d3-starwarsfavorites.apiary-mock.com/favorite/$id");
        if (response.statusCode == 201) {
          tempPerson["enviado"] = "1";
          message = jsonDecode(response.body)["message"];
        } else {
          tempPerson["enviado"] = "0";
          message = "Falha ao enviar para o servidor";
        }
      } catch (e) {
        tempPerson["enviado"] = "0";
        message = "Falha ao enviar para o servidor";
      }
    } else {
      tempPerson["favorito"] = "0";
      tempPerson["enviado"] = "0";
      message = "Removido dos Favoritos";
    }
    person = Person.fromJson(tempPerson);
    await datab.updatePerson(person);
    return message;
  }


  Future<void> favOnline(String id) async {
    PersonDatabase datab = PersonDatabase();
    Person person = await datab.getPerson(id);
    Map<String, dynamic> tempPerson = person.toJson();
    if(tempPerson["favorito"] == "1" && tempPerson["enviado"] == "0"){

    try {
      http.Response response = await http.post(
          "http://private-782d3-starwarsfavorites.apiary-mock.com/favorite/$id");
      if (response.statusCode == 201) {
        tempPerson["enviado"] = "1";
      } else {
        tempPerson["enviado"] = "0";
      }
    } catch (e) {
      tempPerson["enviado"] = "0";
    }
    person = Person.fromJson(tempPerson);
    await datab.updatePerson(person);
  }}
}
