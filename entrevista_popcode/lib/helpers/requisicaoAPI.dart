import 'dart:convert';

import 'package:dio/dio.dart';
import 'package:entrevista_popcode/models/pessoa.dart';
import 'package:flutter/foundation.dart';
import 'package:http/http.dart' as http;

class RequisicaoApi {
  Future<List<Pessoa>> getPessoas(http.Client client, int page) async {
    http.Response response = await client.get('http://swapi.dev/api/people/');

    if (response.statusCode == 200) {
      return compute(parsePessoa, response.body);
    } else {
      throw Exception('Não foi possível se conectar a API');
    }
  }

  Future<String> getEspecie(String nomeEspecie) async {
    var response = await Dio().get(nomeEspecie);
    return response.data['name'];
  }

  Future<String> getPlaneta(String nomePlaneta) async {
    var response = await Dio().get(nomePlaneta);
    return response.data['name'];
  }
}

List<Pessoa> parsePessoa(String responseBody) {
  final parsed = jsonDecode(responseBody);
  var results = parsed['results'];
  return results.map<Pessoa>((json) => Pessoa.fromJson(json)).toList();
}
