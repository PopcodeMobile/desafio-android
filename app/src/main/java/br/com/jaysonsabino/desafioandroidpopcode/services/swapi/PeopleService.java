package br.com.jaysonsabino.desafioandroidpopcode.services.swapi;

import br.com.jaysonsabino.desafioandroidpopcode.entities.Character;
import br.com.jaysonsabino.desafioandroidpopcode.services.swapi.dto.PeopleListResponseDTO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PeopleService {

    @GET("people/")
    Call<PeopleListResponseDTO> getList(@Query("page") int page);

    @GET("people/1")
    Call<Character> getTest();
}
