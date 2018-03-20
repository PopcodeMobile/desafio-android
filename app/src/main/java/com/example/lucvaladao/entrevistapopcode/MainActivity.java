package com.example.lucvaladao.entrevistapopcode;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.lucvaladao.entrevistapopcode.entity.Character;
import com.example.lucvaladao.entrevistapopcode.mvp.HomeAdapterInterface;
import com.example.lucvaladao.entrevistapopcode.mvp.favorite.FavoriteFragment;
import com.example.lucvaladao.entrevistapopcode.mvp.home.HomeFragment;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements HomeAdapterInterface {

    private int mCurrentFragment = 0;
    private ArrayList<Integer> navigationList = new ArrayList<>();
    Toolbar mMainToolbar;
    BottomNavigationView mNavigation;
    MaterialSearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Init
        navigationList.add(-1);
        navigationList.add(0);
        mMainToolbar = findViewById(R.id.mainToolbar);
        mNavigation = findViewById(R.id.navigation);
        mSearchView = findViewById(R.id.searchView);

        setSupportActionBar(mMainToolbar);
        addFragment(new HomeFragment(), R.id.containerFrameLayout, false, "");
    }

    @Override
    protected void onStart() {
        super.onStart();

        mNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.navigation_home: {
                        if (navigationList.get(navigationList.size() - 1) == 1){
                            replaceFragment(new HomeFragment(), R.id.containerFrameLayout, true, "");
                            navigationList.add(0);
                        } else {
                            replaceFragment(new HomeFragment(), R.id.containerFrameLayout, false, "");
                        }
                        mCurrentFragment = 0;
                    }
                    case R.id.navigation_favorite: {
                        if (navigationList.get(navigationList.size() - 1) == 0){
                            replaceFragment(new FavoriteFragment(), R.id.containerFrameLayout, true, "");
                            navigationList.add(1);
                        } else {
                            replaceFragment(new FavoriteFragment(), R.id.containerFrameLayout, false, "");
                        }
                        mCurrentFragment = 1;
                    }
                    default:
                }

                return true;
            }
        });

        mSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (mCurrentFragment == 0){
                    onSearchHome(query);
                } else if (mCurrentFragment == 1){
                    onSearchFavorite(query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        mSearchView.setMenuItem(item);
        return true;

    }

    @Override
    public void onBackPressed() {
        if (mSearchView.isSearchOpen()){
            mSearchView.closeSearch();
        } else if (navigationList.get(navigationList.size() - 2) == 0){
            navigationList.remove(navigationList.size() - 1);
            mNavigation.setSelectedItemId(R.id.navigation_home);
        } else if (navigationList.get(navigationList.size() - 2) == 1){
            navigationList.remove(navigationList.size() - 1);
            mNavigation.setSelectedItemId(R.id.navigation_favorite);
        } else if (navigationList.get(navigationList.size() - 2) == -1 ||
                navigationList.get(navigationList.size() - 1) == -1){
            finish();
        } else {
            super.onBackPressed();
        }

    }

    private void onSearchHome (String query){

    }

    private void onSearchFavorite(String query){

    }

    private void addFragment(Fragment fragment,
                             int containerId,
                             boolean hasBackStack,
                             String tag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(containerId, fragment, tag);
        if (hasBackStack) {
            transaction.addToBackStack(String.valueOf(fragment.getId()));
        }
        transaction.commit();
    }

    private void replaceFragment(Fragment fragment,
                                 int containerId,
                                 boolean hasBackStack,
                                 String tag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(containerId, fragment, tag);
        if (hasBackStack) {
            transaction.addToBackStack(String.valueOf(fragment.getId()));
        }
        transaction.commit();
    }

    @Override
    public void goToCharacterDetail(Character character) {

    }
}
