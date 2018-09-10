package com.popcode.starwars.di;

import com.popcode.starwars.viewmodel.CharacterViewModel;
import com.popcode.starwars.viewmodel.CharactersViewModel;
import com.popcode.starwars.viewmodel.MainViewModel;

import dagger.Subcomponent;

@Subcomponent
public interface ViewModelSubComponent {
    @Subcomponent.Builder
    interface Builder {
        ViewModelSubComponent build();
    }

    CharacterViewModel characterViewModel();
    CharactersViewModel charactersViewModel();
    MainViewModel mainViewModel();
}
