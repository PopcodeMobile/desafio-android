package br.com.star_wars_wiki.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.star_wars_wiki.R;
import br.com.star_wars_wiki.entity.Favorite;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteHolder> {

    private List<Favorite> favoriteList;

    public FavoriteAdapter(List<Favorite> favoriteList){
        this.favoriteList = favoriteList;
    }

    @NonNull
    @Override
    public FavoriteAdapter.FavoriteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_personagem, parent, false);

        return new FavoriteAdapter.FavoriteHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteAdapter.FavoriteHolder holder, int position) {
        Favorite favorite = favoriteList.get(position);

        holder.txtNome.setText(favorite.getName());
        holder.txtAltura.setText(favorite.getHeight());
        holder.txtGenero.setText(favorite.getGender());
        holder.txtPeso.setText(favorite.getMass());
        holder.btnAddFavorito.setVisibility(View.GONE);
        holder.btnVisualizar.setVisibility(View.GONE);
        holder.btnRemoveFavorito.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return favoriteList.size();
    }

    public class FavoriteHolder extends RecyclerView.ViewHolder{

        TextView txtNome, txtAltura, txtGenero, txtPeso;
        Button btnAddFavorito, btnVisualizar, btnRemoveFavorito;

        public FavoriteHolder(@NonNull View itemView) {
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

    public void setFavoriteList(List<Favorite> favoriteList){
        this.favoriteList = favoriteList;
    }
}
