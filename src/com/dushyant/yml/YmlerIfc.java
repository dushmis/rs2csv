package com.dushyant.yml;

public interface YmlerIfc<E> {
  void append(E e) throws YmlException;

  E get(Class<E> className) throws YmlException;

  void exportXml(E e) throws YmlException;

  E importXml(Class<E> e, String fileName) throws YmlException;
}
