package com.spring.Service;

import com.spring.dao.SubscriberRepo;
import com.spring.model.Subscriber;
import com.spring.model.SubscriberSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private SubscriberRepo subscriberRepo;

    public CustomUserDetailsService(SubscriberRepo subscriberRepo) {
        this.subscriberRepo = subscriberRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<Subscriber> subscribers = subscriberRepo.findByEmail(username);
        if(subscribers.isEmpty()){
            throw new UsernameNotFoundException("Email not found");
        }
        return new SubscriberSecurity(subscribers.get(0));
    }
}
