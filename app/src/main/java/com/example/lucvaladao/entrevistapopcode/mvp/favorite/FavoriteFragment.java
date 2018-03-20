package com.example.lucvaladao.entrevistapopcode.mvp.favorite;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.lucvaladao.entrevistapopcode.R;
import com.example.lucvaladao.entrevistapopcode.entity.Character;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment implements FavoriteView {

    private FavoritePresenter mFavoritePresenter = new FavoritePresenterImpl(
            new FavoriteInteractorImpl(), this
    );
    private Context mContext;
    private FavoriteAdapter mFavoriteAdapter;
    private String mFilter = "";
    private FrameLayout mFavoriteProgress, mFavoriteNoResults;
    private RecyclerView mRecyclerView;

    public static Fragment newInstance (String query){
        FavoriteFragment fragment = new FavoriteFragment();
        fragment.mFilter = query;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_favorite, container, false);
        mFavoriteProgress = rootView.findViewById(R.id.favoriteProgress);
        mFavoriteNoResults = rootView.findViewById(R.id.favoriteProgressNoResults);
        mRecyclerView = rootView.findViewById(R.id.favoriteRecyclerView);
        mFavoritePresenter.bindView(this);
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onStart() {
        super.onStart();
        showProgress();
        mFavoritePresenter.getCharacterList();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mFavoritePresenter.unbindView();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void fillAdapter(List<Character> characterList) {
        List<Character> characterListAux = new ArrayList<>();
        for (Character characterAux : characterList){
            if (characterAux.getName().contains(mFilter) && characterAux.isFav()){
                characterListAux.add(characterAux);
            }
        }
        if (!characterListAux.isEmpty()){
            hideNoResults();
            try {
                mFavoriteAdapter = new FavoriteAdapter(characterListAux, mContext);
            } catch (Exception e) {
                e.printStackTrace();
            }
            mRecyclerView.setAdapter(mFavoriteAdapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(), DividerItemDecoration.VERTICAL);
            mRecyclerView.addItemDecoration(dividerItemDecoration);
            hideProgress();
            showToast("Favorite View Ready!");
        } else {
            showNoResults();
        }
    }

    @Override
    public void showProgress() {
        mFavoriteProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mFavoriteProgress.setVisibility(View.GONE);
    }

    @Override
    public void showNoResults() {
        mFavoriteNoResults.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideNoResults() {
        mFavoriteNoResults.setVisibility(View.GONE);
    }
}
