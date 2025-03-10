package com.springboot.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
    name = "Customer",
    description = "Schema to hold Customer and Account information"
)
public class CustomerDto {

  @Schema(
      description = "Name of customer", example = "Tyler Nguyen"
  )
  @NotEmpty(message = "Name can not be null or empty")
  @Size(min = 5, max = 30, message = "The length of the customer name should be between 5 and 30")
  private String name;

  @Schema(
      description = "Email of customer", example = "tyler@gmail.com"
  )
  @NotEmpty(message = "Email can not be null or empty")
  @Email(message = "Email should be a valid format")
  private String email;

  @Schema(
      description = "Mobile number of customer", example = "0987402464"
  )
  @Pattern(regexp = "\\d{10}", message = "Mobile should be 10 digits")
  private String mobileNumber;

  @Schema(
      description = "Account details of the customer"
  )
  private AccountsDto accountsDto;

}
