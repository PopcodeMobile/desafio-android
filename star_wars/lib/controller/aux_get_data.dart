import 'dart:convert';

import 'package:http/http.dart' as http;

class AuxGetData {
  final http.Response response;

  AuxGetData(this.response);

  getPersonModified() async {
    var decoded = json.decode(this.response.body); //Para atribuir nome ao planeta natal
    http.Response name = await http.get(decoded["homeworld"]);
    var nameDecoded = json.decode(name.body);
    decoded["homeworld"] = nameDecoded["name"];

    List<dynamic> species = decoded["species"];

    decoded["species"] = "";
    for (int index = 0; index < species.length; index++) { // Para atribuir nome das especies
      name = await http.get(species[index]);
      nameDecoded = json.decode(name.body);
      decoded["species"] = "${decoded["species"]} ${nameDecoded["name"]} \n";
    }

    return decoded;
  }
}
