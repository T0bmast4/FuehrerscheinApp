package dev.tobi.fuehrerscheinapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import java.sql.Array;
import java.util.ArrayList;

import dev.tobi.fuehrerscheinapp.mysql.MySQL;
import dev.tobi.fuehrerscheinapp.mysql.SQLFahrten;
import dev.tobi.fuehrerscheinapp.utils.CustomAdapter;
import dev.tobi.fuehrerscheinapp.utils.Fahrt;
import dev.tobi.fuehrerscheinapp.utils.MyApp;

public class LoadActivity extends AppCompatActivity {

    public static MySQL mysql;
    private static boolean activityCreated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        if (activityCreated) {
            return;
        }
        activityCreated = true;
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                connectMySQL();
            }
        });
        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        MyApp.debug("MySQL connected", LoadActivity.this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (MyApp.isLoggedIn()) {
                    MyApp.loadActivity(LoadActivity.this, MainActivity.class, true);
                } else {
                    MyApp.loadActivity(LoadActivity.this, LoginActivity.class, true);
                }
            }
        }, 3000);
    }

    private void connectMySQL() {
        mysql = new MySQL("", "FuehrerscheinApp", "fuehrerscheinapp", "");
    }
}