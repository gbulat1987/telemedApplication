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
            @RequestParam int sistolickiTlak,
            @RequestParam int dijastolickiTlak,
            @RequestParam int otkucajiSrca,
            @RequestParam(required = false) String opis) {
        try {
            if (sistolickiTlak <= 0 || dijastolickiTlak <= 0 || otkucajiSrca <= 0) {
                throw new IllegalArgumentException("Vrijednosti moraju biti pozitivne!");
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
            String datum = LocalDateTime.now().format(formatter);

            PatientRecord record = new PatientRecord(datum, sistolickiTlak, dijastolickiTlak, otkucajiSrca, opis);
            patientService.addRecord(record);

            return "redirect:/patientDetails";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
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
        PatientRecord record = patientService.getRecordById(id);
        if (record != null) {
            model.addAttribute("record", record);
            return "editRecord";
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
    @PostMapping("/updateRecord")
    public String updateRecord(
            @RequestParam int id,
            @RequestParam int sistolicki,
            @RequestParam int dijastolicki,
            @RequestParam int otkucaji,
            @RequestParam String opis) {
        PatientRecord record = patientService.getRecordById(id);
        if (record != null) {
            record.setSistolickiTlak(sistolicki);
            record.setDijastolickiTlak(dijastolicki);
            record.setOtkucajiSrca(otkucaji);
            record.setOpis(opis);
            return "redirect:/patientDetails";
        } else {
            return "error";
        }
    }

}
