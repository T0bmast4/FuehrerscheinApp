package dev.tobi.fuehrerscheinapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import dev.tobi.fuehrerscheinapp.utils.MyApp;

public class ListActivity extends AppCompatActivity {

    private FloatingActionButton newItemButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        newItemButton = findViewById(R.id.newItemButton);

        newItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApp.loadActivity(ListActivity.this, AddActivity.class, false);
            }
        });
    }
}