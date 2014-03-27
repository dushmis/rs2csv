package com.r2c;

import java.sql.ResultSet;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.dushyant.xml.Database;
import com.dushyant.xml.Files;
import com.dushyant.xml.Property;
import com.dushyant.xml.Settings;
import com.dushyant.xml.XMLFiles;
import com.dushyant.yml.YmlException;
import com.dushyant.yml.YmlerImpl;

public class Run {
  private static final class FieldValidator extends Validator<ArrayList<String>> {
    public FieldValidator() {}

    @Override
    public boolean isValid(ArrayList<String> e) {
      return super.isValid(e);
    }
  }

  static Logger logger = Logger.getLogger(Run.class);

  public static void main(String[] args) throws InstantiationException, IllegalAccessException,
      ClassNotFoundException, YmlException, ConversionException, Exception {
    Run run = new Run();
    for (XMLFiles xmlFiles : new YmlerImpl<Files>().importXml(Files.class, "Files.xml").getFiles()) {
      if (xmlFiles.exists()) {
        @SuppressWarnings("unchecked")
        SettingsReader settingsReader =
            getXMLData(xmlFiles, (Class<Settings>) Class.forName(xmlFiles.getClassName()));
        for (Property property : settingsReader.getProperties()) {
          if (isValidQuery(property.getAttribute())) {
            final RObject execute =
                run.execute(settingsReader.getActiveDatabase(), property.getValue());
            execute.toCSV(property.getName() + ".csv");
          }
        }
      }
    }
  }

  private static SettingsReader getXMLData(XMLFiles xmlFiles, Class<Settings> clazz)
      throws InstantiationException, IllegalAccessException, YmlException {
    Settings newInstance = (Settings) clazz.newInstance();
    newInstance = new YmlerImpl<Settings>().importXml(clazz, xmlFiles.getFileName());

    SettingsReader settingsReader = new SettingsReader(newInstance);
    return settingsReader;
  }

  private static boolean isValidQuery(final String attribute) {
    return attribute instanceof String && attribute.trim().equalsIgnoreCase("query");
  }

  private RObject execute(Database db, String query) throws ConversionException, Exception {
    RObject rObject = null;
    R2c r2c = null;
    try (DummyConnectionManager conn = new DummyConnectionManager(db, query)) {
      final ResultSet resultSet = conn.getResultSet();
      r2c = new R2c(resultSet);
      final FieldValidator validator = new FieldValidator();
      r2c.addValidator(validator);
      rObject = r2c.getData();
    }
    return rObject;
  }
}
