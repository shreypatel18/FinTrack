package com.FinTrack.FinTrack.Controller;

import com.FinTrack.FinTrack.Service.TimeService;
import com.FinTrack.FinTrack.Service.UserDetailsService;
import com.FinTrack.FinTrack.UserDetailsRepo;
import com.FinTrack.FinTrack.models.Bill;
import com.FinTrack.FinTrack.models.Image;
import com.FinTrack.FinTrack.models.Transaction;
import com.FinTrack.FinTrack.models.UserDetails;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.validation.Valid;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;


//Helps to load the dashBoard and transaction page
@Controller
@SessionAttributes("userDetails")
public class UserDetailsController {

    //helps to display dashboard
    @Autowired
    UserDetailsService userDetailsService;

    //used to calculate the time
    @Autowired
    TimeService timeService;


    //Controls DashBoard Page
    @GetMapping("/dashBoard")
    String DashBoard(Model model, @SessionAttribute("username") String username, HttpSession session) {

        UserDetails userDetails = userDetailsService.getUserDetails(username);
        session.setAttribute("userDetails", userDetails);

        //setting the session attribute so transaction page can also use users properties
        model.addAttribute("userDetails", userDetails);

        //general model for DashBoard page
        model.addAttribute("user", userDetails);

        //models for time circle
        int height1 = timeService.getTimeToEnd();
        int height2 = 100 - height1;
        String height2s = String.valueOf(height1) + "%";
        String height1s = String.valueOf(height2) + "%";
        int daysToMonthEnd = timeService.getDaysToMonthEnd();
        model.addAttribute("Height1", height1s);
        model.addAttribute("Height2", height2s);
        model.addAttribute("daysToMonthEnd", daysToMonthEnd);


        //to display image
        boolean imgAvailable = false;
        String image = userDetailsService.getImage(userDetails);
        if (image != null) {
            imgAvailable = true;
        }
        model.addAttribute("imageAdded", imgAvailable);
        model.addAttribute("base64", image);


        //to set pie charts
        int foodDeg1 = userDetailsService.getDegFood(userDetails);
        String foods1 = String.valueOf(foodDeg1) + "deg";
        model.addAttribute("fd1", foods1);

        int TranDeg1 = userDetailsService.getDegTran(userDetails);
        String Trans1 = String.valueOf(TranDeg1) + "deg";
        model.addAttribute("td1", Trans1);

        int MisDeg1 = userDetailsService.getDegMis(userDetails);
        String Mis1 = String.valueOf(MisDeg1) + "deg";
        model.addAttribute("md1", Mis1);


        //to set width of spent bar
        int sbw = userDetailsService.getWidthForSpentBar(userDetails);
        String sbw1 = String.valueOf(sbw) + "%";
        String sbw2 = String.valueOf(100 - sbw) + "%";
        model.addAttribute("sbw1", sbw1);
        model.addAttribute("sbw2", sbw2);


        //to set essentials or bills
        List<Bill> bills = userDetailsService.getBills(userDetails);
        model.addAttribute("bills", bills);

        int sum = 0;
        for (Bill x : bills) {
            if (!x.status)
                sum = sum + x.amount;
        }
        model.addAttribute("sum", sum);
        return "DashBoard";
    }


    //end points to save and retrieve user image
    @GetMapping("/uploadImage")
    String uploadImage() {
        return "ImageUpload";
    }


    //called internally by upload form
    @PostMapping("/upload")
    public String FileUpload(@ModelAttribute Image imageEntity, @RequestParam("file") MultipartFile file, @SessionAttribute("userDetails") UserDetails userDetails) throws IOException {
        imageEntity.setImg(file.getBytes());
        userDetailsService.saveImage(imageEntity, userDetails);
        return "redirect:/dashBoard";
    }


    //end points to display search and add transactions
    @GetMapping("/transactions")
    String transaction(@SessionAttribute("userDetails") UserDetails userDetails, Model model) {
        List<Bill> bills = userDetails.bills;
        for (Bill bill : bills) {
            model.addAttribute(bill.category.toString(), bill.status);
        }
        model.addAttribute("transaction", new Transaction());
        List<Transaction> transactions = userDetailsService.getAllTransactions(userDetails);
        model.addAttribute("listTran", transactions);
        return "Transactions";
    }


    //manages add bar
    @PostMapping("/addTran")
    public String addTransaction(RedirectAttributes errors, @SessionAttribute("userDetails") UserDetails userDetails, @Valid @ModelAttribute Transaction transaction, BindingResult result, HttpSession session) throws ServletException, IOException {
        if (result.hasErrors()) {
            errors.addAttribute("error", true);
            return "redirect:/transactions";
        }
        LocalDate currDate = LocalDate.now();
        transaction.setDate1(currDate);
        userDetailsService.addTransaction(transaction, userDetails);
        session.setAttribute("userDetails", userDetails);
        return "redirect:/transactions";
    }

    @PostMapping("/search")
    public String searchTran(@SessionAttribute("userDetails") UserDetails userDetails, @RequestParam String search, Model model) {
        System.out.println(search);
        List<Transaction> transactionList = userDetailsService.searchTran(search, userDetails);
        model.addAttribute("transaction", new Transaction());
        List<Transaction> transactions = userDetailsService.getAllTransactions(userDetails);
        model.addAttribute("listTran", transactionList);
        return "Transactions";
    }


    @GetMapping("/Login")
    String login() {
        return "Login";
    }


    @PostMapping("/billUpdates")
    String billUpdates(@SessionAttribute("userDetails") UserDetails userDetails, HttpSession session, @RequestParam(name = "electricity", defaultValue = "false") boolean electricity, @RequestParam(name = "bill", defaultValue = "false") boolean bill, @RequestParam(name = "rent", defaultValue = "false") boolean rent, @RequestParam(name = "recharge", defaultValue = "false") boolean recharge) {
        userDetailsService.updateBillStatus(userDetails, rent, recharge, bill, electricity);
        session.setAttribute("userDetails", userDetails);
        return "redirect:/transactions";
    }
}


