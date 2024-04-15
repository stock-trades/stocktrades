package io.stocktrades.service;

import com.zerodhatech.kiteconnect.KiteConnect;
import com.zerodhatech.kiteconnect.kitehttp.SessionExpiryHook;
import com.zerodhatech.kiteconnect.kitehttp.exceptions.KiteException;
import com.zerodhatech.models.Holding;
import com.zerodhatech.models.Order;
import com.zerodhatech.models.OrderParams;
import com.zerodhatech.models.User;
import io.stocktrades.constant.StockConstants;
import io.stocktrades.dto.request.OrderDto;
import io.stocktrades.dto.response.LogResponseDto;
import io.stocktrades.util.HttpClient;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

  @Value("${zerodha.login.url}")
  private String zerodhaLoginUrl;

  @Value("${dalaltrader.apiKey}")
  private String apiKey;

  @Value("${zerodha.access.url}")
  private String accessUrl;

  @Value("${dalaltrader.apiSecret}")
  private String apiSecret;

  @Value("${zerodha.base.url}")
  private String baseUrl;

  @Autowired
  private ObjectFactory<HttpSession> httpSessionFactory;

  private ModelMapper modelMapper;

  public String getRequestToken() {
    log.info("String getRequestToken method Begins");

    String requestToken = restTemplate.getForEntity(zerodhaLoginUrl, String.class).getBody();
    log.info("the request token is:{}", requestToken);
    return requestToken;
  }

  public KiteConnect getKiteConnect(String userId) {
    KiteConnect kiteconnect = new KiteConnect(apiKey);
    kiteconnect.setUserId(userId);
    String loginURL = kiteconnect.getLoginURL();

    log.info("the loginUrl is:{}", loginURL);
    // Set session expiry callback.
    kiteconnect.setSessionExpiryHook(
        new SessionExpiryHook() {
          @Override
          public void sessionExpired() {
            System.out.println("session expired");
          }
        });
    return kiteconnect;
  }

  public User getAccessToken(String requestToken, String userId) throws IOException, KiteException {
    log.info("String accessToken method Begins for reToken:{},userId:{}", requestToken, userId);

    KiteConnect kiteconnect = getKiteConnect(userId);
    User user = kiteconnect.generateSession(requestToken, apiSecret);
    httpSessionFactory.getObject().setAttribute("access_token",user.accessToken);
    log.info("the User  is:{}", user.toString());

    return user;
  }

  public Order postEquityOrder(OrderDto orderDto, String userId) throws IOException, KiteException {

    KiteConnect kiteConnect = getKiteConnect(userId);
    ModelMapper modelMapper = new ModelMapper();
    OrderParams orderParam = modelMapper.map(orderDto, OrderParams.class);

    Order regular = kiteConnect.placeOrder(orderParam, StockConstants.REGULAR);
    log.info("The regular order:{}", regular);
    return regular;
  }

  public List<Holding> getHoldings(String userId,String accessToken) throws IOException, KiteException {
    log.info("getHoldings method Begins");
    KiteConnect kiteConnect = getKiteConnect(userId);
    Object sessionAccessToken = httpSessionFactory.getObject().getAttribute("access_token");
    /*if(!Objects.isNull(sessionAccessToken))
    {
      log.info("getting access_token from session:{}",sessionAccessToken.toString());
      kiteConnect.setAccessToken(sessionAccessToken.toString());
    } */
    kiteConnect.setAccessToken(accessToken);

    return kiteConnect.getHoldings();
  }

  public Order buyStock(String userId, String stockName, String price)
      throws IOException, KiteException {
    KiteConnect kiteConnect = getKiteConnect(userId);

    Order order = kiteConnect.placeOrder(getOrderParams(stockName, price), StockConstants.REGULAR);
    return order;
  }

  public LogResponseDto logout(String userId, String apiKey, String accessToken)
      throws IOException, KiteException {

    String uriString =
        UriComponentsBuilder.fromHttpUrl(baseUrl)
            .path(accessUrl)
            .query("api_key={api}&access_token={accessToken}")
            .buildAndExpand(apiKey, accessToken)
            .toUriString();
    log.info("the uri String for logout is:{}", uriString);
    ResponseEntity<LogResponseDto> delete =
        httpClient.delete(
            uriString,
            null,
            Collections.singletonMap(StockConstants.KITE_HEADER,StockConstants.V3),
            new ParameterizedTypeReference<LogResponseDto>() {});
    log.info("apiKey is:{}, accessToken:{}, delete response:{}", apiKey, accessToken,delete);
    httpSessionFactory.getObject().invalidate();
    log.info("logout response model is:{}", delete);
    return delete.getBody();
  }

  private OrderParams getOrderParams(String stockName, String price) {

    OrderDto orderDto = new OrderDto();
    orderDto.setExchange(StockConstants.exchangeType.get("NSE"));
    orderDto.setProduct("CNC");
    orderDto.setTradingsymbol(stockName);
    orderDto.setValidity("IOC");
    orderDto.setTransactionType(StockConstants.transactionType.get("BUY"));
    orderDto.setOrderType(
        StringUtils.isNotBlank(price)
            ? StockConstants.orderType.get("LIMIT")
            : StockConstants.orderType.get("MARKET"));
    orderDto.setPrice(StringUtils.isNotBlank(price) ? Double.parseDouble(price) : null);

    log.info("before converting the orderDto");
    OrderParams order = modelMapper.map(orderDto, OrderParams.class);
    log.info("after converting the orderDto");

    return order;
  }

  /*
  put("quantity", "1");
  put("order_type", "SL");
  put("tradingsymbol", "HINDALCO");
  put("product", "CNC");
  put("exchange", "NSE");
  put("transaction_type", "BUY");
  put("validity", "DAY");
  put("price", "158.0");
  put("trigger_price", "157.5");
  */

}
