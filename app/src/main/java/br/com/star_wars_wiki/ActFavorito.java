package br.com.star_wars_wiki;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import java.util.List;

import br.com.star_wars_wiki.adapter.FavoriteAdapter;
import br.com.star_wars_wiki.entity.Favorite;
import br.com.star_wars_wiki.view_model.FavoriteViewModel;

public class ActFavorito extends AppCompatActivity {

    private ProgressBar progressBarFavorito;
    private RecyclerView recyclerFavoritos;
    private FavoriteViewModel favoriteViewModel;
    private FavoriteAdapter favoriteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_favoritos);

        Toolbar toolbar = findViewById(R.id.toolbar_simples);
        toolbar.setTitle(R.string.act_favoritos);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        progressBarFavorito = findViewById(R.id.progress_bar_favorite);
        recyclerFavoritos = findViewById(R.id.recycler_favoritos);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerFavoritos.setLayoutManager(layoutManager);

        recyclerFavoritos.setVisibility(View.GONE);

        favoriteViewModel = new FavoriteViewModel(getApplication());
        favoriteViewModel.getAllFavorites().observe(this, new Observer<List<Favorite>>() {
            @Override
            public void onChanged(List<Favorite> favorites) {
                progressBarFavorito.setVisibility(View.GONE);
                recyclerFavoritos.setVisibility(View.VISIBLE);

                favoriteAdapter = new FavoriteAdapter(favorites);
                recyclerFavoritos.setAdapter(favoriteAdapter);
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}