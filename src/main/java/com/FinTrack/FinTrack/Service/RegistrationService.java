package com.FinTrack.FinTrack.Service;


import com.FinTrack.FinTrack.CustomExceptionHandling.InconsistentData;
import com.FinTrack.FinTrack.UserDetailsRepo;
import com.FinTrack.FinTrack.models.Bill;
import com.FinTrack.FinTrack.models.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


//registers new user
@Service
public class RegistrationService {
    @Autowired
    UserDetailsRepo userDetailsRepo;

    Boolean checkAlreadyRegistered(UserDetails userDetails) {
        return userDetailsRepo.existsById(userDetails.email);
    }

    public void registerUser(UserDetails userDetails, int recharge, int rent, int electricity, int bill) {
        if (!checkAlreadyRegistered(userDetails)) {
            int sum1 = userDetails.foodBalance + userDetails.miscllBalance + userDetails.tranBalance;
            int sum2 = recharge + rent + electricity + bill;
            if (sum1 + sum2 > userDetails.Balance) {
                throw new InconsistentData("Your data is not consistent");
            }
            Bill bill1 = new Bill(rent, false, "rent");
            Bill bill2 = new Bill(electricity, false, "electricity");
            Bill bill3 = new Bill(bill, false, "bill");
            Bill bill4 = new Bill(recharge, false, "recharge");
            bill1.setUserDetails(userDetails);
            bill2.setUserDetails(userDetails);
            bill3.setUserDetails(userDetails);
            bill4.setUserDetails(userDetails);
            userDetails.BalanceCurr = userDetails.Balance;
            userDetails.foodBalanceCurr = userDetails.foodBalance;
            userDetails.miscllBalanceCurr = userDetails.miscllBalance;
            userDetails.TranBalanceCurr = userDetails.tranBalance;
            userDetails.bills.add(bill1);
            userDetails.bills.add(bill2);
            userDetails.bills.add(bill3);
            userDetails.bills.add(bill4);
            userDetails.setImage(null);
            //temporary solution to encrypt password
            String encodedPassword = new BCryptPasswordEncoder().encode(userDetails.password);
            userDetails.password = encodedPassword;
            userDetailsRepo.save(userDetails);

        }
    }
}
