package com.r2c;

public interface Validator<E/* , T */> {
  public boolean isValid(E e/* , T t */);
}
