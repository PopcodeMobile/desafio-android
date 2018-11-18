package com.example.administrador.starwarswiki.ui.CharacterList;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.administrador.starwarswiki.R;
import com.example.administrador.starwarswiki.data.model.StarWarsCharacter;
import com.example.administrador.starwarswiki.ui.CharacterDetails.CharacterDetailsActivity;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> implements Filterable {
    private List<StarWarsCharacter> mDataset;
    private List<StarWarsCharacter> characterListFiltered;
    private CharacterListViewModel viewModel;

    public RecyclerViewAdapter(CharacterListViewModel viewModel) {
        this.viewModel = viewModel;
        mDataset = viewModel.getStarWarsCharactersList().getValue();
        characterListFiltered = mDataset;
    }

    void setStarWarsCharacters(List<StarWarsCharacter> starWarsCharacters){
        mDataset = starWarsCharacters;
        notifyDataSetChanged();
    }

     // Create new views (invoked by the layout manager)
    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item, parent, false);

        TextView textViewName = v.findViewById(R.id.name);
        TextView textViewGender = v.findViewById(R.id.gender);
        TextView textViewHeight = v.findViewById(R.id.height);
        TextView textViewMass = v.findViewById(R.id.mass);
        ToggleButton favbtn =  v.findViewById(R.id.favorite_button);
        ConstraintLayout constraintLayout = v.findViewById(R.id.constraint_layout);

        MyViewHolder vh = new MyViewHolder(v,textViewName, textViewGender, textViewHeight, textViewMass, favbtn, constraintLayout);
        return vh;
    }

    private void viewHolderDataBinder(MyViewHolder holder, int position, List<StarWarsCharacter> dataList){
        holder.textViewName.setText(dataList.get(position).getName());
        holder.textViewGender.setText(dataList.get(position).getGender());
        holder.textViewHeight.setText(dataList.get(position).getHeight());
        holder.textViewMass.setText(dataList.get(position).getMass());

        holder.favoriteButton.setChecked(dataList.get(position).isFavorite());
        holder.favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.favoriteButton.isChecked()) {
                    // The toggle is enabled
                    Log.d(">>>>>>>>>", "inserindo favorito" + dataList.get(position).getId());
                    viewModel.updateFavorite(true, dataList.get(position).getId());
                } else {
                    // The toggle is disabled
                    Log.d(">>>>>>>>>", "removendo favorito");
                    viewModel.updateFavorite(false, dataList.get(position).getId());
                }
            }
        });
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CharacterDetailsActivity.class);
                intent.putExtra("id", dataList.get(position).getId());
                v.getContext().startActivity(intent);
            }
        });
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
    // - replace the contents of the view with that element
        if (characterListFiltered != null) {
            viewHolderDataBinder(holder, position, characterListFiltered);
        }else if(mDataset != null){
           viewHolderDataBinder(holder, position, mDataset);
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if (characterListFiltered != null)
            return characterListFiltered.size();
        else if(mDataset != null)
            return mDataset.size();
        else
            return 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    characterListFiltered = mDataset;
                }else{
                    if(charString.toLowerCase().contains("favorites") ){

                        List<StarWarsCharacter> filteredList = new ArrayList<>();
                        for (StarWarsCharacter row : mDataset) {
                            // isfavorite condition.
                            if (row.isFavorite()) {
                                filteredList.add(row);
                            }
                        }
                        characterListFiltered = filteredList;

                    }else{

                        List<StarWarsCharacter> filteredList = new ArrayList<>();
                        for (StarWarsCharacter row : mDataset) {
                            // name match condition.
                            if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
                                filteredList.add(row);
                            }
                        }
                        characterListFiltered = filteredList;
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = characterListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                characterListFiltered = (ArrayList<StarWarsCharacter>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    // Provide a reference to the views for each data item
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View mView;
        public TextView textViewName;
        public TextView textViewGender;
        public TextView textViewHeight;
        public TextView textViewMass;
        public ToggleButton favoriteButton;
        public ConstraintLayout constraintLayout;

        public MyViewHolder(View v, TextView textViewName, TextView textViewGender, TextView textViewHeight,
                            TextView textViewMass,  ToggleButton favoriteButton, ConstraintLayout constraintLayout) {
            super(v);
            mView = v;
            this.textViewName = textViewName;
            this.textViewGender = textViewGender;
            this.textViewHeight = textViewHeight;
            this.textViewMass = textViewMass;
            this.favoriteButton = favoriteButton;
            this.constraintLayout = constraintLayout;
        }
    }

}

