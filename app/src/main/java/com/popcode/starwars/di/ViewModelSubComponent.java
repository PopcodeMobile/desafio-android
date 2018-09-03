package com.popcode.starwars.di;

import com.popcode.starwars.viewmodel.CharacterListViewModel;
import com.popcode.starwars.viewmodel.CharacterViewModel;

import dagger.Subcomponent;

@Subcomponent
public interface ViewModelSubComponent {
    @Subcomponent.Builder
    interface Builder {
        ViewModelSubComponent build();
    }

    CharacterListViewModel characterListViewModel();
    CharacterViewModel characterViewModel();
}
