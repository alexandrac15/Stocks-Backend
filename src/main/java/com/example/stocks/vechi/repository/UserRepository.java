package com.example.stocks.vechi.repository;

import com.example.stocks.vechi.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
       User findByEmail(String email);
}
