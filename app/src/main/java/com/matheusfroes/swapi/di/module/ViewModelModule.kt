package com.matheusfroes.swapi.di.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.matheusfroes.swapi.ui.favorites.BookmarkedPeopleViewModel
import com.matheusfroes.swapi.ui.peoplelist.PeopleListViewModel
import com.matheusfroes.swapi.ui.persondetail.PersonDetailViewModel
import com.matheusfroes.swapi.ui.searchpeople.SearchPeopleViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton
import kotlin.reflect.KClass

@Singleton
class ViewModelFactory @Inject constructor(private val viewModels: MutableMap<Class<out ViewModel>, Provider<ViewModel>>) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T = viewModels[modelClass]?.get() as T
}

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@MapKey
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(PeopleListViewModel::class)
    internal abstract fun peopleListViewModel(viewModel: PeopleListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PersonDetailViewModel::class)
    internal abstract fun personDetailViewModel(viewModel: PersonDetailViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BookmarkedPeopleViewModel::class)
    internal abstract fun bookmarkedPeopleViewModel(viewModel: BookmarkedPeopleViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchPeopleViewModel::class)
    internal abstract fun searchPeopleViewModel(viewModel: SearchPeopleViewModel): ViewModel
}