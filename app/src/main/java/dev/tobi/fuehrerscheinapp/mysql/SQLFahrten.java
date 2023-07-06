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

        int id = getIDs(user) + 1;

        LoadActivity.mysql.update("INSERT INTO `Fahrten`(`ID`, `USER`, `DATUM`, `START`, `ZIEL`, `KILOMETER`, `KILOMETERSTAND`, `ZEIT`, `WETTER`) VALUES (?,?,?,?,?,?,?,?,?);", id, user, date, start, ziel, kilometers, kilometerStand, time, weather);
    }

    public static ArrayList<Fahrt> getAllDrives(String username, boolean lastFive) {
        ArrayList<Fahrt> fahrtenList = new ArrayList<>();
        ResultSet rs = null;
        try {
            if(lastFive) {
                rs = LoadActivity.mysql.query("SELECT * FROM Fahrten WHERE USER=? ORDER BY ID DESC LIMIT 5", username);
            }else{
                rs = LoadActivity.mysql.query("SELECT * FROM Fahrten WHERE USER=?", username);
            }
            if(rs == null) return fahrtenList;
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

    public static int getIDs(String user) {
        ResultSet rs = LoadActivity.mysql.query("SELECT COUNT(ID) AS CID FROM `Fahrten` WHERE USER=?", user);
        if(rs != null) {
            try {
                if (rs.next()) {
                    return rs.getInt("CID");
                }
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }
}
