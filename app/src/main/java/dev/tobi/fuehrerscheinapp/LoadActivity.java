package dev.tobi.fuehrerscheinapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import dev.tobi.fuehrerscheinapp.mysql.MySQL;

public class LoadActivity extends AppCompatActivity {

    public static MySQL mysql;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        connectMySQL();
        Toast.makeText(LoadActivity.this, "MySQL connected", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = getSharedPreferences(LoginActivity.PREFS_NAME, 0);
                boolean hasLoggedIn = sharedPreferences.getBoolean("hasLoggedIn", false);

                if(hasLoggedIn) {
                    loadActivity(MainActivity.class);
                } else {
                    loadActivity(LoginActivity.class);
                }
            }
        }, 3000);
    }

    private void connectMySQL() {
        mysql = new MySQL("38.242.141.75", "FuehrerscheinApp", "fuehrerscheinapp", "YVwNew6n6bTFW2E");
    }

    private void loadActivity(Class classActivity) {
        Intent activity = new Intent(this, classActivity);
        startActivity(activity);
        finish();
    }
}