package com.nus.dealhunter.exception;

public class BrandServiceException extends RuntimeException  {
    public BrandServiceException(String message) {
        super(message);
    }

    public BrandServiceException(String message, Throwable cause) {
        super(message, cause);
    }


}