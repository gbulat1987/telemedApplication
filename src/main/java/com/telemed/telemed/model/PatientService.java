package com.telemed.telemed.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PatientService {

    @Autowired
    private AppUserRepository appUserRepository;


    @Autowired
    private PatientRecordRepository patientRecordRepository;

    public List<PatientRecord> getAllRecords() {
        return patientRecordRepository.findAll();
    }

    public void addRecord(PatientRecord record) {
        patientRecordRepository.save(record);
    }

    public PatientRecord getRecordById(Long id) {
        return patientRecordRepository.findById(id).orElse(null);
    }

    public boolean deleteRecord(Long id) {
        if (patientRecordRepository.existsById(id)) {
            patientRecordRepository.deleteById(id);
            return true;
        }
        return false;
    }
    public void addUser(AppUser user) {
        appUserRepository.save(user);
    }

    public List<PatientRecord> getRecordsByUserId(Long userId) {
        return patientRecordRepository.findAll()
                .stream()
                .filter(record -> record.getAppUserId().equals(userId))
                .toList();
    }
    public List<AppUser> getPatientsByDoctorId(Long doctorId) {
        return appUserRepository.findByDoctorId(doctorId);
    }
    public PatientRecord getLastRecordByUserId(Long userId) {
        return patientRecordRepository.findFirstByAppUserIdOrderByDatumDesc(userId);
    }


    public AppUser getUserById(Long patientId) {
        return appUserRepository.findById(patientId).orElse(null);

    }
    public List<AppUser> getAllUsers() {
        return appUserRepository.findAll(); // Dohvaća sve korisnike iz baze
    }
    public List<AppUser> getPatientsForDoctor(Long doctorId) {
        // Kreiraj novu listu koja će biti promjenjiva
        List<AppUser> patients = new ArrayList<>();

        // Dohvati pacijente pod trenutnim doktorom
        List<AppUser> patientsUnderDoctor = appUserRepository.findByDoctorId(doctorId)
                .stream()
                .filter(user -> user.getUserTypeId() == 2) // Filtriraj samo pacijente
                .toList();

        // Dohvati pacijente koji nemaju doktora (doctorId = NULL)
        List<AppUser> patientsWithoutDoctor = appUserRepository.findByDoctorId(null)
                .stream()
                .filter(user -> user.getUserTypeId() == 2) // Filtriraj samo pacijente
                .toList();

        // Dodaj obje liste u mutable kolekciju
        patients.addAll(patientsUnderDoctor);
        patients.addAll(patientsWithoutDoctor);

        return patients;
    }
    public boolean deleteUser(Long userId) {
        if (appUserRepository.existsById(userId)) {
            appUserRepository.deleteById(userId);
            return true;
        }
        return false;
    }
}