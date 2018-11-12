package com.example.administrador.starwarswiki;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CharacterFragment extends Fragment {

    private CharacterViewModel mViewModel;

    public static CharacterFragment newInstance() {
        return new CharacterFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.character_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new CharacterViewModel(new CharacterRepository());
        // mViewModel = ViewModelProviders.of(this).get(CharacterViewModel.class);
        mViewModel.init(1);
        // TODO: Use the ViewModel
        //mViewModel.getStarWarsCharacter().observe(this, starWarsCharacter -> {
            // Update UI.
        //});
    }

}
