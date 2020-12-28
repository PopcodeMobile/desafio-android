package com.example.desafiopopcode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.desafiopopcode.Controllers.FavApi;
import com.example.desafiopopcode.Controllers.SWApi;
import com.example.desafiopopcode.Models.ListaPerson;
import com.example.desafiopopcode.Models.Personagem;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class Detalhes extends AppCompatActivity {

    String listaFav = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);

        FavApi.init();

        final TextView text = findViewById(R.id.text);

        Intent intent = getIntent();
        int peopleId = intent.getIntExtra("peopleId", 0);

        SWApi.getApi().getPeople(peopleId, new Callback<Personagem>() {
            @Override
            public void success(Personagem personagem, Response response) {
                text.setText(personagem.detalhar());
            }

            @Override
            public void failure(RetrofitError error) {
                text.setText("error kkkkkk");
            }
        });

        Button voltar = findViewById(R.id.button);
        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Button favoritar = findViewById(R.id.button3);
        favoritar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FavApi.getApi().favPerson(peopleId, new Callback<Personagem>() {
                    @Override
                    public void success(Personagem personagem, Response response) {
                        listaFav.concat(personagem.toString() + "\n");
                        Intent it = new Intent(Detalhes.this, Favs.class);
                        it.putExtra("lista", listaFav);
                        startActivity(it);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        error.getBody();
                    }
                });
            }
        });

    }
}