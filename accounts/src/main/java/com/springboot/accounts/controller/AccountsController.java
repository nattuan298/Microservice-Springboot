package com.springboot.accounts.controller;

import static com.springboot.accounts.constants.AccountsConstants.MESSAGE_201;
import static com.springboot.accounts.constants.AccountsConstants.STATUS_201;

import com.springboot.accounts.constants.AccountsConstants;
import com.springboot.accounts.dto.CustomerDto;
import com.springboot.accounts.dto.ResponseDto;
import com.springboot.accounts.services.IAccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
public class AccountsController {

  private IAccountService iAccountService;

  @PostMapping("/create")
  public ResponseEntity<ResponseDto> createAccount(@RequestBody CustomerDto customerDto) {
    iAccountService.createAccount(customerDto);
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(new ResponseDto(STATUS_201, MESSAGE_201));
  }

  @GetMapping("/fetch")
  public ResponseEntity<CustomerDto> fetchAccountDetail(@RequestParam String mobileNumber) {
    CustomerDto customerDto = iAccountService.fetchAccount(mobileNumber);
    return ResponseEntity.status(HttpStatus.OK).body(customerDto);
  }

  @PutMapping("/update")
  public ResponseEntity<ResponseDto> updateAccountDetail(@RequestBody CustomerDto customerDto) {
    boolean isUpdated = iAccountService.updateAccount(customerDto);

    if (isUpdated) {
      return ResponseEntity
          .status(HttpStatus.OK)
          .body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
    } else {
      return ResponseEntity
          .status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(new ResponseDto(AccountsConstants.STATUS_500, AccountsConstants.MESSAGE_500));
    }
  }

  @DeleteMapping("/delete")
  public ResponseEntity<ResponseDto> deleteAccount(@RequestParam String mobileNumber) {
    boolean isDeleted = iAccountService.deleteAccount(mobileNumber);
    if(isDeleted) {
      return ResponseEntity
          .status(HttpStatus.OK)
          .body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
    }else{
      return ResponseEntity
          .status(HttpStatus.EXPECTATION_FAILED)
          .body(new ResponseDto(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_DELETE));
    }
  }
}
