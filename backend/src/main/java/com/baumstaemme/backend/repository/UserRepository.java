package com.baumstaemme.backend.repository;

import com.baumstaemme.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
