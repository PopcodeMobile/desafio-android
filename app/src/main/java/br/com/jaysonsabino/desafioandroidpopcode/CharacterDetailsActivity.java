package br.com.jaysonsabino.desafioandroidpopcode;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import br.com.jaysonsabino.desafioandroidpopcode.entities.Character;

public class CharacterDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_details);

        Character character = (Character) getIntent().getSerializableExtra("character");

        if (character == null) {
            finish();
            return;
        }

        TextView name = findViewById(R.id.detailsCharacterName);
        name.setText(character.getName());
    }
}
