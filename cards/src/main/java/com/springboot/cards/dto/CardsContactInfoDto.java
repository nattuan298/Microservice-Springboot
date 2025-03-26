package com.springboot.cards.dto;

import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "cards")
@Getter
@Setter
public class CardsContactInfoDto {
  public String message;
  public Map<String, String> contactDetails;
  public List<String> onCallSupport;

}
