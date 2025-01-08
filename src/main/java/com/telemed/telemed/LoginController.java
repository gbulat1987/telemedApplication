package com.telemed.telemed;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class LoginController {
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(
            @RequestParam String email,
            @RequestParam String password,
            Model model) {
        // Hardkodirani korisnici
        if (email.equals("pacijent@telemed.hr") && password.equals("pacijent123")) {
            return "redirect:/formPatient"; // Pacijent odlazi na stranicu formPatient
        } else if (email.equals("doktor@telemed.hr") && password.equals("doktor123")) {
            return "redirect:/patientList"; // Doktor odlazi na stranicu patientDetails
        } else {
            model.addAttribute("error", "Neispravni podaci za prijavu!");
            return "login"; // Ponovno učitavanje stranice za prijavu
        }
    }

    @PostMapping("/togglePasswordVisibility")
    public String togglePasswordVisibility(@RequestParam boolean showPassword, Model model) {
        model.addAttribute("showPassword", !showPassword); // Promjena stanja
        return "login";
    }


}
