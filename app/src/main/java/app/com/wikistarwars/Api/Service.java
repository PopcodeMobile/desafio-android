package app.com.wikistarwars.Api;

import app.com.wikistarwars.Model.PersonagemResponse;
import app.com.wikistarwars.Model.Planet;
import app.com.wikistarwars.Model.Species;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Service {
    @GET("people/")
    Call<PersonagemResponse> getResults(@Query("page") int pageIndex);

    @GET("planets/{id}/")
    Call<Planet> getHomeworld(@Path("id") int planetId);

    @GET("species/{id}/")
    Call<Species> getSpecie(@Path("id") int specieId);

    @GET("people/")
    Call<PersonagemResponse> searchPeople(@Query("search") String peopleName);
}
