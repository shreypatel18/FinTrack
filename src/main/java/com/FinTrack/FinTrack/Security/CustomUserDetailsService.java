package com.FinTrack.FinTrack.Security;

import com.FinTrack.FinTrack.Service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

//fetches user from database to perform authentication
@Service
public class CustomUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    @Autowired
    UserDetailsService userDetailsService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //userDetails loading from database
        com.FinTrack.FinTrack.models.UserDetails userDetails = userDetailsService.getUserDetails(username);
        //internal spring securitys user

        return new org.springframework.security.core.userdetails.User(
                userDetails.getEmail(),
                userDetails.getPassword(),
                true,
                true,
                true,
                true,
                getAuthorities("ROLE_USER")
        );


    }

    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }
}
