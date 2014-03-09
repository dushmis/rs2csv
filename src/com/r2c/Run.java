package com.r2c;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.dushyant.xml.Database;
import com.dushyant.xml.Property;
import com.dushyant.yml.YmlException;

public class Run {

  public static void main(String[] args) {
    try {
      Run run = new Run();
      SettingsReader settingsReader = new SettingsReader();
      Database activeDatabase = settingsReader.getActiveDatabase();
      for (Property property : settingsReader.getProperties()) {
        final String attribute = property.getAttribute();
        if (attribute instanceof String && attribute.trim().equalsIgnoreCase("query")) {
          run.execute(activeDatabase, property.getValue()).toCSV(property.getName() + ".csv");
        }
      }
    } catch (YmlException | ConversionException e) {
      e.printStackTrace();
    }
  }

  private RObject execute(Database db, String query) {
    RObject rObject = null;
    R2c r2c = null;
    try (DummyConnectionManager conn = new DummyConnectionManager(db, query)) {
      final ResultSet resultSet = conn.getResultSet();
      r2c = new R2c(resultSet);
      r2c.addValidator(new Validator<ArrayList<String>>() {
        @Override
        public boolean isValid(ArrayList<String> t) {
          String lastColValue = t.get(t.size() - 1);
          if ((lastColValue == null ? "" : lastColValue).equals("administrator"))
            return true;
          else
            return false;
        }
      });
      rObject = r2c.getData();
    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
        | SQLException e) {
      e.printStackTrace();
    } catch (Exception e1) {
      e1.printStackTrace();
    }
    return rObject;
  }
}
