package com.springboot.accounts.controller;

import static com.springboot.accounts.constants.AccountsConstants.MESSAGE_201;
import static com.springboot.accounts.constants.AccountsConstants.STATUS_201;

import com.springboot.accounts.constants.AccountsConstants;
import com.springboot.accounts.dto.AccountsContactInfoDto;
import com.springboot.accounts.dto.CustomerDto;
import com.springboot.accounts.dto.ErrorResponseDto;
import com.springboot.accounts.dto.ResponseDto;
import com.springboot.accounts.services.IAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
@Validated
@Tag(
    name = "CRUD REST APIs for Accounts:",
    description = "CREATE, UPDATE, FETCH AND DELETE account details"
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
public class AccountsController {

  private IAccountService iAccountService;

  public AccountsController(IAccountService iAccountService) {
    this.iAccountService = iAccountService;
  }

  @Value("${build.version}")
  private String buildVersion;

  @Autowired
  private Environment environment;

  @Autowired
  private AccountsContactInfoDto accountsContactInfoDto;

  @Operation(
      summary = "Create account API",
      description = "REST API to create new account"
  )
  @ApiResponse(
      responseCode = "201",
      description = "HTTP status CREATED"
  )
  @PostMapping("/create")
  public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto) {
    iAccountService.createAccount(customerDto);
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(new ResponseDto(STATUS_201, MESSAGE_201));
  }

  @Operation(
      summary = "Get account API",
      description = "REST API to get new account"
  )
  @GetMapping("/fetch")
  public ResponseEntity<CustomerDto> fetchAccountDetail(
      @RequestParam
      @Pattern(regexp = "\\d{10}", message = "Mobile should be 10 digits")
      String mobileNumber
  ) {
    CustomerDto customerDto = iAccountService.fetchAccount(mobileNumber);
    return ResponseEntity.status(HttpStatus.OK).body(customerDto);
  }

  @Operation(
      summary = "Update account API",
      description = "REST API to get new account"
  )
  @PutMapping("/update")
  public ResponseEntity<ResponseDto> updateAccountDetail(
      @Valid @RequestBody CustomerDto customerDto) {
    boolean isUpdated = iAccountService.updateAccount(customerDto);

    if (isUpdated) {
      return ResponseEntity
          .status(HttpStatus.OK)
          .body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
    } else {
      return ResponseEntity
          .status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(new ResponseDto(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_UPDATE));
    }
  }

  @Operation(
      summary = "Delete account API",
      description = "REST API to get new account"
  )
  @DeleteMapping("/delete")
  public ResponseEntity<ResponseDto> deleteAccount(
      @RequestParam @Pattern(regexp = "\\d{10}", message = "Mobile should be 10 digits") String mobileNumber) {
    boolean isDeleted = iAccountService.deleteAccount(mobileNumber);
    if (isDeleted) {
      return ResponseEntity
          .status(HttpStatus.OK)
          .body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
    } else {
      return ResponseEntity
          .status(HttpStatus.EXPECTATION_FAILED)
          .body(
              new ResponseDto(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_DELETE));
    }
  }

  @Operation(
      summary = "Get build version API",
      description = "REST API to get build version"
  )
  @GetMapping("/build-info")
  public ResponseEntity<String> getBuildInfo() {
    return ResponseEntity.status(HttpStatus.OK).body(buildVersion);
  }

  @Operation(
      summary = "Get java version API",
      description = "REST API to get java version"
  )
  @GetMapping("/java-version")
  public ResponseEntity<String> getJavaVersion() {
    return ResponseEntity.status(HttpStatus.OK).body(environment.getProperty("JAVA_HOME"));
  }

  @Operation(
      summary = "Get contact API",
      description = "REST API to get contact info"
  )
  @GetMapping("/contact-info")
  public ResponseEntity<AccountsContactInfoDto> getContactInfo() {
    return ResponseEntity.status(HttpStatus.OK).body(accountsContactInfoDto);
  }
}
