import 'dart:async';
import 'dart:convert';
import 'package:entrevista_popcode_flutter/models/pessoa.dart';
import 'package:flutter/foundation.dart';
import 'package:http/http.dart' as http;
import 'package:dio/dio.dart';


class Requisicao {
  final starWarsAPi = "http://swapi.dev/api/people/";

  Future<List<Pessoa>> getPersonagens(
      http.Client client, int pageNumber) async {
    http.Response response;
    try {
      response =
          await client.get(starWarsAPi + "?page=$pageNumber&format=json");
    } catch (e) {
      throw new Exception("Não foi possível obter informações da API!");
    }
    return compute(parsePersonagem, response.body);
  }

  Future<String> getNomePlaneta(String planetaPersonagem) async {
    final response = await Dio().get(planetaPersonagem);
    return response.data['name'];
  }
  Future<String> getNomeEspecie(String especiePersonagem) async {
    final response = await Dio().get(especiePersonagem);
    return response.data['name'];
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
