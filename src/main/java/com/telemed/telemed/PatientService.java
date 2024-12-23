package com.telemed.telemed;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class PatientService {

    private final List<PatientRecord> records = new ArrayList<>();
    private boolean initialized = false; // Za praćenje inicijalizacije

    public List<PatientRecord> getAllRecords() {
        if (!initialized) {
            initializeRecords();
            initialized = true; // Oznaka da su podaci inicijalizirani
        }
        return records;
    }

    private void initializeRecords() {
        // Dodavanje hardkodiranih podataka samo jednom
        records.add(new PatientRecord("22.12.2024 12:00", "120/80, 70", "Normalan tlak"));
        records.add(new PatientRecord("21.12.2024 18:00", "140/90, 80", "Lagano povišen tlak"));
        records.add(new PatientRecord("21.12.2024 18:00", "140/90, 80", "Lagano povišen tlak"));
        records.add(new PatientRecord("22.12.2024 12:00", "120/80, 70", "Normalan tlak"));

    }

    public void addRecord(PatientRecord record) {
        records.add(record);
    }
    public PatientRecord getRecordById(int id) {
        return records.stream()
                .filter(record -> record.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public boolean updateRecord(int id, String datum, String tlak, String opis) {
        PatientRecord record = getRecordById(id);
        if (record != null) {
            if (tlak != null) record.setTlak(tlak);
            if (opis != null) record.setOpis(opis);
            return true;
        }
        return false;
    }


    public boolean deleteRecord(int id) {
        return records.removeIf(record -> record.getId() == id);
    }
}
