package com.telemed.telemed.controller;

import jakarta.servlet.http.HttpSession;
import com.telemed.telemed.model.AppUser;
import com.telemed.telemed.model.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class LoginController {

    @Autowired
    private AppUserRepository appUserRepository;


    @GetMapping("/login")
    public String showLoginPage() {
        System.out.println("Login page requested");
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String email,
                               @RequestParam String password,
                               Model model,
                               HttpSession session) {
        // Validacija ulaznih podataka
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            model.addAttribute("error", "Email i lozinka ne smiju biti prazni!");
            return "login";
        }

        Optional<AppUser> user = Optional.ofNullable(appUserRepository.findByEmail(email));

        if (user.isPresent() && user.get().getPassword().equals(password)) {
            session.setAttribute("user", user.get());
            System.out.println("Korisnik u sesiji: " + session.getAttribute("user"));


            if (user.get().getUserTypeId() == 1) { // Doktor
                return "redirect:/patientList";
            } else if (user.get().getUserTypeId() == 2) { // Pacijent
                return "redirect:/formPatient";
            } else {
                model.addAttribute("error", "Nepoznata vrsta korisnika.");
                return "login";
            }
        }

        model.addAttribute("error", "Neispravni podaci za prijavu!");
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @PostMapping("/addPatientFromLogin")
    public String registerPatientFromLogin(@RequestParam String ime,
                                           @RequestParam String prezime,
                                           @RequestParam String email,
                                           @RequestParam String telefon,
                                           @RequestParam String adresa,
                                           @RequestParam String grad,
                                           @RequestParam String lozinka) {
        // Kreiranje novog korisnika
        AppUser newUser = new AppUser();
        newUser.setIme(ime);
        newUser.setPrezime(prezime);
        newUser.setEmail(email);
        newUser.setTelefon(telefon);
        newUser.setAdresa(adresa);
        newUser.setGrad(grad);
        newUser.setPassword(lozinka);
        newUser.setUserTypeId(2); // Označiti korisnika kao pacijenta

        // Spremanje korisnika
        appUserRepository.save(newUser);

        // Preusmjeravanje na login
        return "redirect:/login";
    }
    @GetMapping("/addPatientFromLogin")
    public String showRegistrationForm() {
        return "addPatientFromLogin"; // Prikazuje istu HTML stranicu
    }


}
