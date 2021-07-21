package br.com.star_wars_wiki;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import br.com.star_wars_wiki.network.StarWarsApi;

public class ActHome extends AppCompatActivity {

    private ImageButton btnPersonagens;
    private ImageButton btnFavoritos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);

        //Configuração da Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar_simples);
        toolbar.setTitle(R.string.act_home);
        setSupportActionBar(toolbar);

        StarWarsApi.init();

        btnPersonagens = findViewById(R.id.btnPersonagens);
        btnFavoritos = findViewById(R.id.btnFavoritos);
    }

    public void irTelaPersonagem(View view){
        Intent it = new Intent(this, ActListaPersonagem.class);
        startActivity(it);
    }

    public void itTelaFavorito(View view){
        Intent it = new Intent(this, ActFavorito.class);
        startActivity(it);
    }
}