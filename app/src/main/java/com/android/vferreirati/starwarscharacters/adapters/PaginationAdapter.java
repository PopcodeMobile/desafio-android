package com.android.vferreirati.starwarscharacters.adapters;

import android.content.Context;
import android.graphics.Movie;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.vferreirati.starwarscharacters.R;
import com.android.vferreirati.starwarscharacters.models.Character;

import java.util.List;

public class PaginationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // ViewHolder that actually holds the character View
    public class ContentHolder extends RecyclerView.ViewHolder {
        private TextView mNameTextView;
        private TextView mHeightTextView;
        private TextView mGenderTextView;
        private TextView mWeightTextView;

        public ContentHolder(View itemView) {
            super(itemView);

            mNameTextView = itemView.findViewById(R.id.tv_name);
            mHeightTextView = itemView.findViewById(R.id.tv_height);
            mGenderTextView = itemView.findViewById(R.id.tv_gender);
            mWeightTextView = itemView.findViewById(R.id.tv_weight);
        }

        public void bind(Character character) {
            mNameTextView.setText(character.getName());
            mHeightTextView.setText(character.getHeight());
            mGenderTextView.setText(character.getGender());
            mWeightTextView.setText(character.getWeight());
        }
    }

    // ViewHolder that represents the loading bar at the end of the RC
    // No need to do anything about it
    public class LoadingHolder extends RecyclerView.ViewHolder {

        public LoadingHolder(View itemView) {
            super(itemView);
        }
    }

    private List<Character> mCharacters;
    private Context mContext;
    private boolean isLoadingAdded = false;

    private static final int CONTENT = 0;
    private static final int LOADING = 4;

    public PaginationAdapter(Context packageContext) {
        mContext = packageContext;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View itemView;

        if(viewType == CONTENT) {
            itemView = layoutInflater.inflate(R.layout.list_item, viewGroup, false);
            viewHolder = new ContentHolder(itemView);
        } else {
            itemView = layoutInflater.inflate(R.layout.item_load, viewGroup, false);
            viewHolder = new LoadingHolder(itemView);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        Character character = mCharacters.get(position);

        // If the ViewHolder is type CONTENT
        // Bind the new data
        if(getItemViewType(position) == CONTENT) {
            ((ContentHolder) viewHolder).bind(character);
        }
    }

    @Override
    public int getItemCount() {
        return mCharacters == null ? 0 : mCharacters.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == mCharacters.size() - 1 && isLoadingAdded) ? LOADING : CONTENT;
    }


    public Character getItem(int position) {
        return mCharacters.get(position);
    }

    // Adds a new Character and updates the data set
    public void add(Character character) {
        mCharacters.add(character);
        notifyItemInserted(mCharacters.size() - 1);
    }

    // Adds a list
    public void addAll(List<Character> characters) {
        for(Character character : characters) {
            add(character);
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    public void remove(Character character) {
        int position = mCharacters.indexOf(character);
        if(position >= 0) {
            mCharacters.remove(position);
            notifyItemRemoved(position);
        }
    }

    // Trigger a new character to be added while isLoadingAdded is true
    // Will cause the LoadingHolder to be added to the RC
    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new Character());
    }

    // Removes the Loading holder from the RC by removing the last ListItem
    // if valid. Call this when the new data finished loading.
    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = mCharacters.size() - 1;
        Character character = getItem(position);

        if (character != null) {
            mCharacters.remove(position);
            notifyItemRemoved(position);
        }
    }
}
