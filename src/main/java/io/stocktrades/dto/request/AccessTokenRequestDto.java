package io.stocktrades.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@ToString
public class AccessTokenRequestDto {

  @JsonProperty("checksum")
  private String checkSum;

  @JsonProperty("api_key")
  private String apiKey;

  @JsonProperty("request_token")
  private String requestToken;
}
