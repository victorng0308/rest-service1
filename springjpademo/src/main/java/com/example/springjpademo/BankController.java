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
public class BankController {
  @Autowired private BankRepository bankRepository;

  @GetMapping("/banks")
  public Page<Bank> getAllPosts(Pageable pageable) {
    return bankRepository.findAll(pageable);
  }

  @PostMapping("/banks")
  public Bank createBank(@Valid @RequestBody Bank bank) {
    return bankRepository.save(bank);
  }

  @PutMapping("/banks/{bankId}")
  public Bank updateBank(@PathVariable Long bankId, @Valid @RequestBody Bank bankRequest) {
    return bankRepository
        .findById(bankId)
        .map(
            bank -> {
              bank.setName(bankRequest.getName());
              bank.setBranchCode(bankRequest.getBranchCode());
              return bankRepository.save(bank);
            })
        .orElseThrow(() -> new ResourceNotFoundException("BankId " + bankId + " not found"));
  }

  @DeleteMapping("/banks/{bankId}")
  public ResponseEntity<?> deleteBank(@PathVariable Long bankId) {
    return bankRepository
        .findById(bankId)
        .map(
            bank -> {
              bankRepository.delete(bank);
              return ResponseEntity.ok().build();
            })
        .orElseThrow(() -> new ResourceNotFoundException("BankId " + bankId + " not found"));
  }
}
