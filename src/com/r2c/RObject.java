package com.r2c;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;

public class RObject implements Serializable {
  static Logger logger = Logger.getLogger(RObject.class);

  EventHandler event = null;

  public void addEventHandler(EventHandler eventHandler) {
    this.event = eventHandler;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((csvHeaders == null) ? 0 : csvHeaders.hashCode());
    result = prime * result + ((data == null) ? 0 : data.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    RObject other = (RObject) obj;
    if (csvHeaders == null) {
      if (other.csvHeaders != null) return false;
    } else if (!csvHeaders.equals(other.csvHeaders)) return false;
    if (data == null) {
      if (other.data != null) return false;
    } else if (!data.equals(other.data)) return false;
    return true;
  }

  private static final long serialVersionUID = -6249419240611966559L;
  private Set<RObjectHeader> csvHeaders;
  private ArrayList<ArrayList<String>> data;

  public Set<RObjectHeader> getCsvHeaders() {
    return csvHeaders;
  }

  public void setCsvHeaders(Set<RObjectHeader> csvHeaders) {
    this.csvHeaders = csvHeaders;
  }

  public ArrayList<ArrayList<String>> getData() {
    return data;
  }

  public void setData(ArrayList<ArrayList<String>> data) {
    this.data = data;
  }

  @Override
  public String toString() {
    return String.format("Csv [csvHeaders=%s, data=%s]", csvHeaders, data);
  }

  public void toCSV(String fileName) throws ConversionException {
    logger.debug("tocsv");
    logger.debug("tocsv = " + fileName);
    try (PrintWriter writer = new PrintWriter(fileName, Constants.ENCODING)) {
      Set<RObjectHeader> headers = getCsvHeaders();
      Iterator<RObjectHeader> it = headers.iterator();
      while (it.hasNext()) {
        RObjectHeader cHeader = it.next();
        writer.print("\"" + cHeader.getHeader() + "\"");
        writer.print(Constants.SPLITTER);
      }
      logger.debug("tocsv = header written");
      writer.println("");
      ArrayList<ArrayList<String>> data = getData();
      logger.debug("tocsv = data size " + data.size());
      for (ArrayList<String> indata : data) {
        for (String da : indata) {
          writer.print("\"" + da + "\"");
          writer.print(Constants.SPLITTER);
        }
        writer.println("");
      }
      logger.debug("tocsv = data written ");

    } catch (UnsupportedEncodingException | FileNotFoundException e) {
      throw new ConversionException(e);
    }
    if (this.event instanceof EventHandler) {
      this.event.onDone(fileName);
    }
  }

  public RObject() {}
}
