package com.example.lucvaladao.entrevistapopcode.mvp.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lucvaladao.entrevistapopcode.R;
import com.example.lucvaladao.entrevistapopcode.entity.Character;
import com.example.lucvaladao.entrevistapopcode.mvp.HomeAdapterInterface;

import java.util.List;

/**
 * Created by lucvaladao on 3/19/18.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder>{

    private List<Character> characterList;
    private Context context;
    private HomeAdapterInterface mListener;

    public HomeAdapter (List<Character> characterList, Context context) throws Exception {
        this.characterList = characterList;
        this.context = context;
        if (context instanceof HomeAdapterInterface){
            mListener = (HomeAdapterInterface) context;
        } else {
            throw new Exception("Error - not implemented!");
        }
    }

    @Override
    public HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_character_list, parent, false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HomeViewHolder holder, int position) {
        Character characterItem = characterList.get(position);
        holder.characterName.setText(characterItem.getName());
        holder.characterHeight.setText(characterItem.getHeight());
        holder.characterGender.setText(characterItem.getGender());
        holder.characterWeight.setText(characterItem.getMass());
    }

    @Override
    public int getItemCount() {
        return characterList.size();
    }

    class HomeViewHolder extends RecyclerView.ViewHolder {
        TextView characterName, characterWeight, characterHeight, characterGender;
        public HomeViewHolder(View itemView) {
            super(itemView);
            characterName = itemView.findViewById(R.id.textViewIdName);
            characterWeight = itemView.findViewById(R.id.textViewIdWeight);
            characterHeight = itemView.findViewById(R.id.textViewIdHeight);
            characterGender = itemView.findViewById(R.id.textViewIdGender);
        }
    }
}
