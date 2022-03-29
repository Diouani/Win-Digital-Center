package com.wdc.main.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class CuserDetailService implements UserDetailsService {


   //check if the user exist

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if(username.equals("Diouani")){  // Database CAll
            return new User("Diouani" , "aspirine" , new ArrayList<>());
        }else{
            throw  new UsernameNotFoundException("User does not exist !");
        }

    }
}
