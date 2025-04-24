package com.springboot.accounts.services.impl;

import com.springboot.accounts.dto.AccountsDto;
import com.springboot.accounts.dto.CardsDto;
import com.springboot.accounts.dto.CustomerDetailsDto;
import com.springboot.accounts.dto.LoansDto;
import com.springboot.accounts.entity.Accounts;
import com.springboot.accounts.entity.Customer;
import com.springboot.accounts.exception.ResourceNotFoundException;
import com.springboot.accounts.mapper.AccountsMapper;
import com.springboot.accounts.mapper.CustomerMapper;
import com.springboot.accounts.repository.AccountsRepository;
import com.springboot.accounts.repository.CustomerRepository;
import com.springboot.accounts.services.ICustomersService;
import com.springboot.accounts.services.client.CardsFeignClient;
import com.springboot.accounts.services.client.LoansFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomersServiceImpl implements ICustomersService {

  private AccountsRepository accountsRepository;
  private CustomerRepository customerRepository;
  private CardsFeignClient cardsFeignClient;
  private LoansFeignClient loansFeignClient;

  /**
   * @param mobileNumber
   * @return
   */
  @Override
  public CustomerDetailsDto fetchCustomerDetails(String mobileNumber, String correlationId) {
    Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
        () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
    );

    Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
        () -> new ResourceNotFoundException("Account", "customerId", mobileNumber)
    );

    CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer, new CustomerDetailsDto());
    customerDetailsDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));

    ResponseEntity<LoansDto> loansDtoResponseEntity = loansFeignClient.fetchLoanDetails(correlationId, mobileNumber);
    customerDetailsDto.setLoansDto(loansDtoResponseEntity.getBody());

    ResponseEntity<CardsDto> cardsDtoResponseEntity = cardsFeignClient.fetchCardDetails(correlationId, mobileNumber);
    customerDetailsDto.setCardsDto(cardsDtoResponseEntity.getBody());

    return customerDetailsDto;
  }
}
