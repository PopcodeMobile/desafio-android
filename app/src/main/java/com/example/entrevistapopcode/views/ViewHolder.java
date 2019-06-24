package com.example.entrevistapopcode.views;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.entrevistapopcode.R;

public class ViewHolder extends RecyclerView.ViewHolder {

    public TextView massTextView;
    public TextView nameTextView;
    public TextView heightTextView;
    public TextView genderTextView;
    public ImageView fav;



    public ViewHolder(View v) {
        super(v);
        nameTextView = (TextView) itemView.findViewById(R.id.nameTextView);
        massTextView = (TextView) itemView.findViewById(R.id.massTextView);
        heightTextView = (TextView) itemView.findViewById(R.id.heightTextView);
        genderTextView = (TextView) itemView.findViewById(R.id.genderTextView);
        fav = (ImageView) itemView.findViewById(R.id.fav);

    }


}