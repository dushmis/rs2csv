package com.dushyant.yml;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.helpers.DefaultValidationEventHandler;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

public class Yml<T> implements AutoCloseable {
  public static Marshaller getMarshaller(JAXBContext context) throws YmlException {
    try {
      return context.createMarshaller();
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

  YmlContext context;
  Marshaller marshaller;

  Unmarshaller unmarshaller;

  T object;

  public Yml(T object) throws YmlException {
    this.object = object;
    JAXBContext newInstance = null;
    try {
      newInstance = JAXBContext.newInstance(object.getClass());
    } catch (JAXBException e) {
      throw new YmlException(e);
    }
    context = new YmlContext(newInstance);
    marshaller = getMarshaller();
    unmarshaller = getUnmarshaller();
  }

  // auto
  @Override
  public void close() {
    context.close();
  }

  public Marshaller getMarshaller() {
    return context.createMarshaller();
  }


  public Unmarshaller getUnmarshaller() throws YmlException {
    try {
      final Unmarshaller createUnmarshaller = context.createUnmarshaller();
      // createUnmarshaller.setSchema(validate(createUnmarshaller));
      return createUnmarshaller;
    } catch (JAXBException e) {
      throw new YmlException(e);
    }
  }

  public void marshalToFile() throws YmlException {
    marshal(this.object.getClass().getSimpleName() + YmlConstants.DOT + YmlConstants.EXTENSION);
  }

  public void marshalToFile(String fileName) throws YmlException {
    marshal(fileName);
  }

  @SuppressWarnings("unchecked")
  public T unmarshalFromFile(String fileName) throws YmlException {
    return (T) unmarshal(fileName);
  }

  public boolean validate() throws YmlException {
    /*Schema schema = null;
    try {
      getUnmarshaller().setEventHandler(new DefaultValidationEventHandler());
      SchemaFactory sf = SchemaFactory.newInstance(javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI);
      schema = sf.newSchema(new File("Schema.xsd"));
    } catch (JAXBException | YmlException | SAXException e) {
      throw new YmlException(e);
    }*/
    return true ;
  }

  private void marshal(String simpleName) throws YmlException {
    try (FileWriter fileWriter = new FileWriter(simpleName);
        BufferedWriter writer = new BufferedWriter(fileWriter);) {
      getMarshaller().marshal(this.object, writer);
    } catch (IOException | JAXBException e) {
      throw new YmlException(e);
    }
  }

  private Object unmarshal(String simpleName) throws YmlException {
    try {
      return getUnmarshaller().unmarshal(new File(simpleName));
    } catch (JAXBException | YmlException e) {
      throw new YmlException(e);
    }
  }

  @SuppressWarnings("unused")
  private Schema validate(final Unmarshaller createUnmarshaller) throws YmlException {
    Schema schema = null;
    try {
      createUnmarshaller.setEventHandler(new DefaultValidationEventHandler());
      SchemaFactory sf = SchemaFactory.newInstance(javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI);
      schema = sf.newSchema(new File("Schema.xsd"));
    } catch (JAXBException | SAXException e) {
      throw new YmlException(e);
    }
    return schema;
  }

}
