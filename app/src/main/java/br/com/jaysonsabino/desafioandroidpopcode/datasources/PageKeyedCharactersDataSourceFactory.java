package br.com.jaysonsabino.desafioandroidpopcode.datasources;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;

import br.com.jaysonsabino.desafioandroidpopcode.entities.Character;
import br.com.jaysonsabino.desafioandroidpopcode.services.swapi.PeopleService;
import br.com.jaysonsabino.desafioandroidpopcode.services.swapi.ServiceFactory;

public class PageKeyedCharactersDataSourceFactory extends DataSource.Factory<Integer, Character> {

    private MutableLiveData<PageKeyedCharactersDataSource> sourceLiveData = new MutableLiveData<>();
    private PeopleService peopleService = new ServiceFactory().getPeopleService();

    @Override
    public DataSource<Integer, Character> create() {
        PageKeyedCharactersDataSource dataSource = new PageKeyedCharactersDataSource(peopleService);
        sourceLiveData.postValue(dataSource);
        return dataSource;
    }
}
