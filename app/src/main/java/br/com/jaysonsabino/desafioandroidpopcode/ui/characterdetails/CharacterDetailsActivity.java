package br.com.jaysonsabino.desafioandroidpopcode.ui.characterdetails;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import br.com.jaysonsabino.desafioandroidpopcode.BR;
import br.com.jaysonsabino.desafioandroidpopcode.R;
import br.com.jaysonsabino.desafioandroidpopcode.entities.Character;
import br.com.jaysonsabino.desafioandroidpopcode.entities.Planet;
import br.com.jaysonsabino.desafioandroidpopcode.entities.Specie;
import br.com.jaysonsabino.desafioandroidpopcode.services.swapi.ServiceFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CharacterDetailsActivity extends AppCompatActivity {

    private Character character;

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
}
