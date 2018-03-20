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
import com.example.lucvaladao.entrevistapopcode.mvp.home.HomeAdapter;
import com.example.lucvaladao.entrevistapopcode.mvp.home.HomeInteractorImpl;
import com.example.lucvaladao.entrevistapopcode.mvp.home.HomePresenter;
import com.example.lucvaladao.entrevistapopcode.mvp.home.HomePresenterImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment implements FavoriteView, FavoriteAdapterInterface {

    private FavoritePresenter mFavoritePresenter = new FavoritePresenterImpl(
            new FavoriteInteractorImpl(), this
    );
    private Context mContext;
    private HomeAdapter mFavoriteAdapter;
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
            if (characterAux.getName().contains(mFilter)){
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
            mSwipeRefreshLayout.setRefreshing(false);
            showToast("Updated!");
        } else {
            showNoResults();
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showNoResults() {

    }

    @Override
    public void hideNoResults() {

    }

    @Override
    public void goToCharacterDetailFromFavorite(Character character) {

    }
}
