package com.wdc.main.controller;


import com.wdc.main.entity.JwtRequest;
import com.wdc.main.entity.JwtResponse;
import com.wdc.main.service.CuserDetailService;
import com.wdc.main.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class JwtController {

@Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CuserDetailService customUserDetailService;

    @PostMapping("/generateToken")
    public ResponseEntity<JwtResponse> generateToken(@RequestBody JwtRequest jwtRequest){


        //authentication

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                jwtRequest.getUsername() ,
                jwtRequest.getPassword()
        ));

   UserDetails userDetails = customUserDetailService.loadUserByUsername(jwtRequest.getUsername());
        System.out.println(userDetails);
      String JwtToken =  jwtUtil.generateToken(userDetails) ;
        System.out.println(JwtToken);
        JwtResponse jwtResponse = new JwtResponse(JwtToken);
        return new ResponseEntity<JwtResponse>(jwtResponse,HttpStatus.OK);
    }
}
