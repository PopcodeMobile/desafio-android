package com.example.desafiopopcode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

        final TextView text = findViewById(R.id.text);

        SWApi.init();

        SWApi.getApi().getAllPeople(id, new Callback<ListaPerson<Personagem>>() {
            @Override
            public void success(ListaPerson<Personagem> lista, Response response) {
                String s = "";
                for (Personagem p : lista.results) {
                    p.id++;
                    s += "Name: " + p.name + "\n";
                    text.append(s);
                    text.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent nextActivity = new Intent(MainActivity.this, Detalhes.class);
                            nextActivity.putExtra("id", id);
                            startActivity(nextActivity);
                        }
                    });
                    s += "Height: " + p.height + "\n";
                    text.append(s);
                    s += "Gender: " + p.gender + "\n";
                    text.append(s);
                    s += "Mass: " + p.mass + "\n";
                    text.append(s);
                }
                text.setText(s);
               /* nome.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SWApi.getApi().getPeople();
                    }
                });*/
            }

            @Override
            public void failure(RetrofitError error) { text.setText("Error kkkkk");
            }
        });

        /*Button next = findViewById(R.id.button);
        next.setOnClickListener(new View.OnClickListener() {
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
        });*/

    }
}