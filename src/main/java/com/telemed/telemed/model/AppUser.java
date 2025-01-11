package com.telemed.telemed.model;

import jakarta.persistence.*;

@Entity
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ime;
    private String prezime;
    private String email;
    private String lozinka;
    private String adresa;
    private String telefon;
    private String grad;

    @Transient // Ovaj atribut neće biti spremljen u bazu
    private PatientRecord lastRecord;

    public PatientRecord getLastRecord() {
        return lastRecord;
    }

    public void setLastRecord(PatientRecord lastRecord) {
        this.lastRecord = lastRecord;
    }

    public void setGrad(String grad) {
        this.grad = grad;
    }

    public String getGrad() {
        return grad;
    }

    @Column(name = "user_type_id")
    private Integer userTypeId;

    @Column(name = "doctor_id")
    private Long doctorId;

    public boolean isDoctor() {
        return userTypeId != null && userTypeId == 1;
    }

    public boolean isPatient() {
        return userTypeId != null && userTypeId == 2;
    }

    public Long getId() {
        return (long) Math.toIntExact(id);
    }

    public String getIme() {
        return ime;
    }

    public String getEmail() {
        return email;
    }

    public String getPrezime() {
        return prezime;
    }

    public String getPassword() {
        return lozinka;
    }

    public String getAdresa() {
        return adresa;
    }

    public String getTelefon() {
        return telefon;
    }

    public Integer getUserTypeId() {
        return userTypeId;
    }

    public Integer getDoctorId() {
        return doctorId == null ? null : Math.toIntExact(doctorId);
    }


    public void setIme(String ime) {
        this.ime = ime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.lozinka = password;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public void setUserTypeId(Integer userTypeId) {
        this.userTypeId = userTypeId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = Long.valueOf(doctorId);
    }

}

