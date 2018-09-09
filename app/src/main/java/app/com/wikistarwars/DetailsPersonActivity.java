package app.com.wikistarwars;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import app.com.wikistarwars.Api.RetrofitConfig;
import app.com.wikistarwars.Api.Service;
import app.com.wikistarwars.Model.Personagem;
import app.com.wikistarwars.Model.PersonagemRealm;
import app.com.wikistarwars.Model.PersonagemResponse;
import app.com.wikistarwars.Model.Planet;
import app.com.wikistarwars.Model.Species;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.internal.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;

public class DetailsPersonActivity extends AppCompatActivity {
    private OkHttpClient client;
    public static final String KEY_NAME = "Name";
    private String name = "";
    private String specie = "";
    private Service api;
    private TextView pName,pHeight,pMass,pGender, pHair_color, pEye_color, pSkin_color, pBirth_year, pPlanet, pSpecie;
    private ToggleButton buttonFavorite;
    private Context context;
    private Boolean favorite = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_person);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("Details of Person");

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            name = extras.getString(KEY_NAME);
        }

        Personagem personagens =   PersonagemRealm.getRealmInstance(getApplicationContext()).getPersonagem(name);

        api = RetrofitConfig.getApiService();


        pName = (TextView) findViewById(R.id.name);
        pHeight = (TextView) findViewById(R.id.height);
        pGender = (TextView) findViewById(R.id.gender);
        pMass = (TextView) findViewById(R.id.mass);
        pHair_color = (TextView) findViewById(R.id.hair_color);
        pSkin_color = (TextView) findViewById(R.id.skin_color);
        pEye_color = (TextView) findViewById(R.id.eye_color);
        pBirth_year = (TextView) findViewById(R.id.birth_year);
        pPlanet = (TextView) findViewById(R.id.planet);
        pSpecie = (TextView) findViewById(R.id.specie);
        buttonFavorite = (ToggleButton) findViewById(R.id.favorite_button);

        //Pegando o nome do Planeta
        loadPlanet(getIdFromUrl(personagens.getHomeworld()));
        for ( String s : personagens.getSpecies() ){
            loadSpecies(getIdFromUrl(s));
        }

        pName.setText(personagens.getName());
        pMass.setText(personagens.getMass());
        pHeight.setText(personagens.getHeight());
        pGender.setText(personagens.getGender());
        pHair_color.setText(personagens.getHair_color());
        pSkin_color.setText(personagens.getSkin_color());
        pEye_color.setText(personagens.getEye_color());
        pBirth_year.setText(personagens.getBirth_year());
        pPlanet.setText(" - ");
        pSpecie.setText(" - ");


        favorite = PersonagemRealm.getRealmInstance(context).getPersonagem(personagens.getName()).isFavourite();
        isFavorite(favorite);

        buttonFavorite.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try {
                    PersonagemRealm.getRealmInstance(context).addRemoveFavourite(name);
                    isFavorite(!favorite);

                } catch (java.io.IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void isFavorite(Boolean favorite){
        if(favorite)
             buttonFavorite.setBackgroundResource(R.drawable.ic_favorite_black_24dp);
        else
            buttonFavorite.setBackgroundResource(R.drawable.ic_favorite_border_black_24dp);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) // Press Back Icon
        {
            finish();
        }

        return super.onOptionsItemSelected(item);

    }

    private void loadPlanet(int id) {
         api.getHomeworld(id).enqueue(new retrofit2.Callback<Planet>() {
            @Override
            public void onResponse(retrofit2.Call<Planet> call, retrofit2.Response<Planet> response) {

                Planet p = response.body();
                pPlanet.setText(p.name);
            }

            @Override
            public void onFailure(retrofit2.Call<Planet> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
    private void loadSpecies(int id) {
        api.getSpecie(id).enqueue(new retrofit2.Callback<Species>() {
            @Override
            public void onResponse(retrofit2.Call<Species> call, retrofit2.Response<Species> response) {

                Species s = response.body();
                if(specie.isEmpty())
                    specie = s.getName();
                else
                    specie += "\n"+s.getName();
                pSpecie.setText(specie);
            }

            @Override
            public void onFailure(retrofit2.Call<Species> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
    private int getIdFromUrl(String url){
        URI uri = null;
        try {
            uri = new URI(url);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        String[] segments = uri.getPath().split("/");
        String idStr = segments[segments.length-1];
        return  Integer.parseInt(idStr);
    }

}
