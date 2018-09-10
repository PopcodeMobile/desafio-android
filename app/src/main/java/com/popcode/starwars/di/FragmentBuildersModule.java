package com.popcode.starwars.di;

import com.popcode.starwars.ui.main.fragments.CharactersFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract CharactersFragment contributeCharactersFragment();
}
