package io.stocktrades.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@AllArgsConstructor
public class RestConfig {

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }
}
