package app.com.wikistarwars.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.com.wikistarwars.Model.Personagem;
import app.com.wikistarwars.R;

public class PaginationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int ITEM = 0;
    private static final int LOADING = 1;

    private List<Personagem> pResults;
    private Context context;

    private boolean isLoadingAdded = false;

    public PaginationAdapter(Context context) {
        this.context = context;
        pResults = new ArrayList<>();
    }

    public List<Personagem> getPersons() {
        return pResults;
    }

    public void setPersons(List<Personagem> pResults) {
        this.pResults = pResults;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                viewHolder = getViewHolder(parent, inflater);
                break;
            case LOADING:
                View v2 = inflater.inflate(R.layout.item_progress, parent, false);
                viewHolder = new LoadingVH(v2);
                break;
        }
        return viewHolder;
    }

    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View v1 = inflater.inflate(R.layout.personagem_list, parent, false);
        viewHolder = new PersonVH(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Personagem result = pResults.get(position);

        switch (getItemViewType(position)) {
            case ITEM:
                final PersonVH personVH = (PersonVH) holder;
                personVH.pName.setText(result.getName());
                personVH.pHeight.setText(result.getHeight());
                personVH.pGender.setText(result.getGender());
                personVH.pMass.setText(result.getMass());
                break;

            case LOADING:
                break;
        }

    }

    @Override
    public int getItemCount() {
        return pResults == null ? 0 : pResults.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == pResults.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    public void add(Personagem r) {
        pResults.add(r);
        notifyItemInserted(pResults.size() - 1);
    }

    public void addAll(List<Personagem> pResults) {
        for (Personagem result : pResults) {
            add(result);
        }
    }

    public void remove(Personagem p) {
        int position = pResults.indexOf(p);
        if (position > -1) {
            pResults.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new Personagem());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = pResults.size() - 1;
        Personagem result = getItem(position);

        if (result != null) {
            pResults.remove(position);
            notifyItemRemoved(position);
        }
    }

    public Personagem getItem(int position) {
        return pResults.get(position);
    }

    protected class PersonVH extends RecyclerView.ViewHolder {
        private TextView pName, pHeight, pGender, pMass;
        private ProgressBar mProgress;

        public PersonVH(View itemView) {
            super(itemView);

            pName = (TextView) itemView.findViewById(R.id.name);
            pHeight = (TextView) itemView.findViewById(R.id.height);
            pGender = (TextView) itemView.findViewById(R.id.gender);
            pMass = (TextView) itemView.findViewById(R.id.mass);
            mProgress = (ProgressBar) itemView.findViewById(R.id.progress);
        }
    }


    protected class LoadingVH extends RecyclerView.ViewHolder {

        public LoadingVH(View itemView) {
            super(itemView);
        }
    }


}