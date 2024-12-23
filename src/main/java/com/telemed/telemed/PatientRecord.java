package com.telemed.telemed;

public class PatientRecord {
    private static int counter = 0; // Counter for unique IDs
    private final int id;
    private String datum;
    private String tlak;
    private String opis;

    public PatientRecord(String datum, String tlak, String opis) {
        this.id = ++counter; // Increment ID for each new record
        this.datum = datum;
        this.tlak = tlak;
        this.opis = opis;
    }

    public int getId() {
        return id;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public String getTlak() {
        return tlak;
    }

    public void setTlak(String tlak) {
        this.tlak = tlak;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }
}
