package com.dushyant.yml;

/**
 * The Class YmlException.
 */
public class YmlException extends Exception {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = -6804862239403646634L;

  /**
   * Instantiates a new yml exception.
   */
  public YmlException() {

  }

  /**
   * Instantiates a new yml exception.
   * 
   * @param e the e
   */
  public YmlException(Throwable e) {
    super(e);
  }

  public YmlException(String string) {
    super(string);
  }

}
