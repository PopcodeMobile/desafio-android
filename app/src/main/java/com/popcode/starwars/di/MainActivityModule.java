package com.popcode.starwars.di;


import com.popcode.starwars.ui.detail.DetailActivity;
import com.popcode.starwars.ui.main.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainActivityModule {
    @ContributesAndroidInjector
    abstract MainActivity contributeMainActivity();

    @ContributesAndroidInjector
    abstract DetailActivity contributeDetailActivity();
}