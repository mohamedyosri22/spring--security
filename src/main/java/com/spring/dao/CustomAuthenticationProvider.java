package com.spring.dao;

import com.spring.model.Authority;
import com.spring.model.Subscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private SubscriberRepo subscriberRepo;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public CustomAuthenticationProvider(SubscriberRepo subscriberRepo,PasswordEncoder passwordEncoder) {
        this.subscriberRepo = subscriberRepo;
        this.passwordEncoder = passwordEncoder;
    }



    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userName = authentication.getName();
        String password = authentication.getCredentials().toString();

        List<Subscriber> subscribers = subscriberRepo.findByEmail(userName);
        if(subscribers.isEmpty()){
            throw new BadCredentialsException("User not found !!");
        }
        else{
            if(passwordEncoder.matches(password,subscribers.get(0).getPassword())){
                //List<GrantedAuthority> authorities = new ArrayList<>();
                //authorities.add(new SimpleGrantedAuthority(subscribers.get(0).getRole()));
                return new UsernamePasswordAuthenticationToken(userName,password,getAuthority(subscribers.get(0).getAuthorities()));
            }
            else{
                throw new BadCredentialsException("Wrong password");
            }
        }
    }

    private List<SimpleGrantedAuthority> getAuthority(List<Authority> authorityList){
        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
        for(Authority auth : authorityList){
            simpleGrantedAuthorities.add(new SimpleGrantedAuthority(auth.getName()));
        }
        return simpleGrantedAuthorities;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
