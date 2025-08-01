package com.dailycodework.dreamshops.repository;

import com.dailycodework.dreamshops.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{
    User findByEmail(String email);
    Boolean existsByEmail(String email);
}
