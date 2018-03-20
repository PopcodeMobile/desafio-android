package com.example.lucvaladao.entrevistapopcode.mvp.home;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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
public class HomeFragment extends Fragment implements HomeView {

    private HomePresenter mHomePresenter = new HomePresenterImpl(
            new HomeInteractorImpl(), this
    );
    private Context mContext;
    private HomeAdapter mHomeAdapter;
    private String mFilter = "";
    private FrameLayout mHomeProgress, mHomeNoResults;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public static Fragment newInstance (String query){
        HomeFragment fragment = new HomeFragment();
        fragment.mFilter = query;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        mHomeProgress = rootView.findViewById(R.id.homeProgress);
        mHomeNoResults = rootView.findViewById(R.id.homeNoResults);
        mRecyclerView = rootView.findViewById(R.id.homeRecyclerView);
        mSwipeRefreshLayout = rootView.findViewById(R.id.swipeHomeContainer);
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mHomePresenter.forceUpdate();
            }
        });
        mHomePresenter.bindView(this);
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
        mHomePresenter.getCharacterList();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mHomePresenter.unbindView();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void fillAdapter(List<Character> characterList) {
        List<Character> characterListAux = new ArrayList<>();
        for (Character characterAux : characterList){
            if (characterAux.getName().contains (mFilter)){
                characterListAux.add(characterAux);
            }
        }
        if (!characterListAux.isEmpty()){
            hideNoResults();
            try {
                mHomeAdapter = new HomeAdapter(characterListAux, mContext);
            } catch (Exception e) {
                e.printStackTrace();
            }
            mRecyclerView.setAdapter(mHomeAdapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(), DividerItemDecoration.VERTICAL);
            mRecyclerView.addItemDecoration(dividerItemDecoration);
            hideProgress();
            mSwipeRefreshLayout.setRefreshing(false);
        } else {
            showNoResults();
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void showProgress() {
        mHomeProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mHomeProgress.setVisibility(View.GONE);
    }

    @Override
    public void showNoResults() {
        mHomeNoResults.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideNoResults() {
        mHomeNoResults.setVisibility(View.GONE);
    }
}
