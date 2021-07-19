package br.com.star_wars_wiki;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.star_wars_wiki.entity.People;
import br.com.star_wars_wiki.entity.Planet;
import br.com.star_wars_wiki.entity.Specie;
import br.com.star_wars_wiki.network.StarWars;
import br.com.star_wars_wiki.network.StarWarsApi;
import br.com.star_wars_wiki.view_model.PlanetViewModel;
import br.com.star_wars_wiki.view_model.SpecieViewModel;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ActPersonagem extends AppCompatActivity {

    private ProgressBar progressBarActPersonagem;
    private TextView txtName, txtHeight, txtMass, txtHairColor, txtSkinColor, txtEyeColor, txtBirthYear, txtGender, txtPlanet, txtSpecie;
    private People people;
    private Planet planetPeople;
    private ArrayList<Specie> species = new ArrayList<>();
    private PlanetViewModel planetViewModel;
    private SpecieViewModel specieViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_personagem);

        Toolbar toolbar = findViewById(R.id.toolbar_info_personagem);
        toolbar.setTitle("Informações Personagem");
        setSupportActionBar(toolbar);

        if(StarWarsApi.getApi() == null){
            StarWarsApi.init();
        }

        people = (People) getIntent().getSerializableExtra("people");

        progressBarActPersonagem = findViewById(R.id.progress_bar_act_personagem);
        txtName = findViewById(R.id.txt_name);
        txtHeight = findViewById(R.id.txt_height);
        txtMass = findViewById(R.id.txt_mass);
        txtHairColor = findViewById(R.id.txt_hair_color);
        txtSkinColor = findViewById(R.id.txt_skin_color);
        txtEyeColor = findViewById(R.id.txt_eye_color);
        txtBirthYear = findViewById(R.id.txt_birth_year);
        txtGender = findViewById(R.id.txt_gender);
        txtPlanet = findViewById(R.id.txt_planet);
        //txtSpecie = findViewById(R.id.txt_specie);

        getPlanetAndSpecie();
    }

    public void getPlanetAndSpecie(){
        progressBarActPersonagem.setVisibility(View.VISIBLE);

        String routePlanet = people.getHomeWorldUrl().replaceAll(APIConstants.BASE_URL, "");//Tira a parte da URL base
//        ArrayList<String> routeSpecieAux = people.getSpeciesUrls();
//        ArrayList<String> routeSpecie = new ArrayList<>();
//        for(int i = 0; i < routeSpecieAux.size(); i++){
//            routeSpecie.add(i, routeSpecieAux.get(i).replaceAll(APIConstants.BASE_URL, ""));
//        }

        StarWarsApi.getApi().getPlanet(routePlanet, new Callback<Planet>() {
            @Override
            public void success(Planet planet, Response response) {
                planetPeople = planet;
                planetViewModel = new PlanetViewModel(getApplication());
                planetViewModel.insert(planet);
                preencheCampos();
                //Não consegui implementar a listagem das espécies de um personagem
//                if(routeSpecie.size() > 0){
//                    for(int i = 0; i < routeSpecie.size(); i++){
//                        int finalI = i;
//                        StarWarsApi.getApi().getSpecies(routeSpecie.get(i), new Callback<Specie>() {
//                            @Override
//                            public void success(Specie specie, Response response) {
//                                specieViewModel = new SpecieViewModel(getApplication());
//                                specieViewModel.insert(specie);
//                                species.add(specie);
//                                if(finalI == routeSpecie.size() - 1){//Desabilita a progress bar apenas na última requisição
//                                    preencheCampos();
//                                }
//                            }
//
//                            @Override
//                            public void failure(RetrofitError error) {
//
//                            }
//                        });
//                    }
//                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    public void preencheCampos(){
        planetViewModel.getAllPlanets().observe(this, new Observer<List<Planet>>() {
            @Override
            public void onChanged(List<Planet> planets) {
                Planet planet = planetViewModel.getPlanet(planetPeople.getName());

                txtName.setText(people.getName());
                txtHeight.setText(people.getHeight());
                txtMass.setText(people.getMass());
                txtHairColor.setText(people.getHairColor());
                txtSkinColor.setText(people.getSkinColor());
                txtEyeColor.setText(people.getEyeColor());
                txtBirthYear.setText(people.getBirthYear());
                txtGender.setText(people.getGender());
                txtPlanet.setText(planet.getName());

//                if(species.size() > 0){
//                    String specieAux = "";
//                    for(Specie specie : species){
//                        specieAux = specieAux + specie.getName() + "; ";
//                    }
//                    txtSpecie.setText(specieAux);
//                }else{
//                    txtSpecie.setText("Não informada");
//                }

                progressBarActPersonagem.setVisibility(View.GONE);
            }
        });
    }
}