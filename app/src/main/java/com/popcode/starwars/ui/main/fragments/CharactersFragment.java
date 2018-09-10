package com.popcode.starwars.ui.main.fragments;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.popcode.starwars.R;
import com.popcode.starwars.data.local.entity.CharacterElement;
import com.popcode.starwars.data.remote.response.CharactersResponse;
import com.popcode.starwars.databinding.FragmentCharactersBinding;
import com.popcode.starwars.di.Injectable;
import com.popcode.starwars.ui.common.listeners.EndlessRecyclerViewScrollListener;
import com.popcode.starwars.ui.detail.DetailActivity;
import com.popcode.starwars.ui.main.CharacterClickCallback;
import com.popcode.starwars.ui.main.CharacterListAdapter;
import com.popcode.starwars.viewmodel.CharactersViewModel;

import java.util.List;

import javax.inject.Inject;

import static com.popcode.starwars.util.Converters.getIdFromUrl;

public class CharactersFragment extends Fragment implements Injectable {

    public static final String TAG = "CharactersFragment";


    private FragmentCharactersBinding binding;

    private CharacterListAdapter characterListAdapter;
    private CharacterListAdapter searchListAdapter;
    private EndlessRecyclerViewScrollListener scrollListener;
    private CharactersViewModel viewModel;
    private Integer pageCount = 1;
    private Boolean canShowLoading = true;
    private Boolean offline = false;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    /**
     * Method called when fragment was created.
     *
     * @param inflater layout to inflate
     * @param container base for layout
     * @param savedInstanceState state
     * @return layout inflated with fragment
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate this data binding layout
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_characters, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        characterListAdapter = new CharacterListAdapter(characterClickCallback);
        searchListAdapter = new CharacterListAdapter(characterClickCallback);
        binding.characterList.setAdapter(characterListAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        binding.characterList.setLayoutManager(linearLayoutManager);
        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if (canShowLoading)
                    if (!offline)
                        binding.setIsLoadingCharacters(true);
            }
        };

        binding.setIsMainLoading(true);
        binding.characterList.addOnScrollListener(scrollListener);

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CharactersViewModel.class);

        viewModel.getOfflineCharactersRepository().observe(this, new Observer<List<CharacterElement>>() {
            @Override
            public void onChanged(@Nullable List<CharacterElement> characters) {
                if (characters != null) {
                    binding.setIsLoadingCharacters(false);
                    binding.setIsMainLoading(false);
                    if (offline){
                        if (characters.size() == 0){
                            Snackbar.make(getActivity().findViewById(R.id.main_layout), "Connect to the internet to get characters.", Snackbar.LENGTH_LONG).show();
                        }
                        characterListAdapter.setCharacterList(characters);
                    }
                }
            }
        });

        viewModel.setPage(pageCount);
        viewModel.getOnlineCharactersObservable().observe(this, new Observer<CharactersResponse>() {
            @Override
            public void onChanged(@Nullable CharactersResponse charactersResponse) {
                binding.setIsLoadingCharacters(false);
                binding.setIsMainLoading(false);
                if (charactersResponse != null) {
                    viewModel.setOfflineCharacters(charactersResponse.results);
                    characterListAdapter.addCharactersList(charactersResponse.results);
                    if (charactersResponse.next != null){
                        pageCount += 1;
                        // Log.d(TAG, pageCount.toString());
                        viewModel.setPage(pageCount);
                    }
                    else {
                        canShowLoading = false;
                    }
                } else {
                    offline = true;
                    viewModel.setOfflineCharacters(null);
                }
            }

        });
    }

    private final CharacterClickCallback characterClickCallback = character -> {
        if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
            Intent intent = new Intent(getContext(), DetailActivity.class);
            intent.putExtra("PEOPLE_ID", getIdFromUrl(character.url));
            intent.putExtra("PEOPLE_NAME", character.name);
            intent.putExtra("PEOPLE_PLANET", getIdFromUrl(character.homeworld));
            if (character.species == null){
                if (character.specie != null)
                    intent.putExtra("PEOPLE_SPECIE", getIdFromUrl(character.specie));
            } else {
                try{
                    intent.putExtra("PEOPLE_SPECIE", getIdFromUrl(character.species.get(0)));
                } catch (Exception e){
                    intent.putExtra("PEOPLE_SPECIE", 0);
                }
            }
            startActivity(intent);
        }
    };
}
