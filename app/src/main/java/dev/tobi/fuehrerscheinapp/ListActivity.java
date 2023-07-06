package dev.tobi.fuehrerscheinapp;

import androidx.annotation.NonNull;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import dev.tobi.fuehrerscheinapp.mysql.MySQL;
import dev.tobi.fuehrerscheinapp.mysql.SQLFahrten;
import dev.tobi.fuehrerscheinapp.utils.CustomAdapter;
import dev.tobi.fuehrerscheinapp.utils.Fahrt;
import dev.tobi.fuehrerscheinapp.utils.MyApp;

public class ListActivity extends AppCompatActivity {

    private FloatingActionButton newItemButton;
    private RecyclerView recyclerView;

    private ArrayList<Integer> drive_id;
    private ArrayList<String> drive_start;
    private ArrayList<String> drive_ziel;
    private ArrayList<String> drive_datum;
    private ArrayList<Integer> drive_kilometer;
    private ArrayList<Integer> drive_kilometer_stand;
    private ArrayList<String> drive_zeit;
    private ArrayList<String> drive_wetter;
    private CustomAdapter customAdapter;

    private static Context context;

    private MySQL mysql;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this.getBaseContext();
        setContentView(R.layout.activity_list);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        newItemButton = findViewById(R.id.newItemButton);
        recyclerView = findViewById(R.id.recyclerView);

        drive_id = new ArrayList<>();
        drive_start = new ArrayList<>();
        drive_ziel = new ArrayList<>();
        drive_kilometer = new ArrayList<>();
        drive_kilometer_stand = new ArrayList<>();
        drive_datum = new ArrayList<>();
        drive_zeit = new ArrayList<>();
        drive_wetter = new ArrayList<>();

        dataToArrayList();
        customAdapter = new CustomAdapter(ListActivity.this, drive_id, drive_start, drive_ziel, drive_kilometer, drive_kilometer_stand, drive_datum, drive_zeit, drive_wetter);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(ListActivity.this));

        if(getIntent().hasExtra("scroll")) {
            recyclerView.smoothScrollToPosition(customAdapter.getItemCount());
        }

        newItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApp.loadActivity(ListActivity.this, AddActivity.class, true);
            }
        });
    }

    private void dataToArrayList() {
        String user = MyApp.getLoggedInUser();
        ArrayList<Fahrt> fahrtenList = SQLFahrten.getAllDrives(user, false);
        for (int i = 0; i < fahrtenList.size(); i++) {
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ListActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
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


    public static Context getContext() {
        return context;
    }
}