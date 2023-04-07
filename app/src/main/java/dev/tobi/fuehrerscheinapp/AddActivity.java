package dev.tobi.fuehrerscheinapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.sql.Array;
import java.util.ArrayList;

import dev.tobi.fuehrerscheinapp.mysql.SQLFahrten;
import dev.tobi.fuehrerscheinapp.utils.MyApp;

public class AddActivity extends AppCompatActivity {

    private EditText from_input, to_input, driven_input, kilometer_stand_input;
    private Spinner time_spinner, weather_spinner;
    private Button addButton;

    private ArrayList<Integer> drive_id;
    private ArrayList<String> drive_start;
    private ArrayList<String> drive_ziel;
    private ArrayList<String> drive_datum;
    private ArrayList<Integer> drive_kilometer;
    private ArrayList<Integer> drive_kilometer_stand;
    private ArrayList<String> drive_zeit;
    private ArrayList<String> drive_wetter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        from_input = findViewById(R.id.from_input);
        to_input = findViewById(R.id.to_input);
        driven_input = findViewById(R.id.driven_input);
        kilometer_stand_input = findViewById(R.id.kilometer_stand_input);

        time_spinner = findViewById(R.id.time_spinner);
        weather_spinner = findViewById(R.id.weather_spinner);

        addButton = findViewById(R.id.newItemButton);

        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this, R.array.time, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        time_spinner.setAdapter(adapter);


        ArrayAdapter<CharSequence> adapter2=ArrayAdapter.createFromResource(this, R.array.weather, android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weather_spinner.setAdapter(adapter2);

        drive_id = new ArrayList<>();
        drive_start = new ArrayList<>();
        drive_ziel = new ArrayList<>();
        drive_datum = new ArrayList<>();
        drive_kilometer = new ArrayList<>();
        drive_kilometer_stand = new ArrayList<>();
        drive_zeit = new ArrayList<>();
        drive_wetter = new ArrayList<>();

        dataToArraylist();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String time = time_spinner.getSelectedItem().toString();
                String weather = weather_spinner.getSelectedItem().toString();
                SQLFahrten.addCarDrive(MyApp.getLoggedInUser(),
                        from_input.getText().toString(),
                        to_input.getText().toString(),
                        Integer.parseInt(driven_input.getText().toString()),
                        Integer.parseInt(kilometer_stand_input.getText().toString()),
                        time, weather);
                finish();
            }
        });
    }

    private void dataToArraylist() {
        String user = MyApp.getLoggedInUser();
        for(int i = 0; i < SQLFahrten.getAllDrives(user).size(); i++) {
            drive_id.add(SQLFahrten.getAllDrives(user).get(i).getId());
            drive_start.add(SQLFahrten.getAllDrives(user).get(i).getStart());
            drive_ziel.add(SQLFahrten.getAllDrives(user).get(i).getZiel());
            drive_datum.add(SQLFahrten.getAllDrives(user).get(i).getDatum());
            drive_kilometer.add(SQLFahrten.getAllDrives(user).get(i).getKilometer());
            drive_kilometer_stand.add(SQLFahrten.getAllDrives(user).get(i).getKilometer_stand());
            drive_zeit.add(SQLFahrten.getAllDrives(user).get(i).getZeit());
            drive_wetter.add(SQLFahrten.getAllDrives(user).get(i).getWetter());
        }
    }
}