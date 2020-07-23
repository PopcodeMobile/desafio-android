import 'dart:convert';

import 'package:dio/dio.dart';
import 'package:entrevista_popcode/models/pessoa.dart';
import 'package:flutter/foundation.dart';
import 'package:http/http.dart' as http;

class RequisicaoApi {
  final api = "http://swapi.dev/api/people/";

  Future<List<Pessoa>> getPessoas(http.Client client, int page) async {
    // http.Response response =
    //     await client.get('http://swapi.dev/api/people/?page=$page&format=json');

    // if (response.statusCode == 200) {
    //   return compute(parsePessoa, response.body);
    // } else {
    //   throw Exception('Não foi possível se conectar a API');
    // }

    http.Response response;
    try {
      response = await client.get(api + "?page=$page&format=json");
    } catch (e) {
      throw new Exception("Não foi possível obter informações da API!");
    }
    return compute(parsePessoa, response.body);
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

List<Pessoa> parsePessoa(String response) {
  if (response != "") {
    final parsed = jsonDecode(response);
    var results = parsed['results'];
    return results.map<Pessoa>((json) => Pessoa.fromJson(json, false)).toList();
  }
  return null;
}
