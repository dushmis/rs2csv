package com.dushyant.yml;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.Unmarshaller.Listener;
import javax.xml.bind.helpers.DefaultValidationEventHandler;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

public class YmlContext extends JAXBContext implements AutoCloseable {

  private final JAXBContext context;
  private Marshaller marshaller;
  private Unmarshaller unmarshaller;

  public YmlContext(final JAXBContext context) {
    this.context = context;
  }

  @Override
  public Marshaller createMarshaller() {
    try {
      marshaller = context.createMarshaller();
      marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
    } catch (final PropertyException pe) {
      return marshaller;
    } catch (final JAXBException jbe) {
      return null;
    }
    return marshaller;
  }

  @Override
  public void close() {
    marshaller = null;
  }

  @Override
  public Unmarshaller createUnmarshaller() throws JAXBException {
    unmarshaller = context.createUnmarshaller();
    unmarshaller.setListener(new Listener() {

    });
    unmarshaller.setEventHandler(new DefaultValidationEventHandler());
    SchemaFactory sf = SchemaFactory.newInstance(javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI);
    Schema schema = null;
    try {
      schema = sf.newSchema(new File("myschema.xsd"));
    } catch (SAXException e) {}
    unmarshaller.setSchema(schema);
    return unmarshaller;
  }

  @Override
  @SuppressWarnings("deprecation")
  public javax.xml.bind.Validator createValidator() throws JAXBException {
    return null;
  }
}
