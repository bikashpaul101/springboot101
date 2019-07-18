package com.example.demo;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class ApiSecurity extends WebSecurityConfigurerAdapter{
	private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    
    public static final String SIGN_UP_URL = "/users/add";
    
    public ApiSecurity(UserDetailsService userDetailsService,BCryptPasswordEncoder bCryptPasswordEncoder) {
    	this.userDetailsService=userDetailsService;
    	this.bCryptPasswordEncoder=bCryptPasswordEncoder;
    }
    
    @Override
    protected void configure(HttpSecurity httpsecurity) throws Exception{
    	httpsecurity.csrf().disable().
        authorizeRequests()
        .antMatchers(HttpMethod.POST, SIGN_UP_URL)
        .permitAll()
        .antMatchers("/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**")
        .permitAll()
        .anyRequest().authenticated().and()        
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    	httpsecurity.headers().frameOptions().disable();
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception{
    	authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }
}
