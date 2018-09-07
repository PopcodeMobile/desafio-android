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
        TextView gender = findViewById(R.id.detailsCharacterGender);
        TextView height = findViewById(R.id.detailsCharacterHeight);
        TextView mass = findViewById(R.id.detailsCharacterMass);
        TextView hairColor = findViewById(R.id.detailsCharacterHairColor);
        TextView skinColor = findViewById(R.id.detailsCharacterSkinColor);
        TextView eyeColor = findViewById(R.id.detailsCharacterEyeColor);
        TextView birthYear = findViewById(R.id.detailsCharacterBirthYear);

        name.setText(character.getName());
        gender.setText(character.getGender());
        height.setText(character.getHeight());
        mass.setText(character.getMass());
        hairColor.setText(character.getHairColor());
        skinColor.setText(character.getSkinColor());
        eyeColor.setText(character.getEyeColor());
        birthYear.setText(character.getBirthYear());
    }
}
