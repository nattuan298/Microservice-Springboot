package com.springboot.accounts.services;

import com.springboot.accounts.dto.CustomerDto;

public interface IAccountService {

  /**
   *
   * @param customerDto
   */
  void createAccount(CustomerDto customerDto);

  CustomerDto fetchAccount(String mobileNumber);

}
