package com.CloudClub.jpaStudy.service;

import com.CloudClub.jpaStudy.repository.AccountRepository;

public class TransferService {
  private final AccountRepository accountRepository;

  public TransferService(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  public void transfer(long l, long l1, int i) {
  }
}
