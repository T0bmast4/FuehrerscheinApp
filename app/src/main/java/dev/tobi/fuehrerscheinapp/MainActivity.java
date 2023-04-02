package dev.tobi.fuehrerscheinapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
        connectMySQL();
        Toast.makeText(MainActivity.this, "MySQL connected", Toast.LENGTH_SHORT).show();

        getSupportActionBar().setDisplayShowHomeEnabled(true);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginbutton);

        //Login Button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SQLAccounts.accountExists(String.valueOf(username.getText()))) {
                    Argon2PasswordEncoder encoder = new Argon2PasswordEncoder(32,64,1,15*1024,2);
                    boolean validPassword = encoder.matches(password.getText() + "Hochpräzisionsereigniszeitgeber", SQLAccounts.getPassword(String.valueOf(username.getText())));
                    if(validPassword) {
                        loadOverview();
                    }
                }
            }
        });
    }

    private void connectMySQL() {
        mysql = new MySQL("38.242.141.75", "FuehrerscheinApp", "fuehrerscheinapp", "YVwNew6n6bTFW2E");
    }

    public final static ExecutorService EXECUTOR_SERVICE = Executors.newCachedThreadPool();

    private void loadOverview() {
        Intent overviewActivity = new Intent(this, OverviewActivity.class);
        startActivity(overviewActivity);
    }
}