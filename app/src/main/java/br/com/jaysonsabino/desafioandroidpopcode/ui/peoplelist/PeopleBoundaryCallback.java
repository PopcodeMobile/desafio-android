package br.com.jaysonsabino.desafioandroidpopcode.ui.peoplelist;

import android.app.Activity;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;
import android.widget.Toast;

import java.util.List;

import br.com.jaysonsabino.desafioandroidpopcode.database.AppDatabase;
import br.com.jaysonsabino.desafioandroidpopcode.entities.Character;
import br.com.jaysonsabino.desafioandroidpopcode.services.swapi.PeopleListResponseDTO;
import br.com.jaysonsabino.desafioandroidpopcode.services.swapi.PeopleService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PeopleBoundaryCallback extends PagedList.BoundaryCallback<Character> {

    private final Activity activity;
    private final AppDatabase database;
    private final PeopleService peopleService;
    private int actualPage = 0;
    private boolean loading = false;

    PeopleBoundaryCallback(Activity activity, AppDatabase database, PeopleService peopleService) {
        this.activity = activity;
        this.database = database;
        this.peopleService = peopleService;
    }

    private void queryNextPage() {
        Call<PeopleListResponseDTO> call = peopleService.getList(++actualPage);

        call.enqueue(new Callback<PeopleListResponseDTO>() {
            @Override
            public void onResponse(Call<PeopleListResponseDTO> call, Response<PeopleListResponseDTO> response) {
                PeopleListResponseDTO body = response.body();

                if (body == null) {
                    return;
                }

                List<Character> characters = body.getResults();

                // hack to set id of all returned characters
                for (Character character : characters) {
                    character.setIdByUrl();
                }

                database.getCharacterDAO().insert(body.getResults());
                loading = false;
            }

            @Override
            public void onFailure(Call<PeopleListResponseDTO> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public void onZeroItemsLoaded() {
        if (loading) return;
        loading = true;

        Toast.makeText(activity, "onZeroItemsLoaded " + actualPage, Toast.LENGTH_SHORT).show();

        queryNextPage();
    }

    @Override
    public void onItemAtFrontLoaded(@NonNull Character itemAtFront) {
        // nothing to do, already loaded
    }

    @Override
    public void onItemAtEndLoaded(@NonNull Character itemAtEnd) {
        if (loading) return;
        loading = true;

        Toast.makeText(activity, "onItemAtEndLoaded " + actualPage, Toast.LENGTH_SHORT).show();

        queryNextPage();
    }
}
