package br.com.jaysonsabino.desafioandroidpopcode.ui;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;

import br.com.jaysonsabino.desafioandroidpopcode.database.AppDatabase;
import br.com.jaysonsabino.desafioandroidpopcode.database.DatabaseFactory;
import br.com.jaysonsabino.desafioandroidpopcode.entities.Character;
import br.com.jaysonsabino.desafioandroidpopcode.entities.Planet;
import br.com.jaysonsabino.desafioandroidpopcode.entities.Specie;
import br.com.jaysonsabino.desafioandroidpopcode.repository.FavoritesRepository;
import br.com.jaysonsabino.desafioandroidpopcode.repository.PeopleRepository;
import br.com.jaysonsabino.desafioandroidpopcode.services.starwarsfavorites.StarWarsFavoritesService;
import br.com.jaysonsabino.desafioandroidpopcode.services.starwarsfavorites.StarWarsFavoritesServiceFactory;
import br.com.jaysonsabino.desafioandroidpopcode.services.swapi.PeopleService;
import br.com.jaysonsabino.desafioandroidpopcode.services.swapi.ServiceFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CharacterDetailsViewModel extends ViewModel {

    private final PeopleRepository peopleRepository;
    private final FavoritesRepository favoritesRepository;

    private CharacterDetailsViewModel(PeopleRepository peopleRepository, FavoritesRepository favoritesRepository) {
        this.peopleRepository = peopleRepository;
        this.favoritesRepository = favoritesRepository;
    }

    public void setAsFavorite(Character character) {
        favoritesRepository.setAsFavorite(character);
    }

    public void unsetAsFavorite(Character character) {
        favoritesRepository.unsetAsFavorite(character);
    }

    public MutableLiveData<String> getHomeWorld(Character character) {
        final MutableLiveData<String> homeWorld = new MutableLiveData<>();

        Integer homeworldId = character.getHomeworldId();
        if (homeworldId == null) {
            return homeWorld;
        }

        // TODO criar repositorio e receber no construtor
        Call<Planet> call = new ServiceFactory().getPlanetService().getPlanetById(homeworldId);
        call.enqueue(new Callback<Planet>() {
            @Override
            public void onResponse(Call<Planet> call, Response<Planet> response) {
                Planet body = response.body();
                if (body == null) {
                    return;
                }

                homeWorld.setValue(body.getName());
            }

            @Override
            public void onFailure(Call<Planet> call, Throwable t) {
                t.printStackTrace();
            }
        });

        return homeWorld;
    }

    public MutableLiveData<String> getSpecie(Character character) {
        final MutableLiveData<String> specie = new MutableLiveData<>();

        Integer specieId = character.getSpecie();
        if (specieId == null) {
            return specie;
        }

        // TODO criar repositorio e receber no construtor
        Call<Specie> call = new ServiceFactory().getSpecieService().getSpecieById(specieId);
        call.enqueue(new Callback<Specie>() {
            @Override
            public void onResponse(Call<Specie> call, Response<Specie> response) {
                Specie body = response.body();
                if (body == null) {
                    return;
                }

                specie.setValue(body.getName());
            }

            @Override
            public void onFailure(Call<Specie> call, Throwable t) {
                t.printStackTrace();
            }
        });

        return specie;
    }

    public static class Factory implements ViewModelProvider.Factory {

        private Application app;
        private Executor executor;
        private AppDatabase database;
        private PeopleService peopleService;
        private StarWarsFavoritesService favoritesService;

        public Factory(Application app, Executor executor) {
            this.app = app;
            this.executor = executor;
            database = new DatabaseFactory().getDatabase(app);
            peopleService = new ServiceFactory().getPeopleService();
            favoritesService = new StarWarsFavoritesServiceFactory().getService();
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            PeopleRepository peopleRepository = new PeopleRepository(app, database, peopleService, executor);
            FavoritesRepository favoritesRepository = new FavoritesRepository(app, database, favoritesService, executor);

            return (T) new CharacterDetailsViewModel(peopleRepository, favoritesRepository);
        }
    }
}
