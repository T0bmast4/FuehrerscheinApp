package dev.tobi.fuehrerscheinapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import dev.tobi.fuehrerscheinapp.mysql.SQLAccounts;
import dev.tobi.fuehrerscheinapp.utils.MyApp;

public class RegisterActivity extends AppCompatActivity {
    private EditText username;
    private EditText password;

    private Button registerButton;
    private TextView errorText;
    private TextView correctText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        registerButton = findViewById(R.id.registerButton);
        errorText = findViewById(R.id.errorText);
        correctText = findViewById(R.id.correctText);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                manager.hideSoftInputFromWindow(v.getWindowToken(), 0);

                if (username.getText().toString().equals("")) {
                    errorText.setText(R.string.emptyUsername);
                    return;
                }
                if (username.getText().toString().contains(" ")) {
                    errorText.setText(R.string.usernameWithSpace);
                    return;
                }
                if (password.getText().toString().equals("")) {
                    errorText.setText(R.string.emptyPassword);
                    return;
                }
                if (password.getText().toString().contains(" ")) {
                    errorText.setText(R.string.passwordWithSpace);
                    return;
                }
                if (SQLAccounts.accountExists(username.getText().toString())) {
                    errorText.setText(R.string.accountExists);
                    return;
                }


                SQLAccounts.registerAccount(username.getText().toString(), password.getText().toString());
                correctText.setText(R.string.correctText);
                errorText.setText("");
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        MyApp.setUser(username.getText().toString());
                        MyApp.loadActivity(RegisterActivity.this, MainActivity.class, true);
                    }
                }, 1500);
            }
        });
    }
}