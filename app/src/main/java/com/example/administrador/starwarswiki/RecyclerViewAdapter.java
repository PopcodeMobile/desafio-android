package com.example.administrador.starwarswiki;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> implements Filterable {
    private List<StarWarsCharacter> mDataset;
    private List<StarWarsCharacter> characterListFiltered;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views fo
    // r a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View mView;
        public TextView textViewName;
        public TextView textViewGender;
        public TextView textViewHeight;
        public TextView textViewMass;

        public MyViewHolder(View v, TextView textViewName, TextView textViewGender, TextView textViewHeight, TextView textViewMass) {
            super(v);
            mView = v;
            this.textViewName = textViewName;
            this.textViewGender = textViewGender;
            this.textViewHeight = textViewHeight;
            this.textViewMass = textViewMass;
        }
    }

    public RecyclerViewAdapter(List<StarWarsCharacter> myDataset) {
        mDataset = myDataset;
        characterListFiltered = myDataset;
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
        MyViewHolder vh = new MyViewHolder(v,textViewName, textViewGender, textViewHeight, textViewMass);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
    // - replace the contents of the view with that element
        if (characterListFiltered != null) {
            holder.textViewName.setText(characterListFiltered.get(position).getName());
            holder.textViewGender.setText(characterListFiltered.get(position).getGender());
            holder.textViewHeight.setText(characterListFiltered.get(position).getHeight());
            holder.textViewMass.setText(characterListFiltered.get(position).getMass());
        }else if(mDataset != null){
            holder.textViewName.setText(mDataset.get(position).getName());
            holder.textViewGender.setText(mDataset.get(position).getGender());
            holder.textViewHeight.setText(mDataset.get(position).getHeight());
            holder.textViewMass.setText(mDataset.get(position).getMass());

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
                } else {
                    List<StarWarsCharacter> filteredList = new ArrayList<>();
                    for (StarWarsCharacter row : mDataset) {

                        // name match condition.
                        if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    characterListFiltered = filteredList;
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
}

