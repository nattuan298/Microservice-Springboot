package com.springboot.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(
    name = "Accounts",
    description = "Schema to hold Account information"
)
public class AccountsDto {

  @Schema(
      description = "Mobile number of customer", example = "1234567890"
  )
  @NotEmpty(message = "AccountNumber can not be null or empty")
  @Pattern(regexp = "\\d{10}")
  private Long accountNumber;

  @Schema(
      description = "Account type of customer", example = "DRAFT"
  )
  @NotEmpty(message = "AccountType can not be null or empty")
  private String accountType;

  @Schema(
      description = "Branch address of customer", example = "Hanoi - Vietnam"
  )
  @NotEmpty(message = "BranchAddress can not be null or empty")
  private String branchAddress;

}
