package dev.tobi.fuehrerscheinapp.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Calendar;

import dev.tobi.fuehrerscheinapp.ListActivity;
import dev.tobi.fuehrerscheinapp.LoadActivity;
import dev.tobi.fuehrerscheinapp.LoginActivity;
import dev.tobi.fuehrerscheinapp.MainActivity;
import dev.tobi.fuehrerscheinapp.mysql.SQLFahrten;

public class MyApp extends Application {

    private static MyApp _instance;

    @Override
    public void onCreate() {
        super.onCreate();
        _instance = this;
    }

    public static boolean isLoggedIn() {
        return getPrefBool("hasLoggedIn");
    }

    public static String getLoggedInUser() {
        return getPrefStr("loggedInUser");
    }

    public static void debug(String message, Context context) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void loadActivity(Context currentContext, Class<? extends Activity> targetActivityClass, boolean finishCurrentActivity) {
        Intent intent = new Intent(currentContext, targetActivityClass);
        currentContext.startActivity(intent);
        if (finishCurrentActivity) {
            Activity currentActivity = (Activity) currentContext;
            currentActivity.finish();
        }
    }

    public static void setUser(String username) {
        SharedPreferences sharedPreferences = getMe().getSharedPreferences(LoginActivity.PREFS_NAME, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean("hasLoggedIn", true);
        editor.putString("loggedInUser", username);
        editor.commit();
    }

    private static MyApp getMe() {
        return _instance;
    }

    private static boolean getPrefBool(String prefName) {
        SharedPreferences sharedPreferences = getMe().getSharedPreferences(LoginActivity.PREFS_NAME, 0);
        return sharedPreferences.getBoolean(prefName, false);
    }

    private static String getPrefStr(String prefName) {
        SharedPreferences sharedPreferences = getMe().getSharedPreferences(LoginActivity.PREFS_NAME, 0);
        return sharedPreferences.getString(prefName, "");
    }
}
