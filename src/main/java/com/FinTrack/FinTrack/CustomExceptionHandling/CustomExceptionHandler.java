package com.FinTrack.FinTrack.CustomExceptionHandling;


import jakarta.validation.ValidationException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(InconsistentData.class)
    String incosistentData(InconsistentData inconsistentData, Model model, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("error", true);
        model.addAttribute("errorMessage", true);
        return "redirect:/register/form";
    }

    @ExceptionHandler(InsufficientBalance.class)
    String inSufficientBalance(InsufficientBalance insufficientBalance, Model model, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("error", true);
        model.addAttribute("errorMessage", true);
        return "redirect:/transactions";
    }
}