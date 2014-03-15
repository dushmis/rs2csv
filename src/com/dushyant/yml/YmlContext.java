/*
 * @author - dushyant
 */

package com.dushyant.yml;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.helpers.DefaultValidationEventHandler;

/**
 * The Class YmlContext.
 */
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
    unmarshaller.setEventHandler(new DefaultValidationEventHandler());
    return unmarshaller;
  }

  @Override
  @SuppressWarnings("deprecation")
  public javax.xml.bind.Validator createValidator() throws JAXBException {
    return context.createValidator();
  }
}
