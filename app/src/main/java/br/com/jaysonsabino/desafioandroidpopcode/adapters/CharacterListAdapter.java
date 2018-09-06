package br.com.jaysonsabino.desafioandroidpopcode.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.jaysonsabino.desafioandroidpopcode.R;
import br.com.jaysonsabino.desafioandroidpopcode.entities.Character;

public class CharacterListAdapter extends RecyclerView.Adapter<CharacterListAdapter.CharacterViewHolder> {

    private List<Character> characters;
    private Context context;

    public CharacterListAdapter(Context context, List<Character> characters) {
        this.context = context;
        this.characters = characters;
    }

    @NonNull
    @Override
    public CharacterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.character_list_item, viewGroup, false);

        return new CharacterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterViewHolder characterViewHolder, int i) {
        Character character = characters.get(i);

        characterViewHolder.getName().setText(character.getName());
        characterViewHolder.getGender().setText(character.getGender());
        characterViewHolder.getHeight().setText(character.getHeight());
        characterViewHolder.getMass().setText(character.getMass());
    }

    @Override
    public int getItemCount() {
        return characters.size();
    }

    class CharacterViewHolder extends RecyclerView.ViewHolder {

        private final TextView name;
        private final TextView gender;
        private final TextView height;
        private final TextView mass;

        CharacterViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.listItemCharacterName);
            gender = itemView.findViewById(R.id.listItemCharacterGender);
            height = itemView.findViewById(R.id.listItemCharacterHeight);
            mass = itemView.findViewById(R.id.listItemCharacterMass);
        }

        public TextView getName() {
            return name;
        }

        public TextView getGender() {
            return gender;
        }

        public TextView getHeight() {
            return height;
        }

        public TextView getMass() {
            return mass;
        }
    }
}
