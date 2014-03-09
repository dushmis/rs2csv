/*
 * @author - dushyant
 */
package com.dushyant.yml;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class Yml<T> {

  YmlContext context;

  Marshaller marshaller;

  Unmarshaller unmarshaller;

  T object;

  public Yml(T object) throws YmlException {
    this.object = object;
    context = getContext(object);
    marshaller = getMarshaller();
    unmarshaller = getUnmarshaller();
  }

  public YmlContext getContext(T object) throws YmlException {
    JAXBContext newInstance = null;
    try {
      newInstance = JAXBContext.newInstance(object.getClass());
    } catch (JAXBException e) {
      throw new YmlException(e);
    }
    return new YmlContext(newInstance);
  }

  public void close() {
    context.close();
  }

  public Marshaller getMarshaller() {
    return context.createMarshaller();
  }

  public static Marshaller getMarshaller(JAXBContext context) throws YmlException {
    try {
      return context.createMarshaller();
    } catch (JAXBException e) {
      throw new YmlException(e);
    }
  }

  public Unmarshaller getUnmarshaller() throws YmlException {
    try {
      return context.createUnmarshaller();
    } catch (JAXBException e) {
      throw new YmlException(e);
    }
  }

  public static Unmarshaller getUnmarshaller(JAXBContext context) throws YmlException {
    try {
      return context.createUnmarshaller();
    } catch (JAXBException e) {
      throw new YmlException(e);
    }
  }

  public void marshalToFile(String fileName) throws YmlException {
    marshal(fileName);
  }

  public void marshalToFile() throws YmlException {
    marshal(this.object.getClass().getSimpleName() + YmlConstants.DOT + YmlConstants.EXTENSION);
  }

  public Object unmarshalFromFile(String fileName) throws YmlException {
    return this.unmarshal(fileName);
  }

  private void marshal(String simpleName) throws YmlException {
    try {
      final FileWriter fileWriter = new FileWriter(simpleName);
      BufferedWriter writer = new BufferedWriter(fileWriter);
      getMarshaller().marshal(this.object, writer);
      // final File file = new File(simpleName);
      // System.out.println(file.getAbsolutePath());
      // getMarshaller().marshal(this.object, file);
      writer.close();
    } catch (IOException e) {
      System.out.println(e.getMessage());
      throw new YmlException(e);
    } catch (JAXBException e) {
      throw new YmlException(e);
    }
  }

  private Object unmarshal(String simpleName) throws YmlException {
    try {
      return getUnmarshaller().unmarshal(new File(simpleName));
    } catch (JAXBException e) {
      throw new YmlException(e);
    } catch (YmlException e) {
      throw new YmlException(e);
    }
  }

}
