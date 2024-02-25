package com.FinTrack.FinTrack;


import com.FinTrack.FinTrack.models.Bill;
import com.FinTrack.FinTrack.models.Transaction;
import com.FinTrack.FinTrack.models.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FinTrackApplication{

	public static void main(String[] args) {
		SpringApplication.run(FinTrackApplication.class, args);
	}


}
