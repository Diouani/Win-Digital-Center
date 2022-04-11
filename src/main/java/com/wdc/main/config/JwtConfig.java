package com.wdc.main.config;


import com.wdc.main.filter.JwtAuthFilter;
import com.wdc.main.service.CuserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class JwtConfig  extends WebSecurityConfigurerAdapter {



    @Autowired
    private JwtAuthFilter jwtFilter;
 @Autowired
private CuserDetailService userDetailsService;
    //comment on veut manager notre authentification process
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }


    // controller is l'endpoint est permis ou pas
    @Override
    protected void configure(HttpSecurity http) throws Exception {
       http
               .csrf()
               .disable()
               .cors()
               .disable()
               .authorizeRequests()
               .antMatchers("/api/generateToken").permitAll()   //Only allow this end point without authenfication
               .anyRequest().authenticated()  // any other request , authentification should be performed
               .and()
               .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);  //every requestion should be independent from other  , and server does not have to manage session

   http.addFilterBefore(jwtFilter , UsernamePasswordAuthenticationFilter.class);

    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }


    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();

    }
}
