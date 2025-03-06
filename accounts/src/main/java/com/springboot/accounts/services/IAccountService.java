package com.springboot.accounts.services;

import com.springboot.accounts.dto.CustomerDto;

public interface IAccountService {

  /**
   *
   * @param customerDto
   */
  void createAccount(CustomerDto customerDto);

  /**
   *
   * @param mobileNumber
   * @return Get customer by mobile number
   */
  CustomerDto fetchAccount(String mobileNumber);

  /**
   *
   * @param customerDto
   * @return boolean for update account
   */
  boolean updateAccount(CustomerDto customerDto);


  /**
   *
   * @param mobileNumber
   * @return boolean for delete customer
   */
  boolean deleteAccount(String mobileNumber);

}
