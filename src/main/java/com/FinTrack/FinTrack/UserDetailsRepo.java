package com.FinTrack.FinTrack;

import com.FinTrack.FinTrack.models.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface UserDetailsRepo  extends JpaRepository<UserDetails, String> {
}
