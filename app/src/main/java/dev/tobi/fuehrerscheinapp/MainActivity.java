package dev.tobi.fuehrerscheinapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.snackbar.Snackbar;

import dev.tobi.fuehrerscheinapp.utils.MyApp;

public class MainActivity extends AppCompatActivity {

    private Button listButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        //Snackbar snackbar = Snackbar.make(view, "test", "3");
        //snackbar.show();

        listButton = findViewById(R.id.listButton);

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
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Logout");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getTitle().equals("Logout")) {
            SharedPreferences sharedPreferences = getSharedPreferences(LoginActivity.PREFS_NAME, 0);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putBoolean("hasLoggedIn", false);
            editor.putString("loggedInUser", "");
            editor.commit();
            MyApp.loadActivity(MainActivity.this, LoginActivity.class, true);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}