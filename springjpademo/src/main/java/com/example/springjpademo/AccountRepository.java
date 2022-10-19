package com.example.springjpademo;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
  Page<Account> findByBankId(Long bankId, Pageable pageable);

  Optional<Account> findByIdAndBankId(Long id, Long bankId);
}
