package com.oluwaseun.airtimepayment.repository;

import com.oluwaseun.airtimepayment.domain.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<ApplicationUser, Long> {
    ApplicationUser findByUsername(String username);
    boolean existsByUsername(String username);
}
