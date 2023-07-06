package dev.tobi.fuehrerscheinapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import dev.tobi.fuehrerscheinapp.mysql.SQLFahrten;
import dev.tobi.fuehrerscheinapp.utils.CustomAdapter;
import dev.tobi.fuehrerscheinapp.utils.CustomAdapterOverview;
import dev.tobi.fuehrerscheinapp.utils.Fahrt;
import dev.tobi.fuehrerscheinapp.utils.MyApp;

public class MainActivity extends AppCompatActivity {

    private Button listButton;
    private RecyclerView recyclerView;

    private ArrayList<Integer> drive_id;
    private ArrayList<String> drive_start;
    private ArrayList<String> drive_ziel;
    private ArrayList<String> drive_datum;
    private ArrayList<Integer> drive_kilometer;
    private ArrayList<Integer> drive_kilometer_stand;
    private ArrayList<String> drive_zeit;
    private ArrayList<String> drive_wetter;
    private static CustomAdapterOverview customAdapterOverview;

    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this.getBaseContext();
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


        drive_id = new ArrayList<>();
        drive_start = new ArrayList<>();
        drive_ziel = new ArrayList<>();
        drive_kilometer = new ArrayList<>();
        drive_kilometer_stand = new ArrayList<>();
        drive_datum = new ArrayList<>();
        drive_zeit = new ArrayList<>();
        drive_wetter = new ArrayList<>();

        dataToArrayList();

        listButton = findViewById(R.id.listButton);
        recyclerView = findViewById(R.id.recyclerViewOverview);

        customAdapterOverview = new CustomAdapterOverview(MainActivity.this, drive_id, drive_start, drive_ziel, drive_kilometer, drive_kilometer_stand, drive_datum, drive_zeit, drive_wetter);
        recyclerView.setAdapter(customAdapterOverview);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApp.loadActivity(MainActivity.this, ListActivity.class, false);
            }
        });
    }



    @Override
    public void onBackPressed () {

    }

    @Override
    protected void onResume() {
        super.onResume();
        drive_id.clear();
        drive_start.clear();
        drive_ziel.clear();
        drive_datum.clear();
        drive_kilometer.clear();
        drive_kilometer_stand.clear();
        drive_zeit.clear();
        drive_wetter.clear();

        dataToArrayList();
        customAdapterOverview = new CustomAdapterOverview(MainActivity.this, drive_id, drive_start, drive_ziel, drive_kilometer, drive_kilometer_stand, drive_datum, drive_zeit, drive_wetter);
        recyclerView.setAdapter(customAdapterOverview);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
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

    private void dataToArrayList() {
        String user = MyApp.getLoggedInUser();
        ArrayList<Fahrt> fahrtenList = SQLFahrten.getAllDrives(user, true);
        for(int i = 0; i < fahrtenList.size(); i++) {
            drive_id.add(fahrtenList.get(i).getId());
            drive_start.add(fahrtenList.get(i).getStart());
            drive_ziel.add(fahrtenList.get(i).getZiel());
            drive_datum.add(fahrtenList.get(i).getDatum());
            drive_kilometer.add(fahrtenList.get(i).getKilometer());
            drive_kilometer_stand.add(fahrtenList.get(i).getKilometer_stand());
            drive_zeit.add(fahrtenList.get(i).getZeit());
            drive_wetter.add(fahrtenList.get(i).getWetter());
        }
    }

    public static Context getContext() {
        return context;
    }

    public static CustomAdapterOverview getCustomAdapterOverview() {
        return customAdapterOverview;
    }
}