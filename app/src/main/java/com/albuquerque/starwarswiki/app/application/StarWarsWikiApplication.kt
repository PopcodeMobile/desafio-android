package com.albuquerque.starwarswiki.app.application

import android.app.Application
import br.albuquerque.data.AppDatabase
import br.albuquerque.domain.repository.*
import br.albuquerque.domain.usecase.*
import com.albuquerque.starwarswiki.app.viewmodel.FavoritesViewModel
import com.albuquerque.starwarswiki.app.viewmodel.PeopleViewModel
import com.albuquerque.starwarswiki.app.viewmodel.PersonDetailViewModel
import com.facebook.stetho.Stetho
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class StarWarsWikiApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        setupRoom()
        setupStetho()
        setupKoin()
    }

    private fun setupRoom() {
        AppDatabase.getInstance(this)
    }

    private fun setupStetho() {
        Stetho.initialize(
            Stetho.newInitializerBuilder(this).apply {
                enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this@StarWarsWikiApplication))
            }.build()
        )
    }

    private fun setupKoin() {

        startKoin {

            androidContext(this@StarWarsWikiApplication)

            val databaseModule = module {
                single { AppDatabase.getInstance(get()) }
                single { get<AppDatabase>().wikiDAO }
            }

            val repositoryModule = module {
                factory<IWikiLocalDataSource> { WikiLocalRepository(wikiDao = get()) }
                factory<IWikiRemoteDataSource> { WikiRemoteRepository() }
                factory<IWikiRepository> { WikiRepository(local = get(), remote = get()) }
            }

            val useCaseModule = module {
                factory { GetPeopleUseCase(wikiRepository = get()) }
                factory { FavoriteUseCase(wikiRepository = get()) }
                factory { GetSearchUseCase(wikiRepository = get()) }
                factory { GetFavoritesUseCase(wikiRepository = get()) }
                factory { SetTryAgainUseCase(wikiRepository = get()) }
                factory { GetTryAgainUseCase(wikiRepository = get()) }
                factory { GetConfigUseCase(wikiRepository = get()) }
                factory { GetSpeciesUseCase(wikiRepository = get()) }
                factory { GetHomePlanetUseCase(wikiRepository = get()) }
            }


            val viewModelModule = module {
                viewModel {
                    PeopleViewModel(
                        getPeopleUseCase = get(),
                        favoriteUseCase = get(),
                        getSearchUseCase = get(),
                        setTryAgainUseCase = get(),
                        getTryAgainUseCase = get(),
                        getConfigUseCase = get()
                    )
                }
                viewModel {
                    FavoritesViewModel(getFavoritesUseCase = get(), favoriteUseCase = get())
                }

                viewModel {
                    PersonDetailViewModel(
                        getSpeciesUseCase = get(),
                        getHomePlanetUseCase = get(),
                        favoriteUseCase = get(),
                        setTryAgainUseCase = get()
                    )
                }
            }

            modules(listOf(databaseModule, repositoryModule, useCaseModule, viewModelModule))

        }

    }

}