package io.stocktrades.exception.handler;

import com.zerodhatech.kiteconnect.kitehttp.exceptions.TokenException;
import io.stocktrades.dto.response.LogResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class CustomExceptionHandler {

  // Exception Handler method added in CustomerController to
  // handle CustomerAlreadyExistsException exception
  @ExceptionHandler(value = TokenException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseEntity<LogResponseDto> handleTokenException(TokenException ex) {
    log.error("handleTokenException is:{}",ex.getMessage());
    return ResponseEntity.internalServerError()
        .body(new LogResponseDto("failure", ex.getMessage()));

  }
}
