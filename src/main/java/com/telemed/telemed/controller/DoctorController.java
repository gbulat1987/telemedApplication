// DoctorController.java
package com.telemed.telemed.controller;

import com.telemed.telemed.model.AppUser;
import com.telemed.telemed.model.PatientRecord;
import com.telemed.telemed.model.PatientService;
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

        List<AppUser> patients = patientService.getPatientsForDoctor(doctor.getId());

        for (AppUser patient : patients) {
            PatientRecord lastRecord = patientService.getLastRecordByUserId(patient.getId());
            patient.setLastRecord(lastRecord);
        }

        model.addAttribute("patients", patients);
        model.addAttribute("currentDoctorId", doctor.getId());
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
        newPatient.setDoctorId((int) doctor.getId().longValue()); // Poveži pacijenta s doktorom

        patientService.addUser(newPatient); // Spremi pacijenta
        return "redirect:/patientList";
    }
    @GetMapping("/addPatient")
    public String showAddPatientForm() {
        return "addPatient";
    }
    @PostMapping("/assignPatientToDoctor")
    public String assignPatientToDoctor(@RequestParam Long patientId, HttpSession session) {
        AppUser doctor = (AppUser) session.getAttribute("user");
        if (doctor == null || !doctor.isDoctor()) {
            return "redirect:/login"; // Preusmjeri ako korisnik nije doktor
        }

        AppUser patient = patientService.getUserById(patientId);
        if (patient != null && patient.isPatient()) {
            patient.setDoctorId((int) doctor.getId().longValue()); // Poveži pacijenta s doktorom
            patientService.addUser(patient); // Ažuriraj pacijenta
        }

        return "redirect:/patientList"; // Vrati na listu pacijenata
    }
    @GetMapping("/patientListEdit")
    public String showEditPatientForm(@RequestParam Long patientId, Model model) {
        AppUser patient = patientService.getUserById(patientId);
        model.addAttribute("patient", patient);
        return "patientListEdit";
    }

    @PostMapping("/updatePatient")
    public String updatePatient(@RequestParam Long id,
                                @RequestParam String ime,
                                @RequestParam String prezime,
                                @RequestParam String email,
                                @RequestParam String telefon,
                                @RequestParam String adresa,
                                @RequestParam String grad) {
        AppUser patient = patientService.getUserById(id);
        if (patient != null) {
            patient.setIme(ime);
            patient.setPrezime(prezime);
            patient.setEmail(email);
            patient.setTelefon(telefon);
            patient.setAdresa(adresa);
            patient.setGrad(grad);
            patientService.addUser(patient);
        }
        return "redirect:/patientList";
    }
    @PostMapping("/deletePatient")
    public String deletePatient(@RequestParam Long patientId) {
        patientService.deleteUser(patientId);
        return "redirect:/patientList";
    }

}

