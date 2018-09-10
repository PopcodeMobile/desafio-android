package br.com.jaysonsabino.desafioandroidpopcode.services.swapi;

import br.com.jaysonsabino.desafioandroidpopcode.entities.Specie;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface SpecieService {

    @GET("species/{id}")
    Call<Specie> getSpecieById(@Path("id") int id);
}
