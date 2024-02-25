package com.FinTrack.FinTrack.Controller;

import com.FinTrack.FinTrack.Service.RegistrationService;
import com.FinTrack.FinTrack.models.UserDetails;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import jakarta.validation.constraints.NotNull;


@Controller
@RequestMapping("/register")
public class RegistrationController {

    //to handle registration


    //helps in registering user
    @Autowired
    RegistrationService registrationService;


    //returns the registration form
    @GetMapping("/form")
    public String register(Model model) {
        model.addAttribute("form", new UserDetails());
        return "Registration";
    }


    //internally called by registration form
    @PostMapping("/postRegister")
    public String postRegister(Model model, @Valid @ModelAttribute UserDetails form, BindingResult result, @RequestParam("lightBill") @Positive @NotNull int lightbill, @RequestParam("phoneBill") @Positive @NotNull int phoneBill, @RequestParam("rent") @Positive @NotNull int rent, @RequestParam("monthlyBills") @Positive @NotNull int monthlyBills) {
        boolean error = result.hasErrors();
        if (error) {
            model.addAttribute("form", form);
            model.addAttribute("error", true);
            return "Registration";
        }
        registrationService.registerUser(form, phoneBill, rent, lightbill, monthlyBills);
        return "Login";
    }
}





