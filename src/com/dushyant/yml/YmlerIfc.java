package com.dushyant.yml;

public interface YmlerIfc<E> {
  void append(E e) throws YmlException;

  E get(Class<? extends E> className) throws YmlException;

  void exportXml(E e) throws YmlException;

  E importXml(Class<? extends E> e, String fileName) throws YmlException;
}
