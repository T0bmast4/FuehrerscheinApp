package dev.tobi.fuehrerscheinapp.mysql;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import dev.tobi.fuehrerscheinapp.MainActivity;

public class SQLAccounts {

    private static final String KEY = "";

    public static boolean accountExists(String username) {
        try {
            ResultSet rs = MainActivity.mysql.query("SELECT * FROM Accounts WHERE USERNAME= '" + username + "'");

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
            MainActivity.mysql.update("INSERT INTO Accounts(USERNAME, PASSWORD) VALUES ('" + username + "', '" + password + "');");
            /*try {
            MainActivity.mysql.update("INSERT INTO Accounts(USERNAME, PASSWORD) VALUES ('" + username + "', '" + encryptPassword(password) + "');");
            } catch (Exception e) {

            }*/
        }
    }


    public static String encryptPassword(String data) throws Exception {
        Key key = generateKey();
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedValue = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedValue);
    }

    public static String decryptPassword(String encryptedData) throws Exception {
        Key key = generateKey();
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decodedValue = Base64.getDecoder().decode(encryptedData.getBytes());
        byte[] decryptedValue = cipher.doFinal(decodedValue);
        return new String(decryptedValue, StandardCharsets.UTF_8);
    }

    private static Key generateKey() throws Exception {
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        byte[] keyBytes = sha.digest(KEY.getBytes(StandardCharsets.UTF_8));
        return new SecretKeySpec(keyBytes, "AES");
    }
}
