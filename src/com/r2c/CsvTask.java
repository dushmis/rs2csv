package com.r2c;

import java.sql.ResultSet;

import com.dushyant.xml.Database;
import com.dushyant.xml.Property;

public final class CsvTask implements Runnable {
  private final Property property;
  private final SettingsReader settingsReader;

  public CsvTask(SettingsReader settingsReader, Property property) {
    this.settingsReader = settingsReader;
    this.property = property;
  }

  private synchronized RObject executeDataQuery(Database db, String query) throws ConversionException, Exception {
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
      final RObject execute = executeDataQuery(settingsReader.getActiveDatabase(), property.getValue());
      final String fileName = String.format("%s-%s.%s", property.getName(), (new java.util.Date()).getTime(), Constants.CSV);
      execute.toCSV(fileName);
    } catch (Exception e) {
      // FIXME
      e.printStackTrace();
    }
  }
}
