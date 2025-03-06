package com.springboot.accounts.repository;

import com.springboot.accounts.entity.Accounts;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountsRepository extends JpaRepository<Accounts, Long> {

  Optional<Accounts> findByCustomerId(Long customerId);
}
