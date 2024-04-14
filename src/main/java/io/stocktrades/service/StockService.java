package io.stocktrades.service;

import com.zerodhatech.kiteconnect.KiteConnect;
import com.zerodhatech.kiteconnect.kitehttp.SessionExpiryHook;
import com.zerodhatech.kiteconnect.kitehttp.exceptions.KiteException;
import com.zerodhatech.models.Holding;
import com.zerodhatech.models.Order;
import com.zerodhatech.models.OrderParams;
import com.zerodhatech.models.User;
import io.stocktrades.Constants;
import io.stocktrades.constant.StockConstants;
import io.stocktrades.dto.request.OrderDto;
import io.stocktrades.util.HttpClient;
import java.io.IOException;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
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

  @Value("${dalaltrader.apiSecret}")
  private String apiSecret;

  @Value("${zerodha.base.url}")
  private String baseUrl;

  private ModelMapper modelMapper;

  public String getRequestToken() {
    log.info("String getRequestToken method Begins");

    String requestToken = restTemplate.getForEntity(zerodhaLoginUrl, String.class).getBody();
    log.info("the request token is:{}", requestToken);
    return requestToken;
  }

  public KiteConnect getKiteConnect(String userId){
    KiteConnect kiteconnect = new KiteConnect(apiKey);
    kiteconnect.setUserId(userId);
    String loginURL = kiteconnect.getLoginURL();


    log.info("the loginUrl is:{}",loginURL);
    // Set session expiry callback.
    kiteconnect.setSessionExpiryHook(new SessionExpiryHook() {
      @Override
      public void sessionExpired() {
        System.out.println("session expired");
      }
    });
    return kiteconnect;
  }

  public User getAccessToken(String requestToken, String userId) throws IOException, KiteException {
    log.info("String accessToken method Begins for reToken:{},userId:{}", requestToken, userId);

    KiteConnect kiteconnect =getKiteConnect(userId);
    User user = kiteconnect.generateSession(requestToken, apiSecret);
    log.info("the User  is:{}",user.toString());

    return user;
  }

  public Order postEquityOrder(OrderDto orderDto,String userId) throws IOException, KiteException {

    KiteConnect kiteConnect = getKiteConnect(userId);
    ModelMapper modelMapper=new ModelMapper();
    OrderParams orderParam = modelMapper.map(orderDto, OrderParams.class);

    Order regular = kiteConnect.placeOrder(orderParam, StockConstants.REGULAR);
    log.info("The regular order:{}",regular);
    return regular;
  }

  public List<Holding> getHoldings(String userId) throws IOException, KiteException {
    KiteConnect kiteConnect = getKiteConnect(userId);

    return kiteConnect.getHoldings();

  }

  public Order buyStock(String userId,String stockName,String price) throws IOException, KiteException {
    KiteConnect kiteConnect = getKiteConnect(userId);

    Order order = kiteConnect.placeOrder(getOrderParams(stockName,price), StockConstants.REGULAR);
    return order;

  }

  public JSONObject logout(String userId,String apiKey,String accessToken) throws IOException, KiteException {

    String uriString = UriComponentsBuilder.fromHttpUrl(baseUrl).path("/")
            .query("api_key={api}&access_Token={accessToken}").buildAndExpand(apiKey, accessToken).toUriString();
    log.info("the uri String for logout is:{}",uriString);
    // httpClient.delete()
    KiteConnect kiteConnect = getKiteConnect(userId);
    log.info("apiKey is:{}, accessToken:{}",apiKey,accessToken);
    JSONObject logout = kiteConnect.logout();

    return logout;
  }

  private OrderParams getOrderParams(String stockName,String price){

    OrderDto orderDto=new OrderDto();
    orderDto.setExchange(StockConstants.exchangeType.get("NSE"));
    orderDto.setProduct("CNC");
    orderDto.setTradingsymbol(stockName);
    orderDto.setValidity("IOC");
    orderDto.setTransactionType(StockConstants.transactionType.get("BUY"));
    orderDto.setOrderType(StringUtils.isNotBlank(price)?StockConstants.orderType.get("LIMIT"):StockConstants.orderType.get("MARKET"));
    orderDto.setPrice(StringUtils.isNotBlank(price)?Double.parseDouble(price):null);

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
