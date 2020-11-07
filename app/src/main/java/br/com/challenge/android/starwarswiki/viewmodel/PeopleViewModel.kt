package br.com.challenge.android.starwarswiki.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.challenge.android.starwarswiki.model.data.dto.ApiPerson
import br.com.challenge.android.starwarswiki.model.data.mapper.ApiPersonDataMapper
import br.com.challenge.android.starwarswiki.model.data.mapper.DaoPersonDataMapper
import br.com.challenge.android.starwarswiki.model.data.mapper.ListMapper
import br.com.challenge.android.starwarswiki.model.data.mapper.ListMapperImpl
import br.com.challenge.android.starwarswiki.model.database.PersonEntity
import br.com.challenge.android.starwarswiki.model.domain.Person
import br.com.challenge.android.starwarswiki.model.repository.PersonRepositoryImpl
import br.com.challenge.android.starwarswiki.utils.CheckNetwork
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class PeopleViewModel(private val appContext: Context): ViewModel() {
    // region Declaring properties

    private val listApiMapperImpl: ListMapper<ApiPerson, Person>
    private val listDaoMapperImpl: ListMapper<PersonEntity, Person>
    private val repositoryImpl: PersonRepositoryImpl

    private val _peopleMutableLiveData = MutableLiveData<Resource<ArrayList<Person>>>()
    val peopleLiveData: LiveData<Resource<ArrayList<Person>>> = _peopleMutableLiveData

    private val compositeDisposable = CompositeDisposable()

    // endregion

    init {
        listApiMapperImpl = ListMapperImpl(ApiPersonDataMapper())
        listDaoMapperImpl = ListMapperImpl(DaoPersonDataMapper())
        repositoryImpl = PersonRepositoryImpl(listApiMapperImpl, appContext)

        synchronized(this) {
            fetchPeople()
        }
    }

    private fun fetchPeople() {
        val disposablePeople = repositoryImpl
            .getPeople(1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    _peopleMutableLiveData.postValue(Resource.success(it as ArrayList<Person>))
                },
                {
                    CheckNetwork().checkIfDeviceIsReadyToConnectInternet(appContext)

                    if (CheckNetwork.isNetworkConnected) {
                        _peopleMutableLiveData.postValue(Resource.error(it.message?: "", null))
                    } else {
                        _peopleMutableLiveData.postValue(
                            Resource.error(
                                CheckNetwork.ERROR_INTERNET_NOT_AVAILABLE,
                                null))
                    }
                }
            )

        compositeDisposable.add(disposablePeople)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}