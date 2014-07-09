package com.dushyant.yml;

public class YmlException extends Exception {

  private static final long serialVersionUID = -6804862239403646634L;

  public YmlException() {

  }

  public YmlException(Throwable e) {
    super(e);
  }

  public YmlException(String string) {
    super(string);
  }
}
