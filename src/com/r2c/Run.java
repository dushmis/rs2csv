package com.r2c;

import java.sql.ResultSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.dushyant.xml.Database;
import com.dushyant.xml.Files;
import com.dushyant.xml.Property;
import com.dushyant.xml.Settings;
import com.dushyant.xml.XMLFiles;
import com.dushyant.yml.YmlException;
import com.dushyant.yml.YmlerImpl;

public class Run {
  private static final class CsvTask implements Runnable {
    private final Property property;
    private final Run run;
    private final SettingsReader settingsReader;

    private CsvTask(Property property, Run run, SettingsReader settingsReader) {
      this.property = property;
      this.run = run;
      this.settingsReader = settingsReader;
    }

    @Override
    public void run() {
      try {
        if (isValidQuery(property.getAttribute())) {
          final RObject execute = run.execute(settingsReader.getActiveDatabase(), property.getValue());
          final String fileName = String.format("%s-%s.%s", property.getName(), (new java.util.Date()).getTime(), Constants.CSV);
          execute.toCSV(fileName);
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  // private static final class FieldValidator extends Validator<ArrayList<String>> {
  // public FieldValidator() {}
  // @Override
  // public boolean isValid(ArrayList<String> e) {
  // return super.isValid(e);
  // }
  // }

  static Logger logger = Logger.getLogger(Run.class);

  public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, YmlException,
      ConversionException, Exception {
    final Run run = new Run();
    for (XMLFiles xmlFiles : new YmlerImpl<Files>().importXml(Files.class, Constants.DEFAULTFILE).getFiles()) {
      if (xmlFiles.exists()) {
        @SuppressWarnings("unchecked")
        final SettingsReader settingsReader = getXMLData(xmlFiles, (Class<Settings>) Class.forName(xmlFiles.getClassName()));

        final ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(2);

        for (final Property property : settingsReader.getProperties()) {
          CsvTask task = new CsvTask(property, run, settingsReader);
          newFixedThreadPool.submit(task);
        }
        newFixedThreadPool.awaitTermination(10, TimeUnit.SECONDS);
        newFixedThreadPool.shutdown();
      }
    }
  }

  private static SettingsReader getXMLData(XMLFiles xmlFiles, Class<Settings> clazz) throws InstantiationException, IllegalAccessException,
      YmlException {

    Settings newInstance = (Settings) clazz.newInstance();
    newInstance = new YmlerImpl<Settings>().importXml(clazz, xmlFiles.getFileName());

    SettingsReader settingsReader = new SettingsReader(newInstance);
    return settingsReader;
  }

  private static boolean isValidQuery(final String attribute) {
    return attribute instanceof String && attribute.trim().equalsIgnoreCase(Constants.QUERY);
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
}
