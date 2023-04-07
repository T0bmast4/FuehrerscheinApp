package dev.tobi.fuehrerscheinapp.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import dev.tobi.fuehrerscheinapp.LoadActivity;
import dev.tobi.fuehrerscheinapp.LoginActivity;

public class MyApp extends Application {

    private static MyApp _instance;

    @Override
    public void onCreate() {
        super.onCreate();
        _instance = this;
    }

    public static boolean isLoggedIn() {
        SharedPreferences sharedPreferences = getMe().getSharedPreferences(LoginActivity.PREFS_NAME, 0);
        return sharedPreferences.getBoolean("hasLoggedIn", false);
    }

    public static String getLoggedInUser() {
        SharedPreferences sharedPreferences = getMe().getSharedPreferences(LoginActivity.PREFS_NAME, 0);
        return sharedPreferences.getString("loggedInUser", "");
    }

    public static void debug(String message, Context context) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void loadActivity(Activity currentActivity, Class<? extends Activity> targetActivityClass, boolean finishCurrentActivity) {
        Intent intent = new Intent(currentActivity, targetActivityClass);
        currentActivity.startActivity(intent);
        if (finishCurrentActivity) {
            currentActivity.finish();
        }
    }

    private static MyApp getMe() {
        return _instance;
    }
}
