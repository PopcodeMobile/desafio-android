package br.com.jaysonsabino.desafioandroidpopcode.ui.characterdetails;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import br.com.jaysonsabino.desafioandroidpopcode.BR;
import br.com.jaysonsabino.desafioandroidpopcode.R;
import br.com.jaysonsabino.desafioandroidpopcode.database.AppDatabase;
import br.com.jaysonsabino.desafioandroidpopcode.database.DatabaseFactory;
import br.com.jaysonsabino.desafioandroidpopcode.entities.Character;
import br.com.jaysonsabino.desafioandroidpopcode.entities.FavoriteCharacter;
import br.com.jaysonsabino.desafioandroidpopcode.entities.Planet;
import br.com.jaysonsabino.desafioandroidpopcode.entities.Specie;
import br.com.jaysonsabino.desafioandroidpopcode.services.starwarsfavorites.StarWarsFavoritesResponseDTO;
import br.com.jaysonsabino.desafioandroidpopcode.services.starwarsfavorites.StarWarsFavoritesService;
import br.com.jaysonsabino.desafioandroidpopcode.services.starwarsfavorites.StarWarsFavoritesServiceFactory;
import br.com.jaysonsabino.desafioandroidpopcode.services.swapi.ServiceFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CharacterDetailsActivity extends AppCompatActivity {

    private Character character;
    private Executor executor;
    private AppDatabase database;
    private StarWarsFavoritesService favoritesService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_details);

        character = (Character) getIntent().getSerializableExtra("character");

        if (character == null) {
            finish();
            return;
        }

        executor = Executors.newFixedThreadPool(1);
        database = new DatabaseFactory().getDatabase(this);
        favoritesService = new StarWarsFavoritesServiceFactory().getService();

        ViewDataBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_character_details);
        binding.setVariable(BR.character, character);

        loadHomeWorld();

        loadSpecie();
    }

    private void loadHomeWorld() {
        Integer homeworldId = character.getHomeworldId();

        if (homeworldId == null) {
            return;
        }

        Toast.makeText(CharacterDetailsActivity.this, "Carregando planeta natal.", Toast.LENGTH_SHORT).show();

        final TextView homeworld = findViewById(R.id.detailsCharacterHomeWorld);

        Call<Planet> call = new ServiceFactory().getPlanetService().getPlanetById(homeworldId);
        call.enqueue(new Callback<Planet>() {
            @Override
            public void onResponse(Call<Planet> call, Response<Planet> response) {
                Planet planet = response.body();
                if (planet == null) {
                    return;
                }

                homeworld.setText(planet.getName());
            }

            @Override
            public void onFailure(Call<Planet> call, Throwable t) {
                Toast.makeText(CharacterDetailsActivity.this, "Falha na consulta do planeta natal.", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    private void loadSpecie() {
        Integer specieId = character.getSpecie();

        if (specieId == null) {
            return;
        }

        Toast.makeText(CharacterDetailsActivity.this, "Carregando espécie.", Toast.LENGTH_SHORT).show();

        final TextView tvSpecie = findViewById(R.id.detailsCharacterSpecie);

        Call<Specie> call = new ServiceFactory().getSpecieService().getSpecieById(specieId);
        call.enqueue(new Callback<Specie>() {
            @Override
            public void onResponse(Call<Specie> call, Response<Specie> response) {
                Specie specie = response.body();
                if (specie == null) {
                    return;
                }

                tvSpecie.setText(specie.getName());
            }

            @Override
            public void onFailure(Call<Specie> call, Throwable t) {
                Toast.makeText(CharacterDetailsActivity.this, "Falha na consulta da espécie.", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    public void onClickFavorite(View view) {
        if (!(view instanceof CheckBox)) {
            return;
        }

        final FavoriteCharacter favoriteCharacter = new FavoriteCharacter(character.getId());
        if (((CheckBox) view).isChecked()) {
            favoritar(favoriteCharacter);
        } else {
            desfavoritar(favoriteCharacter);
        }
    }

    private void favoritar(final FavoriteCharacter favoriteCharacter) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                database.getFavoriteCharacterDAO().insert(favoriteCharacter);

                sendFavoriteToStarWarsFavorites(favoriteCharacter);
            }
        });
    }

    private void sendFavoriteToStarWarsFavorites(final FavoriteCharacter favoriteCharacter) {
        Call<StarWarsFavoritesResponseDTO> call =
                favoritesService.favorite(favoriteCharacter.getCharacterId());
        call.enqueue(new Callback<StarWarsFavoritesResponseDTO>() {
            @Override
            public void onResponse(Call<StarWarsFavoritesResponseDTO> call, Response<StarWarsFavoritesResponseDTO> response) {
                StarWarsFavoritesResponseDTO body = response.body();

                if (body == null) {
                    return;
                }

                markFavoriteCharacterAsSynced(favoriteCharacter);

                Toast.makeText(CharacterDetailsActivity.this, body.toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<StarWarsFavoritesResponseDTO> call, Throwable t) {
                Toast.makeText(CharacterDetailsActivity.this, "Falha na comunicação com a StarWarsFavorites.", Toast.LENGTH_LONG).show();
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

    private void desfavoritar(final FavoriteCharacter favoriteCharacter) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                database.getFavoriteCharacterDAO().delete(favoriteCharacter);
            }
        });
    }
}
