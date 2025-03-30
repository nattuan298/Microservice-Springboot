package com.springboot.accounts.services;

import com.springboot.accounts.dto.CustomerDetailsDto;

public interface ICustomersService {

  /**
   *
   * @param mobileNumber
   * @return
   */
  CustomerDetailsDto fetchCustomerDetails(String mobileNumber);
}
