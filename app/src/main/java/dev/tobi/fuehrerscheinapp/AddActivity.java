package dev.tobi.fuehrerscheinapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class AddActivity extends AppCompatActivity {

    private EditText from_input, to_input, driven_input, kilometre_stand_input;
    private Spinner time_spinner, weather_spinner;
    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        from_input = findViewById(R.id.from_input);
        to_input = findViewById(R.id.to_input);
        driven_input = findViewById(R.id.driven_input);
        kilometre_stand_input = findViewById(R.id.kilometre_stand_input);

        time_spinner = findViewById(R.id.time_spinner);
        weather_spinner = findViewById(R.id.weather_spinner);

        addButton = findViewById(R.id.newItemButton);

        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this, R.array.time, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        time_spinner.setAdapter(adapter);


        ArrayAdapter<CharSequence> adapter2=ArrayAdapter.createFromResource(this, R.array.weather, android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weather_spinner.setAdapter(adapter2);


    }
}