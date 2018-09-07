package br.com.jaysonsabino.desafioandroidpopcode.services.starwarsfavorites;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface StarWarsFavoritesService {

    @POST("favorite/{id}")
    Call<StarWarsFavoritesResponseDTO> favorite(
            @Path("id") int characterId,
            @HeaderMap Map<String, String> headers
    );
}
