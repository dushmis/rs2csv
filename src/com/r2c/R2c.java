package com.r2c;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

public class R2c {
  ResultSet resultSet = null;
  Validator<ArrayList<String>> validator = null;

  public void addValidator(Validator<ArrayList<String>> validator) {
    this.validator = validator;
  }

  public R2c(ResultSet resultSet) {
    super();
    this.resultSet = resultSet;
  }

  public synchronized ResultSet getResultSet() {
    return resultSet;
  }

  public synchronized void setResultSet(ResultSet resultSet) {
    this.resultSet = resultSet;
  }

  public RObject getData() throws ConversionException {
    RObject csv = null;
    try {
      ArrayList<ArrayList<String>> data = null;
      Set<RObjectHeader> csvHeaders = null;
      if (!(this.getResultSet() instanceof ResultSet)) {
        throw new ConversionException("Invalid resultset");
      }
      final ResultSetMetaData metaData = this.getResultSet().getMetaData();
      csv = new RObject();
      csvHeaders = new LinkedHashSet<>();
      data = new ArrayList<ArrayList<String>>();
      for (int i = 1; i <= metaData.getColumnCount(); i++) {
        csvHeaders.add(new RObjectHeader(metaData.getColumnName(i)));
      }
      ArrayList<RObjectHeader> headers = new ArrayList<>(csvHeaders);
      while (this.getResultSet().next()) {
        ArrayList<String> innerData = new ArrayList<>();
        for (RObjectHeader rObjectHeader : headers) {
          innerData.add(this.getValue(rObjectHeader.getHeader()));
        }
        if ((this.validator instanceof Validator) && (this.validator.isValid(innerData))
            && (innerData.size() >= 1)) {
          data.add(innerData);
        }

      }
      data.trimToSize();
      csv.setCsvHeaders(csvHeaders);
      csv.setData(data);
    } catch (ConversionException | SQLException e) {
      throw new ConversionException(e);
    }
    return csv;
  }

  @SuppressWarnings("unused")
  private String getValue(int rowCount) throws SQLException, ConversionException {
    if (!(this.getResultSet() instanceof ResultSet)) {
      throw new ConversionException("cannot perform this action on null resultset");
    }
    String string = resultSet.getString(rowCount);
    if (resultSet.wasNull()) {
      System.out.println("was null");
      string = "";
    }
    return string;
  }

  private String getValue(String columnName) throws SQLException, ConversionException {
    if (!(this.getResultSet() instanceof ResultSet)) {
      throw new ConversionException("cannot perform this action on null resultset");
    }
    return resultSet.getString(columnName);
  }
}
