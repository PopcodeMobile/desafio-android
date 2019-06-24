package com.example.entrevistapopcode.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.entrevistapopcode.R;
import com.example.entrevistapopcode.api.entity.entity.Person;
import com.example.entrevistapopcode.db.view.PersonViewModel;
import com.example.entrevistapopcode.view.adapters.Adapter;

import java.util.ArrayList;
import java.util.List;

public class FavoriteFragment extends Fragment {


    public  FavoriteFragment newInstance() {
        return new FavoriteFragment();
    }


    RecyclerView mRecyclerView;
    ArrayList<Person> itens;
    private Adapter mAdapter;
    private PersonViewModel viewModel;
    private SearchView searchView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv);
        searchView=(SearchView) view.findViewById(R.id.search);
        searchView.setVisibility(View.GONE);

        viewModel = ViewModelProviders.of(this).get(PersonViewModel.class);
        setupRecycler();
        return view;
    }

    private void setupRecycler() {

        // Configurando o gerenciador de layout para ser uma lista.
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new Adapter(new ArrayList<>(0));
        mAdapter.setViewModel(viewModel);
        mAdapter.setActivity(getActivity());
        mRecyclerView.setAdapter(mAdapter);


        viewModel.getAllFavorite().observe(this, new Observer<List<Person>>() {
            @Override
            public void onChanged(@Nullable final List<Person> words) {
                mAdapter = new Adapter(words);
                mAdapter.setViewModel(viewModel);
                mAdapter.setActivity(getActivity());
                mRecyclerView.setAdapter(mAdapter);

            }
        });

    }



}