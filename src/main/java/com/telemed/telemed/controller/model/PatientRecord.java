package com.telemed.telemed.controller.model;

import jakarta.persistence.*;

@Entity
public class PatientRecord {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String datum;
    private int sistolickiTlak;
    private int dijastolickiTlak;
    private int otkucajiSrca;
    private String opis;

    public Long getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(Long appUserId) {
        this.appUserId = appUserId;
    }

    @Column(name = "app_user_id")
    private Long appUserId;

    public PatientRecord(String datum, int sistolickiTlak, int dijastolickiTlak, int otkucajiSrca, String opis) {
        this.datum = datum;
        this.sistolickiTlak = sistolickiTlak;
        this.dijastolickiTlak = dijastolickiTlak;
        this.otkucajiSrca = otkucajiSrca;
        this.opis = opis;
    }
    public PatientRecord() {
        // Default constructor required by Hibernate
    }


    public int getId() {
        return Math.toIntExact(id);
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
