// PatientController.java
package com.telemed.telemed.controller.controller;

import com.telemed.telemed.controller.model.AppUser;
import com.telemed.telemed.controller.model.PatientRecord;
import com.telemed.telemed.controller.model.PatientService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class PatientController {

    @Autowired
    private PatientService patientService;

    @GetMapping("/formPatient")
    public String showFormPatient(HttpSession session, Model model) {
        AppUser user = (AppUser) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login"; // Ako korisnik nije prijavljen, preusmjerite ga na login
        }
        model.addAttribute("userName", user.getIme() + " " + user.getPrezime());
        return "formPatient";
    }


    @PostMapping("/formPatient")
    public String saveRecord(HttpSession session,
                             @RequestParam int sistolickiTlak,
                             @RequestParam int dijastolickiTlak,
                             @RequestParam int otkucajiSrca,
                             @RequestParam(required = false) String opis) {
        AppUser user = (AppUser) session.getAttribute("user"); // Dohvaćanje trenutno prijavljenog korisnika
        if (user == null || !user.isPatient()) {
            return "redirect:/login"; // Ako korisnik nije prijavljen, preusmjeri na login
        }

        try {
            // Validacija unosa
            if (sistolickiTlak <= 0 || dijastolickiTlak <= 0 || otkucajiSrca <= 0) {
                throw new IllegalArgumentException("Vrijednosti moraju biti pozitivne!");
            }

            // Formatiranje datuma
            String datum = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));

            // Kreiranje novog zapisa
            PatientRecord record = new PatientRecord(datum, sistolickiTlak, dijastolickiTlak, otkucajiSrca, opis);
            record.setAppUserId(user.getId().longValue()); // Povezivanje zapisa s korisnikom

            // Spremanje zapisa putem servisa
            patientService.addRecord(record);

            return "redirect:/patientDetails"; // Preusmjeravanje na stranicu s detaljima
        } catch (Exception e) {
            e.printStackTrace();
            return "error"; // Prikaz stranice za grešku
        }
    }


    @GetMapping("/patientDetails")
    public String showPatientDetails(HttpSession session, Model model) {
        AppUser user = (AppUser) session.getAttribute("user"); // Dohvat korisnika iz sesije
        if (user == null || !user.isPatient()) {
            return "redirect:/login"; // Ako korisnik nije prijavljen ili nije pacijent
        }

        Long userId = Long.valueOf(user.getId()); // Dohvat ID-a prijavljenog pacijenta
        List<PatientRecord> userRecords = patientService.getRecordsByUserId(userId); // Filtriranje zapisa
        model.addAttribute("records", userRecords);
        model.addAttribute("patientName", user.getIme() + " " + user.getPrezime());
        return "patientDetails";
    }


    @GetMapping("/editRecord")
    public String editRecord(@RequestParam int id, Model model) {
        PatientRecord record = patientService.getRecordById((long) id);
        if (record != null) {
            model.addAttribute("record", record);
            return "editRecord";
        } else {
            return "error";
        }
    }


    @PostMapping("/deleteRecord")
    public String deleteRecord(@RequestParam int id) {
        boolean deleted = patientService.deleteRecord((long) id);
        if (deleted) {
            return "redirect:/patientDetails";
        } else {
            return "error";
        }
    }
    @PostMapping("/updateRecord")
    public String updateRecord(@RequestParam int id,
                               @RequestParam int sistolicki,
                               @RequestParam int dijastolicki,
                               @RequestParam int otkucaji,
                               @RequestParam String opis) {
        PatientRecord record = patientService.getRecordById((long) id);
        if (record != null) {
            record.setSistolickiTlak(sistolicki);
            record.setDijastolickiTlak(dijastolicki);
            record.setOtkucajiSrca(otkucaji);
            record.setOpis(opis);
            patientService.addRecord(record); // Ažuriraj zapis
            return "redirect:/patientDetails";
        } else {
            return "error";
        }
    }
    @GetMapping("/patientRecords")
    public String showPatientRecords(@RequestParam Long patientId, Model model) {
        AppUser patient = patientService.getUserById(patientId);
        List<PatientRecord> records = patientService.getRecordsByUserId(patientId);

        model.addAttribute("patient", patient);
        model.addAttribute("records", records);
        return "patientRecords";
    }



}
