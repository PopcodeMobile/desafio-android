package com.example.starwarswiki;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.starwarswiki.structural.Person;

import java.util.List;

public class PersonListAdapter  extends RecyclerView.Adapter<PersonListAdapter.PersonViewHolder> {
    private final LayoutInflater mInflater;
    private List<Person> listOfPerson;

    public class PersonViewHolder extends RecyclerView.ViewHolder {

        private final TextView personName;
        private final TextView personHeight;
        private final TextView personMass;
        private final TextView personGender;
        public PersonViewHolder(@NonNull View itemView) {
            super(itemView);
            personName = itemView.findViewById(R.id.name);
            personHeight = itemView.findViewById(R.id.height);
            personMass = itemView.findViewById(R.id.mass);
            personGender = itemView.findViewById(R.id.gender);
        }


    }

    public PersonListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public PersonListAdapter.PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new PersonViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonListAdapter.PersonViewHolder holder, int position) {
        if (listOfPerson != null) {
            Person current = listOfPerson.get(position);
            holder.personName.setText(current.getName());
            holder.personHeight.setText(current.getHeight());
            holder.personMass.setText(current.getMass());
            holder.personGender.setText(current.getGender());
        } else {
            // Covers the case of data not being ready yet.
            holder.personName.setText("Loading...");
        }
    }

    @Override
    public int getItemCount() {
        if (listOfPerson != null)
            return listOfPerson.size();
        else return 0;
    }

    void setListOfPersonPerson(List<Person> listOfPerson){
        this.listOfPerson = listOfPerson;
        notifyDataSetChanged();
    }
}
