// PatientController.java
package com.telemed.telemed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class PatientController {

    @Autowired
    private PatientService patientService;

    @GetMapping("/formPatient")
    public String showFormPatient() {
        return "formPatient";
    }

    @PostMapping("/formPatient")
    public String saveRecord(
            @RequestParam(required = true) String sistolickiTlak,
            @RequestParam(required = true) String dijastolickiTlak,
            @RequestParam(required = true) String otkucajiSrca,
            @RequestParam(required = false) String opis) {
        try {
            int sistolic = Integer.parseInt(sistolickiTlak);
            int diastolic = Integer.parseInt(dijastolickiTlak);
            int heartRate = Integer.parseInt(otkucajiSrca);

            if (sistolic <= 0 || diastolic <= 0 || heartRate <= 0) {
                throw new IllegalArgumentException("Vrijednosti moraju biti pozitivne!");
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
            String datum = LocalDateTime.now().format(formatter);
            String tlak = sistolickiTlak + "/" + dijastolickiTlak + ", " + otkucajiSrca;

            PatientRecord record = new PatientRecord(datum, tlak, opis);
            patientService.addRecord(record);

            return "redirect:/patientDetails";
        } catch (Exception e) {
            e.printStackTrace();
            return "error"; // Prikaz posebne stranice s pogreškom
        }
    }

    @GetMapping("/patientDetails")
    public String showPatientDetails(Model model) {
        model.addAttribute("records", patientService.getAllRecords());
        model.addAttribute("patientName", "Goran Bulat");
        model.addAttribute("editableId", null); // Osigurajte da je postavljen na početku
        return "patientDetails";
    }


    @GetMapping("/editRecord")
    public String editRecord(@RequestParam int id, Model model) {
        model.addAttribute("editableId", id); // Postavljanje zapisa za uređivanje
        model.addAttribute("records", patientService.getAllRecords());
        model.addAttribute("patientName", "Goran Bulat"); // Osigurajte da se ime pacijenta prosljeđuje
        return "patientDetails";
    }

    @PostMapping("/updateRecord")
    public String updateRecord(
            @RequestParam int id,
            @RequestParam String tlak,
            @RequestParam String opis) {
        boolean updated = patientService.updateRecord(id, null, tlak, opis); // Datum ostaje nepromijenjen
        if (updated) {
            return "redirect:/patientDetails"; // Vraća na stranicu s listom zapisa
        } else {
            return "error";
        }
    }


    @PostMapping("/deleteRecord")
    public String deleteRecord(@RequestParam int id) {
        boolean deleted = patientService.deleteRecord(id);
        if (deleted) {
            return "redirect:/patientDetails";
        } else {
            return "error";
        }
    }
}
