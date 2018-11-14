package com.example.administrador.starwarswiki;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private List<StarWarsCharacter> mDataset;

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

    // Provide a suitable constructor (depends on the kind of dataset)
    public RecyclerViewAdapter(List<StarWarsCharacter> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.character_fragment, parent, false);
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
        holder.textViewName.setText(mDataset.get(position).getName());
        holder.textViewGender.setText(mDataset.get(position).getGender());
        holder.textViewHeight.setText(mDataset.get(position).getHeight());
        holder.textViewMass.setText(mDataset.get(position).getMass());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}

