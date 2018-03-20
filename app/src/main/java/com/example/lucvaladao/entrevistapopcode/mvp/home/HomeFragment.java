package com.example.lucvaladao.entrevistapopcode.mvp.home;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.lucvaladao.entrevistapopcode.R;
import com.example.lucvaladao.entrevistapopcode.entity.Character;

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

    public static Fragment newInstance (String query){
        HomeFragment fragment = new HomeFragment();
        fragment.mFilter = query;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
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
    public void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void fillAdapter(List<Character> characterList) {

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
}
