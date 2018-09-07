package br.com.jaysonsabino.desafioandroidpopcode.services.swapi;

import br.com.jaysonsabino.desafioandroidpopcode.entities.Planet;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PlanetService {

    @GET("planets/{id}")
    Call<Planet> getPlanetById(@Path("id") int id);
}
