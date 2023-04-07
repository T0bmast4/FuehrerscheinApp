package dev.tobi.fuehrerscheinapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dev.tobi.fuehrerscheinapp.mysql.MySQL;
import dev.tobi.fuehrerscheinapp.mysql.SQLAccounts;
import dev.tobi.fuehrerscheinapp.utils.MyApp;

public class LoginActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private Button loginButton;
    private TextView registerText;
    private TextView errorText;

    public static String PREFS_NAME = "login";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_login);

        getSupportActionBar().setDisplayShowHomeEnabled(true);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.registerButton);
        registerText = findViewById(R.id.registerText);
        errorText = findViewById(R.id.errorText);

        //Login Button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                manager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                if (!username.getText().toString().equals("") && !username.getText().toString().contains(" ")) {
                    if (!password.getText().toString().equals("") && !password.getText().toString().contains(" ")) {
                        if (SQLAccounts.accountExists(String.valueOf(username.getText()))) {
                            Argon2PasswordEncoder encoder = new Argon2PasswordEncoder(32, 64, 1, 15 * 1024, 2);
                            boolean validPassword = encoder.matches(password.getText() + "Hochpräzisionsereigniszeitgeber", SQLAccounts.getPassword(String.valueOf(username.getText())));
                            if (validPassword) {
                                SharedPreferences sharedPreferences = getSharedPreferences(LoginActivity.PREFS_NAME, 0);
                                SharedPreferences.Editor editor = sharedPreferences.edit();

                                editor.putBoolean("hasLoggedIn", true);
                                editor.putString("loggedInUser", username.getText().toString());
                                editor.commit();
                                MyApp.loadActivity(LoginActivity.this, MainActivity.class, true);
                            } else {
                                errorText.setText(R.string.wrongPassword);
                            }
                        } else {
                            errorText.setText(R.string.accountNotFound);
                        }
                    } else {
                        errorText.setText(R.string.wrongPassword);
                    }
                } else {
                    errorText.setText(R.string.emptyUsername);
                }
            }
        });

        //Register Button
        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApp.loadActivity(LoginActivity.this, RegisterActivity.class, false);
            }
        });
    }

    public final static ExecutorService EXECUTOR_SERVICE = Executors.newCachedThreadPool();
}