package br.com.jaysonsabino.desafioandroidpopcode;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ListaPersonagensAdapter extends RecyclerView.Adapter<ListaPersonagensAdapter.PersonagemViewHolder> {

    private List<String> personagens;
    private Context context;

    public ListaPersonagensAdapter(Context context, List<String> personagens) {
        this.context = context;
        this.personagens = personagens;
    }

    @NonNull
    @Override
    public PersonagemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.personagem_list_item, viewGroup, false);

        return new PersonagemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonagemViewHolder personagemViewHolder, int i) {
        TextView nome = personagemViewHolder.itemView.findViewById(R.id.listItemPersonagemNome);
        nome.setText(personagens.get(i));
    }

    @Override
    public int getItemCount() {
        return personagens.size();
    }

    class PersonagemViewHolder extends RecyclerView.ViewHolder {

        PersonagemViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
