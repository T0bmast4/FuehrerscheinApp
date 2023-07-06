package dev.tobi.fuehrerscheinapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import dev.tobi.fuehrerscheinapp.utils.MyApp;

public class DetailActivity extends AppCompatActivity {

    TextView start_txt_detail, ziel_txt_detail, kilometer_txt_detail, kilometerstand_txt_detail, datum_txt_detail, zeit_txt_detail, wetter_txt_detail;
    String start, ziel, kilometer, kilometer_stand, datum, zeit, wetter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        start_txt_detail = findViewById(R.id.start_txt_detail);
        ziel_txt_detail = findViewById(R.id.ziel_txt_detail);
        kilometer_txt_detail = findViewById(R.id.kilometer_txt_detail);
        kilometerstand_txt_detail = findViewById(R.id.kilometerstand_txt_detail);
        datum_txt_detail = findViewById(R.id.date_txt_detail);
        zeit_txt_detail = findViewById(R.id.time_txt_detail);
        wetter_txt_detail = findViewById(R.id.weather_txt_detail);

        getIntentData();
    }

    private void getIntentData() {
        if(getIntent().hasExtra("start") && getIntent().hasExtra("ziel") && getIntent().hasExtra("kilometer") && getIntent().hasExtra("kilometer_stand") && getIntent().hasExtra("datum") && getIntent().hasExtra("zeit") && getIntent().hasExtra("wetter")) {
            start = getIntent().getStringExtra("start");
            ziel = getIntent().getStringExtra("ziel");
            kilometer = getIntent().getStringExtra("kilometer");
            kilometer_stand = getIntent().getStringExtra("kilometer_stand");
            datum = getIntent().getStringExtra("datum");
            zeit = getIntent().getStringExtra("zeit");
            wetter = getIntent().getStringExtra("wetter");

            start_txt_detail.setText(start);
            ziel_txt_detail.setText(ziel);
            kilometer_txt_detail.setText(kilometer);
            kilometerstand_txt_detail.setText(kilometer_stand);
            datum_txt_detail.setText(datum);
            zeit_txt_detail.setText(zeit);
            wetter_txt_detail.setText(wetter);
        }
    }

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