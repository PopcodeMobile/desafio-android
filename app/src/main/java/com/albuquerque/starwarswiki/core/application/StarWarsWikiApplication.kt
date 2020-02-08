package com.albuquerque.starwarswiki.core.application

import android.app.Application
import com.albuquerque.starwarswiki.app.database.AppDatabase
import com.albuquerque.starwarswiki.app.repository.*
import com.albuquerque.starwarswiki.app.usecase.FavoriteUseCase
import com.albuquerque.starwarswiki.app.usecase.GetPeopleUseCase
import com.albuquerque.starwarswiki.app.viewmodel.PeopleViewModel
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
            }

            val viewModelModule = module {
                viewModel { PeopleViewModel(getPeopleUseCase = get(), favoriteUseCase = get()) }
            }

            modules(listOf(databaseModule, repositoryModule, useCaseModule, viewModelModule))

        }

    }

}