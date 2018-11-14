package com.example.administrador.starwarswiki;

import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        //mViewModel = new CharacterViewModel(new CharacterRepository());
        // mViewModel = ViewModelProviders.of(this).get(CharacterViewModel.class);
        //mViewModel.init(1);
        final TextView textViewName = getView().findViewById(R.id.name);
        final TextView textViewMass = getView().findViewById(R.id.mass);
        final TextView textViewHeight = getView().findViewById(R.id.height);
        final TextView textViewGender = getView().findViewById(R.id.gender);

        CharacterRepository characterRepository = new CharacterRepository(getActivity().getApplicationContext());

        Call<PeopleList> call = new RetrofitConfig().getService().getStarWarsCharacters();
        call.enqueue(new Callback<PeopleList>() {
            @Override
            public void onResponse(Call<PeopleList> call, Response<PeopleList> response) {
                textViewName.setText(response.body().getResults().get(9).getName());
                textViewMass.setText(String.valueOf(response.body().getResults().get(9).getMass()));
                textViewHeight.setText(String.valueOf(response.body().getResults().get(9).getHeight()));
                textViewGender.setText(response.body().getResults().get(9).getGender());

                for (StarWarsCharacter starWarsCharacter : response.body().getResults()){
                    characterRepository.insertCharacter(starWarsCharacter);
                    //textViewName.setText(starWarsCharacter.getName());
                }

            }

            @Override
            public void onFailure(Call<PeopleList> call, Throwable t) {
                Log.d("ERROR", "deu erro",t);
            }
        });



/*TODO
se ficar assim deletar CharacterRepository e viewmodel
 */
        //name.setText(mViewModel.getStarWarsCharacter().getValue().getName());
        //mViewModel.getStarWarsCharacter().observe(this, starWarsCharacter -> {
            // Update UI.
        //});
    }

}
