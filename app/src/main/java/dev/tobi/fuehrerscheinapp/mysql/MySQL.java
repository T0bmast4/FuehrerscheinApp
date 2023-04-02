package dev.tobi.fuehrerscheinapp.mysql;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicReference;

public class MySQL {

    private String HOST = "";

    private String DATABASE = "";

    private String USER = "";

    private String PASSWORD = "";

    private Connection con;
    private ExecutorService executorService;

    public MySQL(String host, String database, String user, String password) {
        this.HOST = host;
        this.DATABASE = database;
        this.USER = user;
        this.PASSWORD = password;
        executorService = Executors.newCachedThreadPool();
        executorService.submit(this::connect);
    }

    public void connect() {
        try {
            this.con = DriverManager.getConnection("jdbc:mysql://" + this.HOST + ":3306/" + this.DATABASE + "?autoReconnect=true", this.USER, this.PASSWORD);
            System.out.println("[MySQL] Die Verbindung zur MySQL wurde erfolgreich hergestellt!");
        } catch (SQLException e) {
            System.out.println("[MySQL] Die Verbindung zur MySQL ist fehlgeschlagen! Fehler: " + e.getMessage());
        }
    }

    public void close() {
        try {
            if (this.con != null) {
                this.con.close();
                System.out.println("[MySQL] Die Verbindung zur MySQL wurde erfolgreich beendet!");
            }
        } catch (SQLException e) {
            System.out.println("[MySQL] Fehler beim beenden der Verbindung zu MySQL! Fehler: " + e.getMessage());
        }
    }

    public boolean isConnected() {
        return !(this.con == null);
    }


    public void update(String qry, Object... params) {
        Executors.newCachedThreadPool().submit(() -> {
            int i = 0;
            try {
                PreparedStatement statement = con.prepareStatement(qry);
                for(Object obj : params) {
                    i++;
                    String dataType = obj.getClass().getSimpleName();
                    switch(dataType) {
                        case "Boolean":
                            statement.setBoolean(i, (Boolean)obj);
                            break;
                        case "String":
                            statement.setString(i, (String)obj);
                            break;
                        case "Integer":
                            statement.setInt(i, (Integer)obj);
                            break;
                        case "Float":
                            statement.setFloat(i, (Float)obj);
                            break;
                    }

                }
                statement.executeUpdate();
            }catch (SQLException e) {
                connect();
                System.err.println(e);
            }
        });
    }

    public ResultSet query(String qry, Object... params) {
        try {
            Future<ResultSet> futureResult = executorService.submit(() -> {
                int i = 0;
                try {
                    PreparedStatement statement = con.prepareStatement(qry);
                    for (Object obj : params) {
                        i++;
                        String dataType = obj.getClass().getSimpleName();
                        switch (dataType) {
                            case "Boolean":
                                statement.setBoolean(i, (Boolean) obj);
                                break;
                            case "String":
                                statement.setString(i, (String) obj);
                                break;
                            case "Integer":
                                statement.setInt(i, (Integer) obj);
                                break;
                            case "Float":
                                statement.setFloat(i, (Float) obj);
                                break;
                        }
                    }
                    return statement.executeQuery();
                } catch (SQLException e) {
                    connect();
                    System.err.println(e);
                }
                return null;
            });

            ResultSet result = futureResult.get();
            return result;
        }catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Connection getConnection() {
        return this.con;
    }

    public void setConnection(Connection con) {
        this.con = con;
    }
}
