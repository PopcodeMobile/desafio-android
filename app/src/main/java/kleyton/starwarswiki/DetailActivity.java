package kleyton.starwarswiki;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Detalhes");

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String height = intent.getStringExtra("height");
        String gender = intent.getStringExtra("gender");
        String mass = intent.getStringExtra("mass");
        String hair_color = intent.getStringExtra("hair_color");
        String skin_color = intent.getStringExtra("skin_color");
        String eye_color = intent.getStringExtra("eye_color");
        String birth_year = intent.getStringExtra("birth_year");
        String homeworld = intent.getStringExtra("homeworld");
        String species = intent.getStringExtra("species");

        TextView nameTv, heightTv, genderTv, massTv, hairTv, skinTv, eyeTv, birthTv, homeworldTv, speciesTv;
        nameTv = (TextView) findViewById(R.id.name_detail_textview);
        heightTv = (TextView) findViewById(R.id.height_detail_textview);
        genderTv = (TextView) findViewById(R.id.gender_detail_textview);
        massTv = (TextView) findViewById(R.id.mass_detail_textview);
        hairTv = (TextView) findViewById(R.id.hair_color_detail_textview);
        skinTv = (TextView) findViewById(R.id.skin_color_detail_textview);
        eyeTv = (TextView) findViewById(R.id.eye_color_detail_textview);
        birthTv = (TextView) findViewById(R.id.birth_year_detail_textview);
        homeworldTv = (TextView) findViewById(R.id.birth_year_detail_textview);
        speciesTv = (TextView) findViewById(R.id.homeworld_detail_textview);

        nameTv.setText(name);
        heightTv.setText(height);
        genderTv.setText(gender);
        massTv.setText(mass);
        hairTv.setText(hair_color);
        skinTv.setText(skin_color);
        eyeTv.setText(eye_color);
        birthTv.setText(birth_year);
        homeworldTv.setText(homeworld);
        speciesTv.setText(species);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
