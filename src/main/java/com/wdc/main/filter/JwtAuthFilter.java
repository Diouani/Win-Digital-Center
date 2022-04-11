package com.wdc.main.filter;


import com.wdc.main.service.CuserDetailService;
import com.wdc.main.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component

public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private CuserDetailService cuserDetailService;
    @Autowired
    private JwtUtil jwtUtil;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String bearerToken = request.getHeader("Authorization");
        System.out.println(bearerToken);
        String username = null;
        String token = null;

        if(bearerToken != null && bearerToken.startsWith("Bearer")){
           
            token = bearerToken.substring(7);

            try{

                username = jwtUtil.extractUsername(token);

               UserDetails userDetails = cuserDetailService.loadUserByUsername(username);

               //security check

                if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){

                    UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());

                    upat.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(upat);
                    System.out.println(upat);

                }else
                {
                    System.out.println("invalid  Token ");
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            System.out.println("invalid Bearer Token Format");
        }


        //forward the filter request to the requested endpoint
        filterChain.doFilter(request,response);

    }
}
