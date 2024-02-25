package com.FinTrack.FinTrack.Service;

import com.FinTrack.FinTrack.CustomExceptionHandling.InsufficientBalance;
import com.FinTrack.FinTrack.UserDetailsRepo;
import com.FinTrack.FinTrack.models.Bill;
import com.FinTrack.FinTrack.models.Image;
import com.FinTrack.FinTrack.models.Transaction;
import com.FinTrack.FinTrack.models.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


//resends all the details of user
@Service
public class UserDetailsService {

    @Autowired
    UserDetailsRepo userDetailsRepo;
    //SERVICES FOR PIE

    public UserDetails getUserDetails(String id) {
        Optional<UserDetails> temp = userDetailsRepo.findById(id);
        if (temp != null) {
            return temp.get();
        } else {
            throw new NoSuchElementException();
        }
    }


    //services to save and retrieb=ve user image

    //to retrieve image

    public String getImage(UserDetails userDetails) {
        Image image = userDetails.getImage();
        if (image != null) {
            String base64 = Base64.getEncoder().encodeToString(image.getImg());
            return base64;
        }
        return "null";
    }

    // to save image currently not using jwt auth so manually asking for username inorder to locate in database
    public void saveImage(Image image, UserDetails userDetails) {

        userDetails.setImage(image);
        userDetailsRepo.save(userDetails);
    }


    //service to fetch degrees for pie charts

    public int getDegFood(UserDetails userDetails) {

        return calcDegree(userDetails.foodBalance, userDetails.foodBalanceCurr);
    }

    public int getDegTran(UserDetails userDetails) {
        return calcDegree(userDetails.tranBalance, userDetails.TranBalanceCurr);
    }

    public int getDegMis(UserDetails userDetails) {
        return calcDegree(userDetails.miscllBalance, userDetails.miscllBalanceCurr);
    }

    int calcDegree(int x, int y) {
        double ratio = (double) y / x;
        double ans = ratio * 360;
        return 360 - (int) ans;
    }


    //methods to get width of spent bar
    public int getWidthForSpentBar(UserDetails userDetails) {

        int balanceLeft = userDetails.BalanceCurr;
        int totalBalance = userDetails.Balance;
        double bw = (double) balanceLeft / totalBalance;
        double ans = (double) bw * 100;
        return (int) ans;
    }

    //to retrieve list of Bills

    public List<Bill> getBills(UserDetails userDetails) {
        return userDetails.getBills();
    }

    //to retrieve all transactions
    public List<Transaction> getAllTransactions(UserDetails userDetails) {
        return userDetails.transactions;
    }


    //to enable searching facility for transactions
    public List<Transaction> searchTran(String searchText, UserDetails userDetails) {

        List<Transaction> list = userDetails.transactions;
        return list.stream()
                .filter(transaction ->
                        transaction.getCategory().contains(searchText) ||
                                String.valueOf(transaction.getTranId()).contains(searchText) ||
                                transaction.getName().contains(searchText) ||
                                String.valueOf(transaction.getAmount()).contains(searchText) ||
                                transaction.getDescription().contains(searchText)
                ).toList();
    }

    // to add the transactions
    public void addTransaction(Transaction transaction, UserDetails userDetails) {
        int x = 0;
        switch (transaction.getCategory()) {
            case "transportation":
                x = userDetails.TranBalanceCurr - transaction.getAmount();
                if (x < 0) {
                    throw new InsufficientBalance("transportation balance is less than entered amount");
                } else userDetails.setTranBalanceCurr(x);
                break;

            case "food":
                x = userDetails.foodBalanceCurr - transaction.getAmount();
                if (x < 0) {
                    throw new InsufficientBalance("food balance is less than entered amount");
                } else userDetails.setFoodBalanceCurr(x);
                break;

            case "others":
                x = userDetails.miscllBalanceCurr - transaction.getAmount();
                if (x < 0) {
                    throw new InsufficientBalance("Miscllaneous balance is less than entered amount");
                } else userDetails.setMiscllBalance(x);
                break;
        }

        userDetails.BalanceCurr = userDetails.Balance - transaction.getAmount();
        userDetails.transactions.add(transaction);
        transaction.setUserDetails(userDetails);
        userDetailsRepo.save(userDetails);

    }

    //update bill status

    public void updateBillStatus(UserDetails userDetails, Boolean rent, Boolean recharge, Boolean bill, Boolean electricity) {
        for (Bill bills : userDetails.getBills()) {
            switch (bills.category) {
                case "rent":
                    bills.status = rent;
                    break;
                case "recharge":
                    bills.status = recharge;
                    break;
                case "bill":
                    bills.status = bill;
                    break;
                case "electricity":
                    bills.status = electricity;
                    break;
            }
        }
        userDetailsRepo.save(userDetails);
        return;
    }

}

