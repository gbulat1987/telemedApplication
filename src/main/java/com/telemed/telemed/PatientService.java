package com.telemed.telemed;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Service
public class PatientService {

    private final List<PatientRecord> records = new ArrayList<>();
    private boolean initialized = false; // Za praćenje inicijalizacije

    public List<PatientRecord> getAllRecords() {
        if (!initialized) {
            initializeRecords(); // Pozivanje metode
            initialized = true; // Oznaka da su podaci inicijalizirani
        }
        return records;
    }

    private void initializeRecords() {
        // Dodavanje hardkodiranih podataka samo jednom
        records.add(new PatientRecord("22.12.2024 12:00", 120, 80, 70, "Normalan tlak"));
        records.add(new PatientRecord("21.12.2024 18:00", 140, 90, 80, "Lagano povišen tlak"));
        records.add(new PatientRecord("20.12.2024 16:00", 130, 85, 75, "Normalan tlak"));
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


    public boolean deleteRecord(int id) {
        return records.removeIf(record -> record.getId() == id);
    }

    @GetMapping("/editRecord")
    public String editRecord(@RequestParam int id, Model model) {
        PatientService patientService = null;
        PatientRecord record = patientService.getRecordById(id);
        if (record != null) {
            model.addAttribute("record", record);
            return "editRecord";
        } else {
            return "error"; // Prikaz stranice za grešku
        }
    }

}
