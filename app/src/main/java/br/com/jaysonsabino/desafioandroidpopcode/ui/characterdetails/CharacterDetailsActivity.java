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
import br.com.jaysonsabino.desafioandroidpopcode.database.DatabaseFactory;
import br.com.jaysonsabino.desafioandroidpopcode.entities.Character;
import br.com.jaysonsabino.desafioandroidpopcode.entities.FavoriteCharacter;
import br.com.jaysonsabino.desafioandroidpopcode.entities.Planet;
import br.com.jaysonsabino.desafioandroidpopcode.entities.Specie;
import br.com.jaysonsabino.desafioandroidpopcode.services.FavoritesService;
import br.com.jaysonsabino.desafioandroidpopcode.services.swapi.ServiceFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CharacterDetailsActivity extends AppCompatActivity {

    private Character character;
    private FavoritesService favoritesService;
    private Executor executor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_details);

        character = (Character) getIntent().getSerializableExtra("character");

        if (character == null) {
            finish();
            return;
        }

        ViewDataBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_character_details);
        binding.setVariable(BR.character, character);

        executor = Executors.newFixedThreadPool(2);

        loadHomeWorld();

        loadSpecie();

        loadFavorite();

        favoritesService = new FavoritesService(this, executor);
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

    private void loadFavorite() {
        final CheckBox favorite = findViewById(R.id.detailsCheckIsFavorite);

        favorite.setVisibility(View.INVISIBLE);

        executor.execute(new Runnable() {
            @Override
            public void run() {
                FavoriteCharacter favoriteCharacter = new DatabaseFactory().getDatabase(CharacterDetailsActivity.this).getFavoriteCharacterDAO().getByCharacterId(character.getId());

                favorite.setChecked(favoriteCharacter != null);
                favorite.setVisibility(View.VISIBLE);
            }
        });
    }

    public void onClickFavorite(View view) {
        if (!(view instanceof CheckBox)) {
            return;
        }

        if (((CheckBox) view).isChecked()) {
            favoritesService.setAsFavorite(character);
        } else {
            favoritesService.unsetAsFavorite(character);
        }
    }

}
