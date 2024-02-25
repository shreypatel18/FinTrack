package com.FinTrack.FinTrack.Service;


import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;


//calculates curr time and days remaining to month end
@Service
public class TimeService {

    //get heights for dashboard month end


    public int getTimeToEnd() {
        LocalDate currentDate = LocalDate.now();

        // Get the last day of the month
        LocalDate lastDayOfMonth = currentDate.withDayOfMonth(currentDate.lengthOfMonth());

        // Calculate the remaining days to month end
        int remainingDays = (int) ChronoUnit.DAYS.between(currentDate, lastDayOfMonth);

        // Get the total days in the month
        int totalDaysInMonth = currentDate.lengthOfMonth();
        int daysSpent = totalDaysInMonth - (int) remainingDays;
        double height1 = (double) remainingDays / totalDaysInMonth;
        double height2 = daysSpent / totalDaysInMonth;
        double ans = height1 * 100;
        return (int) ans;
    }


    public int getDaysToMonthEnd() {
        LocalDate currentDate = LocalDate.now();

        // Get the last day of the month
        LocalDate lastDayOfMonth = currentDate.withDayOfMonth(currentDate.lengthOfMonth());
        int remainingDays = (int) ChronoUnit.DAYS.between(currentDate, lastDayOfMonth);
        return remainingDays;
    }


}
