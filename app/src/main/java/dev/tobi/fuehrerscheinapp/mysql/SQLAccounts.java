package dev.tobi.fuehrerscheinapp.mysql;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.ResultSet;
import java.sql.SQLException;

import dev.tobi.fuehrerscheinapp.MainActivity;

public class SQLAccounts {

    public static boolean accountExists(String username) {
        try {
            ResultSet rs = MainActivity.mysql.query("SELECT * FROM Accounts WHERE USERNAME=?", username);
            if(rs.next()) {
                return rs.getString("USERNAME") != null;
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void registerAccount(String username, String password) {
        if(!(accountExists(username))) {
            MainActivity.mysql.update("INSERT INTO Accounts(USERNAME, PASSWORD) VALUES (?, ?);", username, encryptPassword(password));
        }
    }

    public static String getPassword(String username) {
        try {
            ResultSet rs = MainActivity.mysql.query("SELECT * FROM Accounts WHERE USERNAME=?", username);
            if (rs.next())
                return rs.getString("PASSWORD");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String encryptPassword(String password) {
        Argon2PasswordEncoder encoder = new Argon2PasswordEncoder(32,64,1,15*1024,2);
        String passwordNormal = password + "Hochpräzisionsereigniszeitgeber";

        String encodedPassword = encoder.encode(passwordNormal);
        return encodedPassword;
    }
}
