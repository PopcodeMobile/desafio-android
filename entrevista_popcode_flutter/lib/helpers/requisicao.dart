import 'dart:async';
import 'dart:convert';
import 'package:entrevista_popcode_flutter/models/pessoa.dart';
import 'package:flutter/foundation.dart';
import 'package:http/http.dart' as http;

class Requisicao {
  final starWarsAPi = "http://swapi.dev/api/people/";
  Future<List<Pessoa>> getPersonagens(
      http.Client client, int pageNumber) async {
    http.Response resposta;
    try {
      resposta =
          await client.get(starWarsAPi + "?page=$pageNumber&format=json");
    } catch (e) {
      throw new Exception("Não foi possível obter informações da API!");
    }
    return compute(parsePersonagem, resposta.body);
  }
}

List<Pessoa> parsePersonagem(String responseBody) {
  if (responseBody != "") {
    final parsed = jsonDecode(responseBody);
    var results = parsed['results'];
    return results.map<Pessoa>((json) => Pessoa.fromJson(json, false)).toList();
  } else {
    return null;
  }
}
