package dev.tobi.fuehrerscheinapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.Arrays;
import java.util.List;

import dev.tobi.fuehrerscheinapp.mysql.SQLFahrten;
import dev.tobi.fuehrerscheinapp.utils.MyApp;

public class AddActivity extends AppCompatActivity {

    private EditText start_input, end_input, driven_input, kilometer_stand_input;
    private Button addButton;
    private AutoCompleteTextView timeDropdown;
    private AutoCompleteTextView weatherDropdown;
    //private String destination;
    //private double lat1 = 0, long1 = 0, lat2 = 0, long2 = 0;
    //int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        start_input = findViewById(R.id.start_input);
        end_input = findViewById(R.id.end_input);
        driven_input = findViewById(R.id.driven_input);
        kilometer_stand_input = findViewById(R.id.kilometer_stand_input);
        timeDropdown = findViewById(R.id.timeDropdown);
        weatherDropdown = findViewById(R.id.weatherDropdown);
        addButton = findViewById(R.id.newItemButton);

        //Places.initialize(getApplicationContext(), "AIzaSyClUJOMKErRuZoK56JnJhxiZ8B35UEI0TY");

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.time, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeDropdown.setAdapter(adapter);
        timeDropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });


        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.weather, android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weatherDropdown.setAdapter(adapter2);
        weatherDropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(start_input.getText().toString().trim().isEmpty()) {
                    return;
                }
                SQLFahrten.addCarDrive(MyApp.getLoggedInUser(),
                        start_input.getText().toString(),
                        end_input.getText().toString(),
                        Integer.parseInt(driven_input.getText().toString()),
                        Integer.parseInt(kilometer_stand_input.getText().toString()),
                        timeDropdown.getText().toString(), weatherDropdown.getText().toString());
                Intent intent = new Intent(AddActivity.this, ListActivity.class);
                intent.putExtra("scroll", true);
                startActivity(intent);
            }
        });

        if(getIntent().hasExtra("start")) {
            start_input.setText(getIntent().getStringExtra("start"));
        }
        if(getIntent().hasExtra("ziel")) {
            end_input.setText(getIntent().getStringExtra("ziel"));
        }
        if(getIntent().hasExtra("weather")) {
            weatherDropdown.setText(getIntent().getStringExtra("weather"));
        }
        if(getIntent().hasExtra("zeit")) {
            timeDropdown.setText(getIntent().getStringExtra("zeit"));
        }
        if(getIntent().hasExtra("kilometer")) {
            driven_input.setText(getIntent().getStringExtra("kilometer"));
        }
    }


    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100 && requestCode == RESULT_OK) {
            Place place = Autocomplete.getPlaceFromIntent(data);
            if(destination.equals("start")) {
                flag++;
                start_input.setText(place.getAddress());
                String start = String.valueOf(place.getLatLng());
                start = start.replaceAll("lat/lng: ", "");
                start = start.replace("(", "");
                start = start.replace(")", "");
                String[] split = start.split(",");

                lat1 = Double.parseDouble(split[0]);
                long1 = Double.parseDouble(split[1]);
            }else{
                flag++;
                end_input.setText(place.getAddress());
                String end = String.valueOf(place.getLatLng());
                end = end.replaceAll("lat/lng: ", "");
                end = end.replace("(", "");
                end = end.replace(")", "");
                String[] split = end.split(",");

                lat2 = Double.parseDouble(split[0]);
                long2 = Double.parseDouble(split[1]);
            }

            if(flag >= 2) {
                distance(lat1, long1, lat2, long2);
            }
        }else if(requestCode == AutocompleteActivity.RESULT_ERROR) {
            Status status = Autocomplete.getStatusFromIntent(data);
            MyApp.debug(status.getStatusMessage(), getApplicationContext());
        }
    }

    private void distance(double lat1, double long1, double lat2, double long2) {
        double longDiff = long1 - long2;
        double distance = Math.sin(degToRad(lat1))
                * Math.sin(degToRad(lat2))
                + Math.cos(degToRad(lat1))
                * Math.cos(degToRad(lat2))
                * Math.cos(degToRad(longDiff));
        distance = Math.acos(distance);

        distance = radToDeg(distance);
        distance = distance * 60 * 1.1515;
        distance = distance * 1.609344;

        driven_input.setText(String.valueOf(distance));
    }

    private double radToDeg(double distance) {
        return (distance * 180.0 / Math.PI);
    }

    private double degToRad(double lat1) {
        return (lat1*Math.PI/180.0);
    }
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Logout");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getTitle().equals("Logout")) {
            SharedPreferences sharedPreferences = getSharedPreferences(LoginActivity.PREFS_NAME, 0);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putBoolean("hasLoggedIn", false);
            editor.putString("loggedInUser", "");
            editor.commit();
            MyApp.loadActivity(this, LoginActivity.class, true);
        }
        return super.onOptionsItemSelected(item);
    }
}
