package io.stocktrades.service;

import io.stocktrades.Constants;
import io.stocktrades.dto.request.AccessTokenRequestDto;
import io.stocktrades.util.ChecksumGenerator;
import io.stocktrades.util.HttpClient;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class StockService {

  private final HttpClient httpClient;

  private final RestTemplate restTemplate;

  @Value("zerodha.login.url")
  private String zerodhaLoginUrl;

  @Value("zerodha.base.ur")
  private String zerodhaBaseUrl;

  @Value("zerodha.access.token")
  private String zerodhaAccessUrl;

  @Value("dalaltrader.apiKey")
  private String apiKey;

  @Value("dalaltrader.apiSecret")
  private String apiSecret;

  public String getRequestToken() {
    log.info("String getRequestToken method Begins");

    String requestToken = restTemplate.getForEntity(zerodhaLoginUrl, String.class).getBody();
    log.info("the request token is:{}", requestToken);
    return requestToken;
  }

  public JSONObject getAccessToken() {
    log.info("String accessToken method Begins");
    String requestToken = getRequestToken();
    String zerodhaSessionUri =
        UriComponentsBuilder.fromUriString(zerodhaBaseUrl).path(zerodhaAccessUrl).toUriString();
    String checkSum = ChecksumGenerator.generateChecksum(apiKey, requestToken, apiSecret);
    AccessTokenRequestDto accessTokenRequest =
        AccessTokenRequestDto.builder()
            .apiKey(apiKey)
            .requestToken(requestToken)
            .checkSum(checkSum)
            .build();
    log.info("AccessToken dto:{} to url:{}", accessTokenRequest, zerodhaSessionUri);
    // @NonNull String url, @NonNull Map<String, String> customHeaders, P entity,
    // ParameterizedTypeReference<T> type httpClient.post(zerodhaSessionUri)

    ResponseEntity<JSONObject> accessToken =
        httpClient.post(
            zerodhaSessionUri,
            Collections.singletonMap(Constants.X_KITE_VERSION, Constants.X_KITE_VERSION_VALUE),
            accessTokenRequest,
            new ParameterizedTypeReference<JSONObject>() {});
    log.info("the accessToken is:{}", accessToken.getBody());

    log.info("String accessToken method Ends");

    return accessToken.getBody();
  }
}
