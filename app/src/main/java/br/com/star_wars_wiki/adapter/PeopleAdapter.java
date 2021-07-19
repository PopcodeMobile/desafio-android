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
import br.com.star_wars_wiki.entity.People;

public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.PersonagemHolder>{

    private List<People> peopleList;

    public PeopleAdapter(List<People> lista){
        this.peopleList = lista;
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

        //TODO Falta colocar a ação do botão
    }

    @Override
    public int getItemCount() {
        return this.peopleList.size();
    }

    public class PersonagemHolder extends RecyclerView.ViewHolder{

        TextView txtNome, txtAltura, txtGenero, txtPeso;
        Button btnAddFavorito;

        public PersonagemHolder(@NonNull View itemView) {
            super(itemView);
            txtNome = itemView.findViewById(R.id.txt_nome);
            txtAltura = itemView.findViewById(R.id.txt_altura);
            txtGenero = itemView.findViewById(R.id.txt_genero);
            txtPeso = itemView.findViewById(R.id.txt_peso);
            btnAddFavorito = itemView.findViewById(R.id.btn_add_favoritos);
        }
    }

    public void setPeopleList(List<People> peopleList) {
        this.peopleList = peopleList;
    }
}
