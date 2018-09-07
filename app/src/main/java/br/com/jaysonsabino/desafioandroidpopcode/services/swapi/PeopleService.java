package br.com.jaysonsabino.desafioandroidpopcode.services.swapi;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PeopleService {

    @GET("people/")
    Call<PeopleListResponseDTO> getList(@Query("page") int page);
}
