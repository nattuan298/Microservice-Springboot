package com.springboot.accounts.services.impl;

import com.springboot.accounts.constants.AccountsConstants;
import com.springboot.accounts.dto.AccountsDto;
import com.springboot.accounts.dto.CustomerDto;
import com.springboot.accounts.entity.Accounts;
import com.springboot.accounts.entity.Customer;
import com.springboot.accounts.exception.CustomerAlreadyExistsException;
import com.springboot.accounts.exception.ResourceNotFoundException;
import com.springboot.accounts.mapper.AccountsMapper;
import com.springboot.accounts.mapper.CustomerMapper;
import com.springboot.accounts.repository.AccountsRepository;
import com.springboot.accounts.repository.CustomerRepository;
import com.springboot.accounts.services.IAccountService;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountService {

  private CustomerRepository customerRepository;
  private AccountsRepository accountsRepository;

  /**
   *
   * @param customerDto
   */
  @Override
  public void createAccount(CustomerDto customerDto) {
    Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());

    Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customerDto.getMobileNumber());
    if (optionalCustomer.isPresent()) {
      throw new CustomerAlreadyExistsException("Customer already registered with given mobile number: "
          + customerDto.getMobileNumber());
    }
    customer.setCreatedAt(LocalDateTime.now());
    customer.setCreatedBy("Admin");
    Customer savedCustomer = customerRepository.save(customer);
    accountsRepository.save(createNewAccount(savedCustomer));
  }

  @Override
  public CustomerDto fetchAccount(String mobileNumber) {
    Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
        () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
    );

    Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
        () -> new ResourceNotFoundException("Account", "customerId", mobileNumber)
    );

    CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
    customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));
    return customerDto;
  }

  /**
   * @param customerDto
   * @return boolean for update account
   */
  @Override
  public boolean updateAccount(CustomerDto customerDto) {
    boolean isUpdated = false;

    AccountsDto accountsDto = customerDto.getAccountsDto();
    if (accountsDto != null) {
      Accounts accounts = accountsRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
          () -> new ResourceNotFoundException("Account", "AccountNumber", accountsDto.getAccountNumber().toString())
      );
      AccountsMapper.mapToAccounts(accountsDto, accounts);
      accounts = accountsRepository.save(accounts);

      Long customerId = accounts.getCustomerId();
      Customer customer = customerRepository.findById(customerId).orElseThrow(
          () -> new ResourceNotFoundException("Customer", "CustomerId", customerId.toString())
      );
      CustomerMapper.mapToCustomer(customerDto, customer);
      customerRepository.save(customer);

      isUpdated = true;
    }
    return isUpdated;
  }

  /**
   * @param mobileNumber
   * @return boolean for delete customer
   */
  @Override
  public boolean deleteAccount(String mobileNumber) {
    Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
        () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
    );
    accountsRepository.deleteByCustomerId(customer.getCustomerId());
    customerRepository.deleteById(customer.getCustomerId());
    return true;
  }

  /**
   *
   * @param customer
   * @return new account detail
   */
  private Accounts createNewAccount(Customer customer) {
    Accounts newAccount = new Accounts();
    newAccount.setCustomerId(customer.getCustomerId());
    long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

    newAccount.setAccountNumber(randomAccNumber);
    newAccount.setAccountType(AccountsConstants.SAVINGS);
    newAccount.setBranchAddress(AccountsConstants.ADDRESS);
    newAccount.setCreatedAt(LocalDateTime.now());
    newAccount.setCreatedBy("Admin");
    return newAccount;
  }
}
