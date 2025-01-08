// DoctorController.java
package com.telemed.telemed;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class DoctorController {

    @GetMapping("/patientList")
    public String showPatientList(Model model) {
        model.addAttribute("patients", getPatients());
        return "patientList";
    }

    @GetMapping("/addPatient")
    public String showAddPatientForm() {
        return "addPatient";
    }

    @PostMapping("/addPatient")
    public String addPatient(
            @RequestParam String ime,
            @RequestParam String prezime,
            @RequestParam String email,
            @RequestParam String telefon,
            @RequestParam String adresa,
            @RequestParam String grad,
            Model model) {
        // Dodavanje novog pacijenta (simulirano)
      //  PatientRecord newPatient = new PatientRecord(ime, prezime, email, telefon, adresa, grad);
     //   patients.add(newPatient);
        return "redirect:/patientList";
    }

    private final List<PatientRecord> patients = new ArrayList<>();

    private List<PatientRecord> getPatients() {
//        if (patients.isEmpty()) {
//            patients.add(new PatientRecord("Ivan", "Horvat", "ivan.horvat@example.com", "123456789", "Adresa 1", "Zagreb"));
//            patients.add(new PatientRecord("Ana", "Marić", "ana.maric@example.com", "987654321", "Adresa 2", "Split"));
//        }
      return patients;
  }

}


