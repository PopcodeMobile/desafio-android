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

        carregarPlanetaNatal();
    }

    private void carregarPlanetaNatal() {
        Integer homeworldId = character.getHomeworldId();

        if (homeworldId == null) {
            return;
        }

        Toast.makeText(CharacterDetailsActivity.this, "Carregando planeta natal.", Toast.LENGTH_SHORT).show();

        final TextView homeworld = findViewById(R.id.detailsCharacterHomeWorld);

        Call<Planet> callPlanet = new ServiceFactory().getPlanetService().getPlanetById(homeworldId);
        callPlanet.enqueue(new Callback<Planet>() {
            @Override
            public void onResponse(Call<Planet> call, Response<Planet> response) {
                Planet planet = response.body();
                Toast.makeText(CharacterDetailsActivity.this, "Planeta natal carregado.", Toast.LENGTH_SHORT).show();

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
}
