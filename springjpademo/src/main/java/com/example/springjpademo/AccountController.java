package com.example.springjpademo;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {
  @Autowired private AccountRepository accountRepository;
  @Autowired private BankRepository bankRepository;

  @GetMapping("/banks/{bankId}/accounts")
  public Page<Account> getAllCommentsByBankId(
      @PathVariable(value = "bankId") Long bankId, Pageable pageable) {
    return accountRepository.findByBankId(bankId, pageable);
  }

  @PostMapping("/banks/{bankId}/accounts")
  public Account createAccount(
      @PathVariable(value = "bankId") Long bankId, @Valid @RequestBody Account account) {
    return bankRepository
        .findById(bankId)
        .map(
            bank -> {
              account.setBank(bank);
              return accountRepository.save(account);
            })
        .orElseThrow(() -> new ResourceNotFoundException("BankId " + bankId + " not found"));
  }

  @PutMapping("/banks/{bankId}/accounts/{accountId}")
  public Account updateAccount(
      @PathVariable(value = "bankId") Long bankId,
      @PathVariable(value = "accountId") Long accountId,
      @Valid @RequestBody Account accountRequest) {
    if (!bankRepository.existsById(bankId)) {
      throw new ResourceNotFoundException("BankId " + bankId + " not found");
    }
    return accountRepository
        .findById(accountId)
        .map(
            account -> {
              account.setValue(accountRequest.getValue());
              return accountRepository.save(account);
            })
        .orElseThrow(() -> new ResourceNotFoundException("AccountId " + accountId + "not found"));
  }

  @DeleteMapping("/banks/{bankId}/accounts/{accountId}")
  public ResponseEntity<?> deleteAccount(
      @PathVariable(value = "bankId") Long bankId,
      @PathVariable(value = "accountId") Long accountId) {
    return accountRepository
        .findByIdAndBankId(accountId, bankId)
        .map(
            account -> {
              accountRepository.delete(account);
              return ResponseEntity.ok().build();
            })
        .orElseThrow(
            () ->
                new ResourceNotFoundException(
                    "Account not found with id " + accountId + " and bankId " + bankId));
  }
}
