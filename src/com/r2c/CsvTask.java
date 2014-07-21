package com.r2c;

import java.sql.ResultSet;

import com.dushyant.xml.Database;
import com.dushyant.xml.Property;

public final class CsvTask implements Runnable {
  private final SettingsReader settingsReader;
  private final Property property;

  public CsvTask(SettingsReader settingsReader, Property property) {
    this.settingsReader = settingsReader;
    this.property = property;
  }

  private RObject execute(Database db, String query) throws ConversionException, Exception {
    RObject rObject = null;
    R2c r2c = null;
    try (DummyConnectionManager conn = new DummyConnectionManager(db, query)) {
      final ResultSet resultSet = conn.getResultSet();
      r2c = new R2c(resultSet);
      rObject = r2c.getData();
    }
    return rObject;
  }

  @Override
  public void run() {
    try {
      final RObject execute = execute(settingsReader.getActiveDatabase(), property.getValue());
      final String fileName =
          String.format("%s-%s.csv", property.getName(), (new java.util.Date()).getTime());
      execute.toCSV(fileName);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
