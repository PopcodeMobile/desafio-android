package com.example.entrevistapopcode;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.example.entrevistapopcode.view.adapters.BottomBarAdapter;
import com.example.entrevistapopcode.views.NoSwipePager;
import com.example.entrevistapopcode.views.fragments.FavoriteFragment;
import com.example.entrevistapopcode.views.fragments.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity   {
    private BottomNavigationView navigationView;
    private final int[] colors = {R.color.amarelo, R.color.amarelo};
    private Toolbar toolbar;
    private NoSwipePager viewPager;
    private AHBottomNavigation bottomNavigation;
    private BottomBarAdapter pagerAdapter;
    private Fragment home;
    private Fragment fav;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        navigationView = (BottomNavigationView) findViewById(R.id.navigationView);
//        navigationView.setOnNavigationItemSelectedListener(this);

//        Fragment musicasFragment = new HomeFragment().newInstance();
//        openFragment(musicasFragment);
//
        bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);

        setupBottomNavStyle();
        setupViewPager();


        addBottomNavigationItems();
        bottomNavigation.setCurrentItem(0);


        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
//                fragment.updateColor(ContextCompat.getColor(MainActivity.this, colors[position]));

                if (!wasSelected)
                    viewPager.setCurrentItem(position);





                return true;
            }
        });

    }


    private void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    private void addBottomNavigationItems() {
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.tab_1, R.drawable.baseline_home_black_18dp, colors[0]);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.tab_2, R.drawable.baseline_favorite_black_18dp, colors[1]);


        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);


    }
    private int fetchColor(@ColorRes int color) {
        return ContextCompat.getColor(this, color);
    }
    private void setupBottomNavStyle() {
        /*
        Set Bottom Navigation colors. Accent color for active item,
        Inactive color when its view is disabled.

        Will not be visible if setColored(true) and default current item is set.
         */
        bottomNavigation.setDefaultBackgroundColor(Color.WHITE);


        // Colors for selected (active) and non-selected items.

        bottomNavigation.setColoredModeColors(fetchColor(R.color.botao_cinza),
                Color.WHITE);
        //  Enables Reveal effect
        bottomNavigation.setColored(true);

        //  Displays item Title always (for selected and non-selected items)
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        bottomNavigation.setTranslucentNavigationEnabled(false);

    }


    private void setupViewPager() {
        viewPager = (NoSwipePager) findViewById(R.id.viewpager);
        viewPager.setPagingEnabled(false);
        pagerAdapter = new BottomBarAdapter(getSupportFragmentManager());

        pagerAdapter.addFragments(new HomeFragment());
        pagerAdapter.addFragments(new FavoriteFragment());


        viewPager.setAdapter(pagerAdapter);
    }



}