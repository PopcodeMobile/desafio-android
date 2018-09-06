package app.com.wikistarwars.Api;

import app.com.wikistarwars.Model.Personagem;
import app.com.wikistarwars.Model.PersonagemResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Service {
    @GET("people/")
    Call<PersonagemResponse> getResults(@Query("page") int pageIndex);

    @GET("people/{id}/")
    Call<Personagem> getPeople(@Path("id") int peopleId);
}
