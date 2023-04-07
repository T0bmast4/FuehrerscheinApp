package dev.tobi.fuehrerscheinapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import dev.tobi.fuehrerscheinapp.mysql.MySQL;
import dev.tobi.fuehrerscheinapp.utils.MyApp;

public class LoadActivity extends AppCompatActivity {

    public static MySQL mysql;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        connectMySQL();
        MyApp.debug("MySQL connected", LoadActivity.this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(MyApp.isLoggedIn()) {
                    MyApp.loadActivity(LoadActivity.this, MainActivity.class, true);
                } else {
                    MyApp.loadActivity(LoadActivity.this, LoginActivity.class, true);
                }
            }
        }, 3000);
    }

    private void connectMySQL() {
        mysql = new MySQL("38.242.141.75", "FuehrerscheinApp", "fuehrerscheinapp", "YVwNew6n6bTFW2E");
    }
}