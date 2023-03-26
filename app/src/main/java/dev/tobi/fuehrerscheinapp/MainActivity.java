package dev.tobi.fuehrerscheinapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import dev.tobi.fuehrerscheinapp.mysql.MySQL;
import dev.tobi.fuehrerscheinapp.mysql.SQLAccounts;

public class MainActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private Button loginButton;
    public static MySQL mysql;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main);
        System.out.println("Test");
        connectMySQL();

        getSupportActionBar().setDisplayShowHomeEnabled(true);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginbutton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLAccounts.registerAccount("Tobi", "goijdfr42");
            }
        });
    }

    private void connectMySQL() {
        mysql = new MySQL("38.242.141.75", "FuehrerscheinApp", "fuehrerscheinapp", "9u&3$(&ggj4§549");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(R.string.DropDown1);
        menu.add(R.string.DropDown2);
        return super.onCreateOptionsMenu(menu);
    }

    private void loadOverview() {
        Intent switchActivity = new Intent(this, OverviewActivity.class);
        startActivity(switchActivity);
    }

}