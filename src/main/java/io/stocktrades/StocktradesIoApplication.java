package io.stocktrades;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication(scanBasePackages = "io.stocktrades")
public class StocktradesIoApplication {

  public static final String APP_EXTERNAL_PROPERTIES_CONFIG =
      "spring.config.location=classpath:/" + ",classpath:application.properties";

  public static void main(String[] args) {
    new SpringApplicationBuilder(StocktradesIoApplication.class)
        .properties(APP_EXTERNAL_PROPERTIES_CONFIG)
        .run(args);
  }
}
