package com.dushyant.yml;

/**
 * The Interface IGenYml.
 * 
 * @param <E> the element type
 */
public interface YmlerIfc<E> {

  /**
   * Append.
   * 
   * @param e the e
   * @throws YmlException the yml exception
   */
  void append(E e) throws YmlException;

  /**
   * Gets the.
   * 
   * @param className the class name
   * @return the e
   * @throws YmlException the yml exception
   */
  E get(Class<E> className) throws YmlException;

  /**
   * Save.
   * 
   * @param e the e
   * @throws YmlException the yml exception
   */
  void save(E e) throws YmlException;
}
