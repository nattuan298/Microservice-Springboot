package com.springboot.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(
    name = "CustomerDetails",
    description = "Schema to hold Customer, Account, Cards and Loans information"
)
public class CustomerDetailsDto extends CustomerDto {

  @Schema(
      description = "Cards details of the customer"
  )
  private CardsDto cardsDto;

  @Schema(
      description = "Loans details of the customer"
  )
  private LoansDto loansDto;
}
