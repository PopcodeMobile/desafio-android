package com.popcode.starwars.ui.main;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.popcode.starwars.R;
import com.popcode.starwars.data.local.entity.CharacterElement;
import com.popcode.starwars.databinding.CharacterListItemBinding;

import java.util.ArrayList;
import java.util.List;

public class CharacterListAdapter extends RecyclerView.Adapter<CharacterListAdapter.CharacterViewHolder> {

    List<? extends CharacterElement> characterList;

    @Nullable
    private final CharacterClickCallback characterClickCallback;

    public CharacterListAdapter(@Nullable CharacterClickCallback characterClickCallback) {
        this.characterClickCallback = characterClickCallback;
        characterList = new ArrayList<>();
    }

    public void addCharactersList(final List<? extends CharacterElement> characters)  {
        List<CharacterElement> elements = new ArrayList<>(characterList);
        elements.addAll(characters);
        characterList = elements;
        notifyDataSetChanged();
    }

    public List<? extends CharacterElement> getCharacterList() {
        return characterList;
    }

    public void setCharacterList(final List<? extends CharacterElement> characters) {
        if (this.characterList == null) {
            this.characterList = characters;
            notifyItemRangeInserted(0, characters.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return CharacterListAdapter.this.characterList.size();
                }

                @Override
                public int getNewListSize() {
                    return characters.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return false;
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    return false;
                }
            });
            this.characterList = characters;
            result.dispatchUpdatesTo(this);
        }
    }

    public void clear(){
        characterList.clear();
        notifyDataSetChanged();
    }

    @Override
    public CharacterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CharacterListItemBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.character_list_item,
                        parent, false);

        binding.setCallback(characterClickCallback);

        return new CharacterViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(CharacterViewHolder holder, int position) {
        CharacterElement item = characterList.get(position);
        if (item.mass.equals("unknown")){
            item.mass = "-- ";
        }
        if (item.height.equals("unknown")){
            item.height = "-- ";
        }
        holder.binding.setCharacterElement(item);
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return characterList == null ? 0 : characterList.size();
    }

    static class CharacterViewHolder extends RecyclerView.ViewHolder {

        final CharacterListItemBinding binding;

        public CharacterViewHolder(CharacterListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}