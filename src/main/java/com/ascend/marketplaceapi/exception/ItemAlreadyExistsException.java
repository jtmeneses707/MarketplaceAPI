package com.ascend.marketplaceapi.exception;

public class ItemAlreadyExistsException extends RuntimeException{
  public ItemAlreadyExistsException() {
  }

  public ItemAlreadyExistsException(String message) {
    super(message);
  }

  public ItemAlreadyExistsException(String message, Throwable cause) {
    super(message, cause);
  }

  public ItemAlreadyExistsException(Throwable cause) {
    super(cause);
  }

  public ItemAlreadyExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
