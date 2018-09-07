package br.com.jaysonsabino.desafioandroidpopcode.viewmodels;

import android.app.Activity;
import android.arch.lifecycle.LiveData;
import android.arch.paging.DataSource;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.widget.Toast;

import java.util.Random;

import br.com.jaysonsabino.desafioandroidpopcode.database.AppDatabase;
import br.com.jaysonsabino.desafioandroidpopcode.database.DatabaseFactory;
import br.com.jaysonsabino.desafioandroidpopcode.entities.Character;
import br.com.jaysonsabino.desafioandroidpopcode.services.swapi.DatabaseSynchronizer;

public class PeopleListViewModel {

    private Activity activity;
    private final AppDatabase database;

    private LiveData<PagedList<Character>> characters;

    public PeopleListViewModel(Activity activity) {
        this.activity = activity;
        database = new DatabaseFactory().getDatabase(activity);

        configurarLivePagedList();
    }

    public LiveData<PagedList<Character>> getCharacters() {
        return characters;
    }

    private void configurarLivePagedList() {
        DataSource.Factory<Integer, Character> factory = database.getCharacterDAO().loadCharactersByNameAsc();
        PagedList.Config config = new PagedList.Config.Builder().setPageSize(10).setEnablePlaceholders(false).build();

        characters = new LivePagedListBuilder<>(factory, config).build();
    }

    /**
     * TODO temporario
     */
    public void apagarPersonagensBancoLocal() {
        database.getCharacterDAO().deleteAll();
    }

    public void sincronizarBancoDePersonagens() {
        if (new Random().nextInt(2) == 1) {
            Toast.makeText(activity, "Simulando banco local desatualizado", Toast.LENGTH_LONG).show();
            database.getCharacterDAO().deleteSomeForTestPurposes();
        }

        new DatabaseSynchronizer(activity, database).syncCharacters();
    }
}
