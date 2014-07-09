package com.r2c;

public class ConversionException extends Exception {
  private static final long serialVersionUID = 2699550984181869850L;

  public ConversionException() {}

  public ConversionException(Throwable throwable) {
    initCause(throwable);
  }

  public ConversionException(String string) {
    super(string);
  }

}
