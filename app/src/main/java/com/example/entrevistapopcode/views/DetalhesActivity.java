package com.example.entrevistapopcode.views;

import android.os.Bundle;

import com.example.entrevistapopcode.api.entity.API;
import com.example.entrevistapopcode.api.entity.config.RetrofitConfig;
import com.example.entrevistapopcode.api.entity.entity.Favorito;
import com.example.entrevistapopcode.api.entity.entity.OnlyNameGeneric;
import com.example.entrevistapopcode.api.entity.entity.Person;
import com.example.entrevistapopcode.db.view.PersonViewModel;
import com.example.entrevistapopcode.view.adapters.Adapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.entrevistapopcode.R;

import org.json.JSONObject;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalhesActivity extends AppCompatActivity {

    API service;
    private CompositeDisposable disposable = new CompositeDisposable();
    Observable<OnlyNameGeneric> specieObservable;
    Observable<OnlyNameGeneric> planetObservable;
    private TextView nome_gen;
    private TextView caracteristicas;
    private  String firts;
    private ToggleButton favorite;
    private Person p;
    private Button voltar;
    private Toolbar toolbar;
    private Boolean check = false;
    private int  tamanho =-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        service = new RetrofitConfig(false).getSwa();
        setContentView(R.layout.activity_detalhes);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        nome_gen = (TextView)findViewById(R.id.nome_genero);
        favorite = (ToggleButton) findViewById(R.id.favorite);
        caracteristicas = (TextView)findViewById(R.id.caract);


        p = (Person) getIntent().getExtras().getSerializable("person");
        String[] id_home  = p.getHomeworld().split("/");
        String[] id_specie = p.getSpecies().get(0).split("/");

        specieObservable = service.getSpecie(Integer.valueOf(id_specie[id_specie.length - 1]));
        planetObservable = service.getPlanet(Integer.valueOf(id_home[id_home.length - 1]));


        nome_gen.setText(p.getName() +" - "+p.getGender());
        String contenr = "O personagem possui "+p.getHeight()+"cm de altura com um peso de "+p.getMass()+"\n" +
                "Sua aparência inclui como cor de cabelo "+p.getHairColor()+" e olhos "+p.getEyeColor()+" e cor da pela " +p.getSkinColor()+"\n" +
                "Data de aniversario: "+p.getBirthYear();
        caracteristicas.setText(contenr);
        disposable.add(
                planetObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<OnlyNameGeneric>() {

                            @Override
                            public void onNext(OnlyNameGeneric generic) {
                                caracteristicas.setText(caracteristicas.getText() + "\n" + "Planeta: "+generic.name);
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        }));
        disposable.add(
                specieObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<OnlyNameGeneric>() {

                            @Override
                            public void onNext(OnlyNameGeneric generic) {
                                caracteristicas.setText(caracteristicas.getText() + "\n" + "Especie: "+generic.name);
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        }));
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.7f, 1.0f, 0.7f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.7f, Animation.RELATIVE_TO_SELF, 0.7f);
        scaleAnimation.setDuration(500);
        BounceInterpolator bounceInterpolator = new  BounceInterpolator();
        scaleAnimation.setInterpolator(bounceInterpolator);
        favorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                buttonView.startAnimation(scaleAnimation);
                PersonViewModel viewModel = ViewModelProviders.of(DetalhesActivity.this).get(PersonViewModel.class);
                viewModel.insertFavorite(p.getName(), isChecked);
                if(!check){

                    viewModel.getAllFavorite().observe(DetalhesActivity.this, new Observer<List<Person>>() {
                        @Override
                        public void onChanged(@Nullable final List<Person> words) {
                            if(words!=null && tamanho == -1){

                                tamanho = words.size();
                                API serviceFav = new RetrofitConfig(true).getSwa();
                                String[] id  = p.getUrl().split("/");
                                String header = tamanho%2 != 0 ? "400" : "200";

                                Call<Favorito> callPage = serviceFav.postFavoriteCharacter(header,id[id.length - 1]);
                                callPage.enqueue(new Callback<Favorito>() {
                                    @Override
                                    public void onResponse(Call<Favorito> call, Response<Favorito> response) {
                                        tamanho = -1;
                                        Favorito cep = response.body();
                                        if (cep!=null){
                                            Toast.makeText(DetalhesActivity.this,cep.getMessage(),Toast.LENGTH_LONG).show();
                                            Log.e("favorito","resultado"+cep.getMessage());
                                        } else if (response.code() == 400 ){
                                            try{
                                                JSONObject jObjError = new JSONObject(response.errorBody().string());
                                                Toast.makeText(DetalhesActivity.this, jObjError.getString("error_message"), Toast.LENGTH_LONG).show();
                                            }catch (Exception e){
                                                e.printStackTrace();
                                            }


//                                            Log.e("favorito","resultado"+cep.getMessage());
                                        }



                                    }

                                    @Override
                                    public void onFailure(Call<Favorito> call, Throwable t) {
                                        Log.i("toast",t.getMessage());
                                        Toast.makeText(DetalhesActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
                                    }
                                });
                            }


                        }
                    });


                }

            }
        });
        PersonViewModel viewModel = ViewModelProviders.of(this).get(PersonViewModel.class);

        viewModel.isFav(p.getName()).observe(DetalhesActivity.this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {

                check = aBoolean;
                favorite.setChecked(check);
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
            return true;
        } else {
            super.onOptionsItemSelected(item);
            return false;
        }
    }

}
