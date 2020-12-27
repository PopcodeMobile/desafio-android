package com.example.desafiopopcode;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.desafiopopcode.Controllers.SWApi;
import com.example.desafiopopcode.Models.ListaPerson;
import com.example.desafiopopcode.Models.Personagem;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {

    int id = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textView = findViewById(R.id.textView);
        SWApi.init();

        SWApi.getApi().getAllPeople(id, new Callback<ListaPerson<Personagem>>() {
            @Override
            public void success(ListaPerson<Personagem> lista, Response response) {
                textView.setText(lista.toString());
            }

            @Override
            public void failure(RetrofitError error) {
                textView.setText("Error kkkkk");
            }
        });

        Button btn = findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id++;
                SWApi.getApi().getAllPeople(id, new Callback<ListaPerson<Personagem>>() {
                    @Override
                    public void success(ListaPerson<Personagem> lista, Response response) {
                        textView.setText(lista.toString());
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        textView.setText("Error kkkkk");
                    }
                });

            }
        });

    }
}