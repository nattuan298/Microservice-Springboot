package com.springboot.accounts.repository;

import com.springboot.accounts.entity.Accounts;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

public interface AccountsRepository extends JpaRepository<Accounts, Long> {

  Optional<Accounts> findByCustomerId(Long customerId);

  @Transactional
  @Modifying
  void deleteByCustomerId(Long customerId);
}
