package br.com.jaysonsabino.desafioandroidpopcode.adapters;

import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.jaysonsabino.desafioandroidpopcode.R;
import br.com.jaysonsabino.desafioandroidpopcode.entities.Character;

public class CharacterListAdapter extends PagedListAdapter<Character, CharacterListAdapter.CharacterViewHolder> {

    private Context context;
    private OnClickListener onClickListener;

    public CharacterListAdapter(Context context, @NonNull DiffUtil.ItemCallback<Character> diffCallback) {
        super(diffCallback);
        this.context = context;
    }

    public void setOnItemClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public CharacterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.character_list_item, viewGroup, false);

        CharacterViewHolder characterViewHolder = new CharacterViewHolder(view);

        if (onClickListener != null) {
            characterViewHolder.setOnClickListener(onClickListener);
        }

        return characterViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterViewHolder characterViewHolder, int i) {
        Character character = getItem(i);

        if (character == null) {
            return;
        }

        String height = character.getHeight();
        String mass = character.getMass();

        if (!height.equals("unknown")) {
            height = height + " cm";
        }
        if (!mass.equals("unknown")) {
            mass = mass + " kg";
        }

        characterViewHolder.getName().setText(character.getName());
        characterViewHolder.getGender().setText(character.getGender());
        characterViewHolder.getHeight().setText(height);
        characterViewHolder.getMass().setText(mass);
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

        public void setOnClickListener(final OnClickListener onClickListener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Character item = getItem(getAdapterPosition());

                    onClickListener.onClick(v, item);
                }
            });
        }

        TextView getName() {
            return name;
        }

        TextView getGender() {
            return gender;
        }

        TextView getHeight() {
            return height;
        }

        TextView getMass() {
            return mass;
        }
    }

    public interface OnClickListener {

        void onClick(View v, Character character);
    }
}
