package io.stocktrades.exception.handler;

import org.springframework.http.HttpStatus;

public class StockException extends RuntimeException{

    private HttpStatus httpStatus;
    public StockException(String message, int code) {
        super(message);
        this.httpStatus = HttpStatus.resolve(code);

    }


}
