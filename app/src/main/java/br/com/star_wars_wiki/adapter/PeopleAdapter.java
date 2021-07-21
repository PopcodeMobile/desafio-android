package br.com.star_wars_wiki.adapter;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.star_wars_wiki.ActListaPersonagem;
import br.com.star_wars_wiki.ActPersonagem;
import br.com.star_wars_wiki.R;
import br.com.star_wars_wiki.database.converters.Converters;
import br.com.star_wars_wiki.entity.Favorite;
import br.com.star_wars_wiki.entity.People;
import br.com.star_wars_wiki.entity.ResponseFavorite;
import br.com.star_wars_wiki.network.StarWarsFavoriteApi;
import br.com.star_wars_wiki.view_model.FavoriteViewModel;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.PersonagemHolder>{

    private List<People> peopleList;
    private List<Favorite> favoriteList;
    private Context context;
    private Application application;
    private FavoriteViewModel favoriteViewModel;

    public PeopleAdapter(List<People> lista, Context context, Application application){
        this.context = context;
        this.peopleList = lista;
        this.application = application;
    }

    @NonNull
    @Override
    public PeopleAdapter.PersonagemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_personagem, parent, false);

        return new PersonagemHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull PeopleAdapter.PersonagemHolder holder, int position) {
        People people = peopleList.get(position);

        holder.txtNome.setText(people.getName());
        holder.txtAltura.setText(people.getHeight());
        holder.txtGenero.setText(people.getGender());
        holder.txtPeso.setText(people.getMass());

        holder.btnAddFavorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StarWarsFavoriteApi.init();
                StarWarsFavoriteApi.getApi().setFavorite(people.getName(), new Callback<ResponseFavorite>() {
                    @Override
                    public void success(ResponseFavorite responseFavorite, Response response) {
                        favoriteViewModel = new FavoriteViewModel(application);
                        favoriteViewModel.insert(Converters.convertePeopleToFavorite(people));
                        if(responseFavorite.getStatus() != null){
                            Toast.makeText(context, responseFavorite.getStatus() + ". " + responseFavorite.getMessage(), Toast.LENGTH_SHORT).show();
                        }else{
                            if(responseFavorite.getError() != null){
                                Toast.makeText(context, responseFavorite.getError() + ". " + responseFavorite.getErrorMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(context, "internal error. Only at the end do you realize the power of the Dark Side.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        holder.btnVisualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(context, ActPersonagem.class);
                it.putExtra("people", peopleList.get(position));
                it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(it);
            }
        });

        holder.btnRemoveFavorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favoriteViewModel = new FavoriteViewModel(application);
                favoriteViewModel.remove(Converters.convertePeopleToFavorite(people), context);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.peopleList.size();
    }

    public class PersonagemHolder extends RecyclerView.ViewHolder{

        TextView txtNome, txtAltura, txtGenero, txtPeso;
        Button btnAddFavorito, btnVisualizar, btnRemoveFavorito;

        public PersonagemHolder(@NonNull View itemView) {
            super(itemView);
            txtNome = itemView.findViewById(R.id.txt_nome);
            txtAltura = itemView.findViewById(R.id.txt_altura);
            txtGenero = itemView.findViewById(R.id.txt_genero);
            txtPeso = itemView.findViewById(R.id.txt_peso);
            btnAddFavorito = itemView.findViewById(R.id.btn_add_favoritos);
            btnVisualizar = itemView.findViewById(R.id.btn_visualizar);
            btnRemoveFavorito = itemView.findViewById(R.id.btn_remove_favorito);
        }
    }

    public void setPeopleList(List<People> peopleList) {
        this.peopleList = peopleList;
    }
}
