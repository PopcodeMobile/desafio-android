package com.example.desafiopopcode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Favs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favs);

        final TextView text = findViewById(R.id.textView);

        Intent intent = getIntent();
        String favs = intent.getStringExtra("lista");

        text.setText(favs);

        Button voltar = findViewById(R.id.button4);
        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}