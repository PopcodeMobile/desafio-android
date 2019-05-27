package com.example.starwarswiki;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.starwarswiki.structural.Person;

import java.util.ArrayList;
import java.util.List;

public class PersonListAdapter  extends RecyclerView.Adapter<PersonListAdapter.PersonViewHolder> {
    private final LayoutInflater mInflater;
    private List<Person> listOfPerson;
    private OnPersonClickListener mOnPersonClickListener;
    private OnCheckedFavListener onCheckedFavListener;
    private SparseBooleanArray itemStateArray= new SparseBooleanArray();



    public class PersonViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener, CompoundButton.OnCheckedChangeListener {

        private final TextView personName;
        private final TextView personHeight;
        private final TextView personMass;
        private final TextView personGender;
        private final Switch favSwitch;

        private OnCheckedFavListener onCheckedFavListener;
        private OnPersonClickListener onPersonClickListener;

        public PersonViewHolder(@NonNull View itemView,
                                OnPersonClickListener onPersonClickListener,
                                OnCheckedFavListener onCheckedFavListener) {
            super(itemView);
            personName = itemView.findViewById(R.id.name);
            personHeight = itemView.findViewById(R.id.height);
            personMass = itemView.findViewById(R.id.mass);
            personGender = itemView.findViewById(R.id.gender);

            favSwitch = itemView.findViewById(R.id.switch1);
            favSwitch.setOnCheckedChangeListener(this);

            this.onPersonClickListener = onPersonClickListener;
            this.onCheckedFavListener = onCheckedFavListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onPersonClickListener.onPersonClick(getAdapterPosition());
        }

        /**
         * It returns the name and a flag integer that indicates if its a fav or not
         * @param buttonView
         * @param isChecked
         */
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(buttonView.getContentDescription() != null && buttonView.isPressed()) {
                onCheckedFavListener.onFavClick(buttonView.getContentDescription().toString(), isChecked ? 1 : 0);
            }
        }
    }

    public PersonListAdapter(Context context, OnPersonClickListener mOnPersonClickListener, OnCheckedFavListener onCheckedFavListener) {
        mInflater = LayoutInflater.from(context);
        this.mOnPersonClickListener = mOnPersonClickListener;
        this.onCheckedFavListener = onCheckedFavListener;
    }

    @NonNull
    @Override
    public PersonListAdapter.PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new PersonViewHolder(itemView, mOnPersonClickListener, onCheckedFavListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonListAdapter.PersonViewHolder holder, int position) {
        if (listOfPerson != null) {
            Person current = listOfPerson.get(position);
            holder.personName.setText(current.getName());
            holder.personHeight.setText(current.getHeight());
            holder.personMass.setText(current.getMass());
            holder.personGender.setText(current.getGender());
            holder.favSwitch.setChecked(current.getFavorite()==1);
            holder.favSwitch.setContentDescription(current.getName());
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

    public Person getPerson(int position) {
        return listOfPerson.get(position);
    }

    public void setListOfPerson(List<Person> listOfPerson){
        this.listOfPerson = listOfPerson;
        notifyDataSetChanged();
    }

    public void updateList (List<Person> listOfPerson) {
        this.listOfPerson = new ArrayList<>();
        this.listOfPerson.addAll(listOfPerson);
        notifyDataSetChanged();
    }

    /**
     * This is used to map clicks
     */
    public interface OnPersonClickListener {
        void onPersonClick(int position);
    }

    /**
     * Interface used to set/unset Favorites
     */
    public interface OnCheckedFavListener {
        void onFavClick(String name, int fav);
    }
}
