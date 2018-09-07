package br.com.jaysonsabino.desafioandroidpopcode;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

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

        ViewDataBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_character_details);
        binding.setVariable(BR.character, character);
    }
}
