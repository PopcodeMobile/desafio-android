package com.popcode.starwars.ui.detail;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.popcode.starwars.R;
import com.popcode.starwars.data.local.entity.CharacterElement;
import com.popcode.starwars.data.remote.response.GenericResponse;
import com.popcode.starwars.databinding.ActivityDetailBinding;
import com.popcode.starwars.viewmodel.CharacterViewModel;

import java.util.Objects;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class DetailActivity extends AppCompatActivity  {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private ActivityDetailBinding binding;
    private Integer peopleId;
    private String peopleName;
    private Integer peoplePlanetId;
    private Integer specieId;

    private CharacterViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(CharacterViewModel.class);

        peopleId = getIntent().getExtras().getInt("PEOPLE_ID");
        peopleName = getIntent().getExtras().getString("PEOPLE_NAME");
        peoplePlanetId = getIntent().getExtras().getInt("PEOPLE_PLANET");
        specieId = getIntent().getExtras().getInt("PEOPLE_SPECIE");

        viewModel.setCharacterID(peopleId);
        viewModel.setHomeWorld(peoplePlanetId);
        viewModel.setSpecieId(specieId);

        binding.setViewModel(viewModel);
        binding.setIsLoading(true);

        viewModel.getObservableCharacter().observe(this, new Observer<CharacterElement>() {
            @Override
            public void onChanged(@Nullable CharacterElement characterElement) {
                if (characterElement != null) {
                    binding.setIsLoading(false);
                    viewModel.setCharacter(characterElement);
                } else {
                    binding.setIsLoading(false);
                    viewModel.setCharacterName(peopleName);
                }
            }
        });

        viewModel.getObservableCharacterOffline().observe(this, new Observer<CharacterElement>() {
            @Override
            public void onChanged(@Nullable CharacterElement characterElement) {
                if (characterElement != null) {
                    binding.setIsLoading(false);
                    viewModel.setCharacter(characterElement);
                } else {
                    Snackbar.make(findViewById(R.id.detail_layout), "Connect to the internet to get the Star Wars character.", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        viewModel.getCharacterHomeWorldObservable().observe(this, new Observer<GenericResponse>() {
            @Override
            public void onChanged(@Nullable GenericResponse genericResponse) {
                if (genericResponse != null) {
                    viewModel.setPlanet(genericResponse.name);
                } else {
                    viewModel.setPlanet("--");
                    Snackbar.make(findViewById(R.id.detail_layout), "Could not get the planet.", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        viewModel.getSpecieObservable().observe(this, new Observer<GenericResponse>() {
            @Override
            public void onChanged(@Nullable GenericResponse genericResponse) {
                if (genericResponse != null) {
                    viewModel.setSpecie(genericResponse.name);
                } else {
                    viewModel.setSpecie("--");
                    Snackbar.make(findViewById(R.id.detail_layout), "Could not get the specie.", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}