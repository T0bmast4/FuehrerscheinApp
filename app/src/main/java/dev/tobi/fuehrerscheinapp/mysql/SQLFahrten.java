package dev.tobi.fuehrerscheinapp.mysql;

import android.database.Cursor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import dev.tobi.fuehrerscheinapp.LoadActivity;
import dev.tobi.fuehrerscheinapp.utils.Fahrt;

public class SQLFahrten {

    public static void addCarDrive(String user, String start, String ziel, int kilometers, int kilometerStand, String time, String weather) {
        Calendar calender = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String date = dateFormat.format(calender.getTime());
        LoadActivity.mysql.update("INSERT INTO `Fahrten`(`USER`, `DATUM`, `START`, `ZIEL`, `KILOMETER`, `KILOMETERSTAND`, `ZEIT`, `WETTER`) VALUES (?,?,?,?,?,?,?,?);", user, date, start, ziel, kilometers, kilometerStand, time, weather);
    }

    public static ArrayList<Fahrt> getAllDrives(String username) {
        ArrayList<Fahrt> fahrtenList = new ArrayList<>();
        try {
            ResultSet rs = LoadActivity.mysql.query("SELECT * FROM Fahrten WHERE USERNAME=?", username);
            while(rs.next()) {
                Fahrt fahrt = new Fahrt(rs.getInt("ID"),
                                        rs.getString("DATUM"),
                                        rs.getString("START"),
                                        rs.getString("ZIEL"),
                                        rs.getInt("KILOMETER"),
                                        rs.getInt("KILOMETERSTAND"),
                                        rs.getString("ZEIT"),
                                        rs.getString("WETTER"));
                fahrtenList.add(fahrt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fahrtenList;
    }
}
