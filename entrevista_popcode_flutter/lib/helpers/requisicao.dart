import 'dart:async';
import 'dart:convert';
import 'package:entrevista_popcode_flutter/helpers/helperFavoritos.dart';
import 'package:entrevista_popcode_flutter/models/pessoa.dart';
import 'package:flutter/foundation.dart';
import 'package:http/http.dart' as http;
import 'package:dio/dio.dart';

class Requisicao {
  final starWarsAPi = "http://swapi.dev/api/people/"; //URL BASE DA API

  //REQUISITA AS PAGINAS DOS PERSONAGENS DA SWAPI USANDO HTTP
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

  //RETORNA UMA STRING FUTURA COM O NOME DO PLANETA. REQUISIÇÃO FEITA ATRAVÉS DO DIO
  Future<String> getNomePlaneta(String planetaPersonagem) async {
    final response = await Dio().get(planetaPersonagem);
    return response.data['name'];
  }

  //RETORNA UMA STRING FUTURA COM O NOME DA ESPÉCIE. REQUISIÇÃO FEITA ATRAVÉS DO DIO
  Future<String> getNomeEspecie(String especiePersonagem) async {
    final response = await Dio().get(especiePersonagem);
    return response.data['name'];
  }

  //REALIZA UMA REQUISIÇÃO POST PARA ADICIONAR UM PERSONAGEM A API AVIARY
  Future<http.Response> adicionaFavoritos(Pessoa personagem) async {
    bool numeroPar = (personagem.name.length % 2 == 0) ? true : false;
    var url = 'http://private-anon-ca41b17eb1-starwarsfavorites.apiary-mock.com/favorite/${personagem.name}';
    var response = await http.post(url, body: jsonEncode(personagem.toJson()), headers: {'Prefer': (numeroPar) ? 'status=400' : ''});
    print('Response status: ${response.statusCode}');
    print('Response body: ${response.body}');
    if (response.statusCode != 201)
      personagem.requestFailed = 1;
    else
      personagem.requestFailed = 0;

    HelperFavoritos().update(personagem);
    return response;
  }
}

//DECODIFICA E CONVERTE O JSON EM UMA LISTA DE PESSOA/PERSONAGEM
List<Pessoa> parsePersonagem(String responseBody) {
  if (responseBody != "") {
    final parsed = jsonDecode(responseBody);
    var results = parsed['results'];
    return results.map<Pessoa>((json) => Pessoa.fromJson(json, false)).toList();
  } else {
    return null;
  }
}
