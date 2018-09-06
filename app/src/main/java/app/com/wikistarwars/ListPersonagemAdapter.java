package app.com.wikistarwars;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import app.com.wikistarwars.Model.Personagem;

public class ListPersonagemAdapter extends RecyclerView.Adapter<ListPersonagemAdapter.CustomViewHolder> {
    private List<Personagem> personagem;

    public ListPersonagemAdapter(List<Personagem> personagem) {
        this.personagem = personagem;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.personagem_list, parent, false);

        return new CustomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        Personagem employee = personagem.get(position);
        holder.name.setText(employee.getName());
    }

    @Override
    public int getItemCount() {
        return personagem.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        public TextView name;

        public CustomViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
        }
    }

}
