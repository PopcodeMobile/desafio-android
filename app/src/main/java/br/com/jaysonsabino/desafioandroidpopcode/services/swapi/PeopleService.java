package br.com.jaysonsabino.desafioandroidpopcode.services.swapi;

import java.util.List;

import br.com.jaysonsabino.desafioandroidpopcode.entities.Character;
import retrofit2.Call;
import retrofit2.http.GET;

public interface PeopleService {

    @GET("people/")
    Call<List<Character>> getList();

    @GET("people/1")
    Call<Character> getTest();
}
