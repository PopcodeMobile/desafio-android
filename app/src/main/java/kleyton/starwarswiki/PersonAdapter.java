package kleyton.starwarswiki;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder>{

    private Context context;
    private List<Person> personList;

    public PersonAdapter(Context context, List<Person> personList) {
        this.context = context;
        this.personList = personList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        final Person person = personList.get(i);

        final String name = person.getName();
        final String height = person.getHeight();
        final String gender = person.getGender();
        final String mass = person.getMass();
        final String hair_color = person.getHair_color();
        final String skin_color = person.getSkin_color();
        final String eye_color = person.getEye_color();
        final String birth_year = person.getBirth_year();
        final String homeworld = person.getHomeworld();
        final String species = person.getSpecies();

        viewHolder.name.setText(name);
        viewHolder.height.setText(height);
        viewHolder.gender.setText(gender);
        viewHolder.mass.setText(mass);

        viewHolder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("msg", "This works properly.");
                intent.putExtra("name", name);
                intent.putExtra("height", height);
                intent.putExtra("gender", gender);
                intent.putExtra("mass", mass);
                intent.putExtra("hair_color", hair_color);
                intent.putExtra("skin_color", skin_color);
                intent.putExtra("eye_color", eye_color);
                intent.putExtra("birth_year", birth_year);
                intent.putExtra("homeworld", homeworld);
                intent.putExtra("species", species);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, height, gender, mass;
        LinearLayout item;

        public ViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name_textView);
            height = itemView.findViewById(R.id.height_textView);
            gender = itemView.findViewById(R.id.gender_textView);
            mass = itemView.findViewById(R.id.mass_textView);
            item = itemView.findViewById(R.id.itemLinearLayout);
        }
    }

    @Override
    public int getItemCount() {
        return personList.size();
    }
}
