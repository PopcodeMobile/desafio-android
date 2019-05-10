package kleyton.starwarswiki;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder>{

    private Context context;
    private List<Person> personList;

    public PersonAdapter(Context context, List<Person> personList) {
        this.context = context;
        this.personList = personList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Person person = personList.get(i);
        viewHolder.name.setText(person.getName());
        viewHolder.height.setText(person.getHeight());
        viewHolder.gender.setText(person.getGender());
        viewHolder.mass.setText(person.getMass());

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name, height, gender, mass;

        public ViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name_textView);
            height = itemView.findViewById(R.id.height_textView);
            gender = itemView.findViewById(R.id.gender_textView);
            mass = itemView.findViewById(R.id.mass_textView);
        }
    }

    @Override
    public int getItemCount() {
        return personList.size();
    }
}
