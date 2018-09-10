package br.com.jaysonsabino.desafioandroidpopcode;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import br.com.jaysonsabino.desafioandroidpopcode.entities.Character;
import br.com.jaysonsabino.desafioandroidpopcode.ui.CharacterDetailsViewModel;

public class CharacterDetailsActivity extends AppCompatActivity {

    private Character.CharacterWithFavorite characterWithFavorite;
    private CharacterDetailsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_details);

        characterWithFavorite = (Character.CharacterWithFavorite) getIntent().getSerializableExtra("characterWithFavorite");

        if (characterWithFavorite == null) {
            finish();
            return;
        }

        ViewDataBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_character_details);
        binding.setVariable(BR.characterWithFavorite, characterWithFavorite);

        viewModel = getViewModel();

        loadHomeWorld();

        loadSpecie();
    }

    private CharacterDetailsViewModel getViewModel() {
        Executor executor = Executors.newFixedThreadPool(2);

        return ViewModelProviders
                .of(this, new CharacterDetailsViewModel.Factory(getApplication(), executor))
                .get(CharacterDetailsViewModel.class);
    }

    private void loadHomeWorld() {
        final TextView tvHomeWorld = findViewById(R.id.detailsCharacterHomeWorld);

        viewModel.getHomeWorld(characterWithFavorite.getCharacter()).observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String homeWorld) {
                tvHomeWorld.setText(homeWorld);
            }
        });
    }

    private void loadSpecie() {
        final TextView tvSpecie = findViewById(R.id.detailsCharacterSpecie);

        viewModel.getSpecie(characterWithFavorite.getCharacter()).observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String specie) {
                tvSpecie.setText(specie);
            }
        });
    }

    public void onClickFavorite(View view) {
        if (!(view instanceof CheckBox)) {
            return;
        }

        if (((CheckBox) view).isChecked()) {
            viewModel.setAsFavorite(characterWithFavorite.getCharacter());
        } else {
            viewModel.unsetAsFavorite(characterWithFavorite.getCharacter());
        }
    }

}
