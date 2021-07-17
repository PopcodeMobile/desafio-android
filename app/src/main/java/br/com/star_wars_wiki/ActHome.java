package br.com.star_wars_wiki;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class ActHome extends AppCompatActivity {

    private ImageButton btnPersonagens;
    private ImageButton btnFavoritos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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