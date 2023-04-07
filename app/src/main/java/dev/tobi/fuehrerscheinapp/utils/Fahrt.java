package dev.tobi.fuehrerscheinapp.utils;

public class Fahrt {

    private int id;
    private String start;
    private String ziel;
    private String datum;
    private int kilometer;
    private int kilometer_stand;
    private String zeit;
    private String wetter;

    public Fahrt(int id, String datum, String start, String ziel, int kilometer, int kilometer_stand, String zeit, String wetter) {
        this.id = id;
        this.start = start;
        this.ziel = ziel;
        this.datum = datum;
        this.kilometer = kilometer;
        this.kilometer_stand = kilometer_stand;
        this.zeit = zeit;
        this.wetter = wetter;
    }

    public int getId() {
        return id;
    }

    public String getStart() {
        return start;
    }

    public String getZiel() {
        return ziel;
    }

    public String getDatum() {
        return datum;
    }

    public int getKilometer() {
        return kilometer;
    }

    public int getKilometer_stand() {
        return kilometer_stand;
    }

    public String getZeit() {
        return zeit;
    }

    public String getWetter() {
        return wetter;
    }
}
