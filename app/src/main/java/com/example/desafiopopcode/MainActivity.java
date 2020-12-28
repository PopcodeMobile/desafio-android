package com.example.desafiopopcode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.desafiopopcode.Controllers.SWApi;
import com.example.desafiopopcode.Models.ListaPerson;
import com.example.desafiopopcode.Models.Personagem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
                adpt.notifyDataSetChanged();
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
                people.add("kkkkkkkkkkkkkk");
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
                                //adpt = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, people);
                                list.setAdapter(adpt);
                                adpt.notifyDataSetChanged();
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
                                //list.setOnScrollListener(new EndlessScrollListener());
                                adpt.notifyDataSetChanged();
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                people.add("kkkkkkkkkkkkkk");
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

        /*Button btn = findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id++;
                SWApi.getApi().getAllPeople(id, new Callback<ListaPerson<Personagem>>() {
                    @Override
                    public void success(ListaPerson<Personagem> lista, Response response) {
                        for (Personagem p : lista.results) {
                            people.add(p.toString());
                        }
                        adpt = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, people);
                        list.setAdapter(adpt);
                        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                int peopleId = (int)id+1;
                                if (peopleId >= 17) {
                                    peopleId++;
                                }
                                Intent it = new Intent(MainActivity.this, Detalhes.class);
                                it.putExtra("peopleId", peopleId);
                                startActivity(it);
                            }
                        });
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        people.add("kkkkkkkkkkkkkk");
                    }
                });
            }
        });*/
}