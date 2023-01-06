package com.spring.dao;

import com.spring.model.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriberRepo extends JpaRepository<Subscriber,Long> {
    public List<Subscriber> findByEmail(String email);
}
