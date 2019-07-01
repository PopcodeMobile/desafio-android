package com.example.entrevistapopcode.view.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.example.entrevistapopcode.R;
import com.example.entrevistapopcode.api.entity.entity.Person;
import com.example.entrevistapopcode.db.view.PersonViewModel;
import com.example.entrevistapopcode.views.ViewHolder;

import java.util.List;

@SuppressWarnings("unchecked")
public class Adapter extends RecyclerView.Adapter<ViewHolder> {

    private final List<Person> mUsers;
    private PersonViewModel viewModel;
    private FragmentActivity activity;


    public Adapter(List users) {
        mUsers = users;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        Person p = mUsers.get(position);
        holder.nameTextView.setText(p.getName());
        holder.massTextView.setText(p.getMass());
        holder.heightTextView.setText(p.getHeight());
        holder.genderTextView.setText(p.getGender());
        holder.fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removerFav(p, position);

            }
        });
        viewModel.getPerson(p.getName()).observe( activity,new Observer<Person>() {
            @Override
            public void onChanged(@Nullable final Person words) {
                if(words!=null){
                    if(words.getLoved()){
                       holder.fav.setBackgroundResource(R.drawable.baseline_favorite_black_18dp);
                    }else {
                        holder.fav.setVisibility(View.GONE);
                    }
                }


            }
        });

    }

    @Override
    public int getItemCount() {
        return mUsers != null ? mUsers.size() : 0;
    }

    private void insertItem(Person user) {
        mUsers.add(user);
        notifyItemInserted(getItemCount());
    }

    private void updateItem(int position) {

        notifyItemChanged(position);
    }

    private void removerFav(Person p, int position) {

        viewModel.insertFavorite(p.getName(), false);
        removerItem(position);
    }


    private void removerItem(int position) {
        mUsers.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mUsers.size());
    }

    public void updateList(Person user) {
        insertItem(user);
    }

    public List<Person> getList() {

        return mUsers;
    }

    public void setViewModel(PersonViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public void setActivity(FragmentActivity activity) {
        this.activity = activity;
    }
}