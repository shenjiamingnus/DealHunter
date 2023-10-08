package com.nus.dealhunter.exception;

public class PriceHistoryServiceException extends RuntimeException  {

    public PriceHistoryServiceException(String message) {
        super(message);
    }

    public PriceHistoryServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
