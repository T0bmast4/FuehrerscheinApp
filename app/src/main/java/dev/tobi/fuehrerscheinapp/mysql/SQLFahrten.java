package dev.tobi.fuehrerscheinapp.mysql;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import dev.tobi.fuehrerscheinapp.LoadActivity;

public class SQLFahrten {

    public static void addCarDrive(String user, String from, String to, String kilometers, String kilometerStand, String time, String weather) {
        Calendar calender = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String date = dateFormat.format(calender.getTime());
        LoadActivity.mysql.update("INSERT INTO `Fahrten`(`USER`, `DATUM`, `VON`, `BIS`, `KILOMETER`, `KILOMETERSTAND`, `ZEIT`, `WETTER`) VALUES (?,?,?,?,?,?,?,?);", user, date, from, to, kilometers, kilometerStand, time, weather);
    }
}
