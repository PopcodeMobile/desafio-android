package br.com.star_wars_wiki;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.sql.SQLOutput;

import br.com.star_wars_wiki.entity.People;
import br.com.star_wars_wiki.entity.SWModelList;
import br.com.star_wars_wiki.network.StarWarsApi;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ActHome extends AppCompatActivity {

    private ImageButton btnPersonagens;
    private ImageButton btnFavoritos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StarWarsApi.init();

        btnPersonagens = findViewById(R.id.btnPersonagens);
        btnFavoritos = findViewById(R.id.btnFavoritos);
    }

    public void irTelaPersonagem(View view){
        Intent it = new Intent(this, ActPersonagem.class);
        startActivity(it);
    }

    public void itTelaFavorito(View view){
        Intent it = new Intent(this, ActFavorito.class);
        startActivity(it);
    }
}