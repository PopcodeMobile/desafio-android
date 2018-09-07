package br.com.jaysonsabino.desafioandroidpopcode.datasources;

import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;

import java.io.IOException;
import java.util.ArrayList;

import br.com.jaysonsabino.desafioandroidpopcode.entities.Character;
import br.com.jaysonsabino.desafioandroidpopcode.services.swapi.PeopleService;
import br.com.jaysonsabino.desafioandroidpopcode.services.swapi.dto.PeopleListResponseDTO;
import retrofit2.Call;
import retrofit2.Response;

public class PageKeyedCharactersDataSource extends PageKeyedDataSource<Integer, Character> {

    private PeopleService peopleService;

    PageKeyedCharactersDataSource(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Character> callback) {
        Integer page = 1;
        Integer nextPage = page + 1;
        Call<PeopleListResponseDTO> call = peopleService.getList(page);

        try {
            Response<PeopleListResponseDTO> response = call.execute();
            PeopleListResponseDTO body = response.body();

            if (body != null) {
                callback.onResult(body.getResults(), null, nextPage);
            } else {
                callback.onResult(new ArrayList<Character>(), null, null);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Character> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Character> callback) {
        Integer page = params.key;
        Integer previousPage = page > 1 ? page - 1 : null;
        Integer nextPage = page + 1;
        Call<PeopleListResponseDTO> call = peopleService.getList(params.key);

        try {
            Response<PeopleListResponseDTO> response = call.execute();
            PeopleListResponseDTO body = response.body();

            if (body != null) {
                callback.onResult(body.getResults(), nextPage);
            } else {
                callback.onResult(new ArrayList<Character>(), null);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
