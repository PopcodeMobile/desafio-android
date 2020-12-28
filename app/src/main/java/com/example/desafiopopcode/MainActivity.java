package com.example.desafiopopcode;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.desafiopopcode.Controllers.BuscaPerson;
import com.example.desafiopopcode.Controllers.SWApi;
import com.example.desafiopopcode.Models.ListaPerson;
import com.example.desafiopopcode.Models.Personagem;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {

    List<String> people;
    ArrayAdapter<String> adpt;
    ListView list;
    int id = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = (ListView) findViewById(R.id.lista);

        SWApi.init();

        SWApi.getApi().getAllPeople(id, new Callback<ListaPerson<Personagem>>() {
            @Override
            public void success(ListaPerson<Personagem> lista, Response response) {
                people = new ArrayList<String>();
                for (Personagem p : lista.results) {
                    people.add(p.toString());
                }

                adpt = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, people);
                list.setAdapter(adpt);
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        adpt.notifyDataSetChanged();
                        int peopleId = (int) id + 1;
                        if (peopleId == 17) {
                            peopleId++;
                        }
                        adpt.notifyDataSetChanged();
                        Intent it = new Intent(MainActivity.this, Detalhes.class);
                        it.putExtra("peopleId", peopleId);
                        startActivity(it);
                    }
                });
                adpt.notifyDataSetChanged();
                list.setOnScrollListener(new EndlessScrollListener());
                adpt.notifyDataSetChanged();
            }

            @Override
            public void failure(RetrofitError error) {
                error.getBody();
            }
        });

        final EditText busca = findViewById(R.id.busca);
        final TextView resposta = findViewById(R.id.resposta);
        Button botaovoltar = findViewById(R.id.button2);
        Button botaobuscar = findViewById(R.id.botaobuscar);
        botaobuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newid = busca.getText().toString();
                int newId = Integer.parseInt(newid);
                SWApi.getApi().getPeople(newId, new Callback<Personagem>() {
                    @Override
                    public void success(Personagem personagem, Response response) {
                        resposta.setVisibility(View.VISIBLE);
                        list.setVisibility(View.INVISIBLE);
                        busca.setVisibility(View.INVISIBLE);
                        botaobuscar.setVisibility(View.INVISIBLE);
                        resposta.setText(personagem.toString());
                        botaovoltar.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        error.getBody();
                    }
                });
            }
        });

        botaovoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resposta.setVisibility(View.INVISIBLE);
                botaovoltar.setVisibility(View.INVISIBLE);
                list.setVisibility(View.VISIBLE);
                busca.setVisibility(View.VISIBLE);
                botaobuscar.setVisibility(View.VISIBLE);
            }
        });
    }


    public class EndlessScrollListener implements AbsListView.OnScrollListener {

        private int visibleThreshold = 82;
        private int previousTotal = 0;
        private boolean loading = true;

        public EndlessScrollListener() {
        }

        public EndlessScrollListener(int visibleThreshold) {
            this.visibleThreshold = visibleThreshold;
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if (loading) {
                if (totalItemCount > previousTotal) {
                    loading = false;
                    previousTotal = totalItemCount;
                    if (id < 9) {
                        id++;
                        SWApi.getApi().getAllPeople(id, new Callback<ListaPerson<Personagem>>() {
                            @Override
                            public void success(ListaPerson<Personagem> lista, Response response) {
                                for (Personagem p : lista.results) {
                                    people.add(p.toString());
                                }
                                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        adpt.notifyDataSetChanged();
                                        int peopleId = (int) id + 1;
                                        if (peopleId >= 17) {
                                            peopleId++;
                                        }
                                        adpt.notifyDataSetChanged();
                                        Intent it = new Intent(MainActivity.this, Detalhes.class);
                                        it.putExtra("peopleId", peopleId);
                                        startActivity(it);
                                    }
                                });
                                adpt.notifyDataSetChanged();
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                error.getBody();
                            }
                        });
                        adpt.notifyDataSetChanged();
                    }
                }
            }
            if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
                loading = true;
                adpt.notifyDataSetChanged();
            }
        }

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
        }
    }
}