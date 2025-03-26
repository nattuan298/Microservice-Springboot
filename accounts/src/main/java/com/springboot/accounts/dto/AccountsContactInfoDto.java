package com.springboot.accounts.dto;

import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "accounts")
@Setter
@Getter
public class AccountsContactInfoDto {
  public String message;
  public Map<String, String> contactDetails;
  public List<String> onCallSupport;

}
