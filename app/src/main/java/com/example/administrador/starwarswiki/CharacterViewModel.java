package com.example.administrador.starwarswiki;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CharacterViewModel extends ViewModel {
    private List<StarWarsCharacter> starWarsCharactersList;
    private CharacterRepository characterRepository;
    private Webservice webservice;
    private Context context;
    private RetrofitConfig retrofit;
    private PeopleList lastList;

    public CharacterViewModel(Context context){
        this.characterRepository = new CharacterRepository(context.getApplicationContext());
        this.webservice = new RetrofitConfig().getService();
        this.starWarsCharactersList = new ArrayList<StarWarsCharacter>();
        this.context = context;
        retrofit = new RetrofitConfig();
    }

    public List<StarWarsCharacter> getStarWarsCharactersList() {
        return starWarsCharactersList;
    }

    public PeopleList getLastList() {
        return lastList;
    }
    /*public void start(){

        Call<PeopleList> call = webservice.getStarWarsCharacters();
        try {
            PeopleList response = call.execute().body();
            for (StarWarsCharacter starWarsCharacter : response.getResults()) {
                characterRepository.insertCharacter(starWarsCharacter);
                this.starWarsCharacters.add(starWarsCharacter);
                Log.d("aqui","auiiiii");
                //textViewName.setText(starWarsCharacter.getName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    // Append the next page of data into the adapter
    // This method probably sends out a network request and appends new data items to your adapter.
    public void loadNextDataFromApi(int offset, RecyclerViewAdapter mAdapter) {
        // Send an API request to retrieve appropriate paginated data
        //  --> Send the request including an offset value (i.e `page`) as a query parameter.


        Call<PeopleList> call = retrofit.getService().getStarWarsCharacters(offset);
        call.enqueue(new Callback<PeopleList>() {
            @Override
            public void onResponse(Call<PeopleList> call, Response<PeopleList> response) {
                lastList = response.body();
                for (StarWarsCharacter starWarsCharacter : response.body().getResults()) {
                    characterRepository.insertCharacter(starWarsCharacter);
                    starWarsCharactersList.add(starWarsCharacter);
                    //textViewName.setText(starWarsCharacter.getName());
                }
                mAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<PeopleList> call, Throwable t) {
                Log.d("ERROR", "deu erro",t);
            }
        });

        //  --> Deserialize and construct new model objects from the API response
        //  --> Append the new data objects to the existing set of items inside the array of items
        //  --> Notify the adapter of the new items made with `notifyItemRangeInserted()`
    }

    public void loadDatabase(RecyclerViewAdapter mAdapter){
        Call<PeopleList> call = retrofit.getService().getStarWarsCharacters();
        call.enqueue(new Callback<PeopleList>() {
            @Override
            public void onResponse(Call<PeopleList> call, Response<PeopleList> response) {
                lastList = response.body();
                for (StarWarsCharacter starWarsCharacter : response.body().getResults()) {
                    characterRepository.insertCharacter(starWarsCharacter);
                    starWarsCharactersList.add(starWarsCharacter);
                    //textViewName.setText(starWarsCharacter.getName());
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<PeopleList> call, Throwable t) {
                Log.d("ERROR", "deu erro",t);
            }
        });
    }
/*
    public void init(int id) {
        if (this.starWarsCharacter != null) {
            // ViewModel is created on a per-Fragment basis, so the userId
            // doesn't change.
            return;
        }
        this.starWarsCharacter = characterRepository.getStarWarsCharacter(id);

    }

    public LiveData<StarWarsCharacter> getStarWarsCharacter() {
        return starWarsCharacter;
    }
    */
}
