package com.dushyant.yml;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

public class YmlerImpl<E> implements YmlerIfc<E> {

  public YmlerImpl() {}

  @Override
  public void append(E e) throws YmlException {
    throw new YmlException(new Throwable("not implemented"));
  }

  @Override
  public E get(Class<E> className) throws YmlException {
    try {
      E newInstance = className.newInstance();
      try (Yml<E> yml = new Yml<E>(newInstance)) {
        Constructor<?>[] constructors = className.getConstructors();
        if (constructors.length < 1) {
          throw new YmlException(new Throwable("Default constructors is required"));
        }
        newInstance = null;
        E unmarshalFromFile =
            yml.unmarshalFromFile(className.getSimpleName() + YmlConstants.DOT
                + YmlConstants.EXTENSION);
        return unmarshalFromFile;
      }
    } catch (SecurityException | IllegalAccessException | IllegalArgumentException
        | InstantiationException e) {
      throw new YmlException(e);
    }
  }

  private boolean isAnnotationPresent(E e) {
    boolean isValid = false;
    final Class<? extends Object> clazz = e.getClass();
    if (clazz.isAnnotationPresent(XmlAccessorType.class)
        && clazz.isAnnotationPresent(XmlRootElement.class)) {
      isValid = true;
      for (Field field : clazz.getDeclaredFields()) {
        if (field.getType().equals(List.class)) {
          if (field.isAnnotationPresent(XmlElement.class)) {} else {
            isValid = false;
          }
        }
      }
    }
    return isValid;
  }

  @Override
  public void exportXml(E e) throws YmlException {
    if (!this.isAnnotationPresent(e)) {
      throw new YmlException(new Throwable("Invalid annotations in class " + e));
    }
    try (Yml<E> yml = new Yml<E>(e);) {
      yml.marshalToFile();
    }
  }


  @Override
  public E importXml(Class<E> eClass, String fileName) throws YmlException {
    E e2 = null;
    try {
      final E newInstance = eClass.newInstance();
      try (Yml<E> yml = new Yml<E>(newInstance)) {
        e2 = yml.unmarshalFromFile(fileName);
      }
    } catch (InstantiationException | IllegalAccessException e) {
      throw new YmlException(e);
    }
    return e2;
  }
}
