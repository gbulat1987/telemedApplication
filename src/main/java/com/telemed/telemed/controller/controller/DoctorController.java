// DoctorController.java
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

import java.util.List;

@Controller
public class DoctorController {

    @Autowired
    private PatientService patientService;

    @GetMapping("/patientList")
    public String showPatientList(HttpSession session, Model model) {
        AppUser doctor = (AppUser) session.getAttribute("user");
        if (doctor == null || !doctor.isDoctor()) {
            return "redirect:/login";
        }

        Long doctorId = Long.valueOf(doctor.getId());
        List<AppUser> patients = patientService.getPatientsByDoctorId(doctorId);

        // Dodavanje zadnjeg zapisa svakom pacijentu
        for (AppUser patient : patients) {
            PatientRecord lastRecord = patientService.getLastRecordByUserId(patient.getId().longValue());

            patient.setLastRecord(lastRecord);
        }

        model.addAttribute("patients", patients);
        return "patientList";
    }


    @PostMapping("/addPatient")
    public String addPatient(HttpSession session,
                             @RequestParam String ime,
                             @RequestParam String prezime,
                             @RequestParam String email,
                             @RequestParam String telefon,
                             @RequestParam String adresa,
                             @RequestParam String grad,
                             @RequestParam String lozinka) {
        AppUser doctor = (AppUser) session.getAttribute("user"); // Dohvati prijavljenog doktora
        if (doctor == null || !doctor.isDoctor()) {
            return "redirect:/login"; // Preusmjeri ako korisnik nije doktor
        }

        AppUser newPatient = new AppUser();
        newPatient.setIme(ime);
        newPatient.setPrezime(prezime);
        newPatient.setEmail(email);
        newPatient.setTelefon(telefon);
        newPatient.setAdresa(adresa);
        newPatient.setGrad(grad);
        newPatient.setPassword(lozinka);
        newPatient.setUserTypeId(2); // Postavi korisnika kao pacijenta
        newPatient.setDoctorId(doctor.getId()); // Poveži pacijenta s doktorom

        patientService.addUser(newPatient); // Spremi pacijenta
        return "redirect:/patientList";
    }
    @GetMapping("/addPatient")
    public String showAddPatientForm() {
        return "addPatient";
    }

}

