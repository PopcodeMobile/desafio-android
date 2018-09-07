package br.com.jaysonsabino.desafioandroidpopcode.services;

import android.app.Activity;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Executor;

import br.com.jaysonsabino.desafioandroidpopcode.database.AppDatabase;
import br.com.jaysonsabino.desafioandroidpopcode.database.DatabaseFactory;
import br.com.jaysonsabino.desafioandroidpopcode.entities.Character;
import br.com.jaysonsabino.desafioandroidpopcode.entities.FavoriteCharacter;
import br.com.jaysonsabino.desafioandroidpopcode.services.starwarsfavorites.StarWarsFavoritesResponseDTO;
import br.com.jaysonsabino.desafioandroidpopcode.services.starwarsfavorites.StarWarsFavoritesService;
import br.com.jaysonsabino.desafioandroidpopcode.services.starwarsfavorites.StarWarsFavoritesServiceFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoritesService {

    private AppDatabase database;
    private Executor executor;
    private StarWarsFavoritesService favoritesService;
    private Activity activity;

    public FavoritesService(Activity activity, Executor executor) {
        this.activity = activity;

        database = new DatabaseFactory().getDatabase(activity);
        this.executor = executor;
        favoritesService = new StarWarsFavoritesServiceFactory().getService();
    }

    public void setAsFavorite(Character character) {
        final FavoriteCharacter favoriteCharacter = new FavoriteCharacter(character.getId());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                database.getFavoriteCharacterDAO().insert(favoriteCharacter);

                sendFavoriteToStarWarsFavorites(favoriteCharacter);
            }
        });
    }

    public void unsetAsFavorite(Character character) {
        final FavoriteCharacter favoriteCharacter = new FavoriteCharacter(character.getId());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                database.getFavoriteCharacterDAO().delete(favoriteCharacter);
            }
        });
    }

    private void sendFavoriteToStarWarsFavorites(final FavoriteCharacter favoriteCharacter) {
        Map<String, String> headers = new HashMap<>();

        if (new Random().nextInt(2) == 1) {
            headers.put("Prefer", "status=400");
        }

        Call<StarWarsFavoritesResponseDTO> call = favoritesService.favorite(
                favoriteCharacter.getCharacterId(),
                headers
        );
        call.enqueue(new Callback<StarWarsFavoritesResponseDTO>() {
            @Override
            public void onResponse(Call<StarWarsFavoritesResponseDTO> call, Response<StarWarsFavoritesResponseDTO> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(activity, "Falha ao salvar favorito na api: code " + response.code(), Toast.LENGTH_LONG).show();
                    return;
                }

                StarWarsFavoritesResponseDTO body = response.body();

                if (body == null) {
                    return;
                }

                markFavoriteCharacterAsSynced(favoriteCharacter);

                Toast.makeText(activity, body.toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<StarWarsFavoritesResponseDTO> call, Throwable t) {
                Toast.makeText(activity, "Falha na comunicação com a StarWarsFavorites.", Toast.LENGTH_LONG).show();
                t.printStackTrace();
            }
        });
    }

    private void markFavoriteCharacterAsSynced(final FavoriteCharacter favoriteCharacter) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                favoriteCharacter.setSyncedWithApi(true);
                database.getFavoriteCharacterDAO().update(favoriteCharacter);
            }
        });
    }
}
