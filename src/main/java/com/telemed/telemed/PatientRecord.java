package com.telemed.telemed;

public class PatientRecord {
    private static int counter = 0;
    private final int id;
    private String datum;
    private int sistolickiTlak;
    private int dijastolickiTlak;
    private int otkucajiSrca;
    private String opis;

    public PatientRecord(String datum, int sistolickiTlak, int dijastolickiTlak, int otkucajiSrca, String opis) {
        this.id = ++counter;
        this.datum = datum;
        this.sistolickiTlak = sistolickiTlak;
        this.dijastolickiTlak = dijastolickiTlak;
        this.otkucajiSrca = otkucajiSrca;
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

    public int getSistolickiTlak() {
        return sistolickiTlak;
    }

    public void setSistolickiTlak(int sistolickiTlak) {
        this.sistolickiTlak = sistolickiTlak;
    }

    public int getDijastolickiTlak() {
        return dijastolickiTlak;
    }

    public void setDijastolickiTlak(int dijastolickiTlak) {
        this.dijastolickiTlak = dijastolickiTlak;
    }

    public int getOtkucajiSrca() {
        return otkucajiSrca;
    }

    public void setOtkucajiSrca(int otkucajiSrca) {
        this.otkucajiSrca = otkucajiSrca;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }
}
