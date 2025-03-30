package com.springboot.accounts.controller;

import com.springboot.accounts.dto.CustomerDetailsDto;
import com.springboot.accounts.dto.ErrorResponseDto;
import com.springboot.accounts.services.ICustomersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
@Tag(
    name = "CRUD REST APIs for Customer:",
    description = "FETCH customer details"
)
@ApiResponse(
    responseCode = "200",
    description = "HTTP status OK"
)
@ApiResponse(
    responseCode = "417",
    description = "Expectation Failed"
)
@ApiResponse(
    responseCode = "500",
    description = "HTTP status Internal Server Error",
    content = @Content(
        schema = @Schema(implementation = ErrorResponseDto.class)
    )
)
public class CustomerController {

  private final ICustomersService iCustomersService;

  public CustomerController(ICustomersService iCustomersService) {
    this.iCustomersService = iCustomersService;
  }

  @Operation(
      summary = "Get customer details API",
      description = "REST API to get customer details"
  )
  @GetMapping("/fetchCustomerDetails")
  public ResponseEntity<CustomerDetailsDto> fetchCustomerDetails(
      @RequestParam
      @Pattern(regexp = "\\d{10}", message = "Mobile should be 10 digits")
      String mobileNumber) {
    CustomerDetailsDto customerDetailsDto = iCustomersService.fetchCustomerDetails(mobileNumber);
    return ResponseEntity.status(HttpStatus.OK).body(customerDetailsDto);
  }
}
