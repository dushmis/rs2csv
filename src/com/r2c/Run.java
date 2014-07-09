package com.r2c;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.dushyant.xml.Files;
import com.dushyant.xml.Property;
import com.dushyant.xml.Settings;
import com.dushyant.xml.XMLFiles;
import com.dushyant.yml.YmlException;
import com.dushyant.yml.YmlerImpl;

public class Run {
  private final static int NTHREAD = 2;

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
    for (XMLFiles xmlFiles : new YmlerImpl<Files>().importXml(Files.class, Constants.DEFAULTFILE).getFiles()) {
      if (xmlFiles.exists()) {
        @SuppressWarnings("unchecked")
        Class<? extends Settings> forName = (Class<? extends Settings>) Class.forName(xmlFiles.getClassName());
        final SettingsReader settingsReader = getXMLData(xmlFiles, forName);
        final ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(NTHREAD);

        for (final Property property : settingsReader.getProperties()) {
          if (isValidQuery(property.getAttribute())) {
            CsvTask task = new CsvTask(property, settingsReader);
            newFixedThreadPool.submit(task);
          }
        }
        newFixedThreadPool.awaitTermination(1, TimeUnit.SECONDS);
        newFixedThreadPool.shutdown();
      }
    }
  }

  private static SettingsReader getXMLData(XMLFiles xmlFiles, Class<? extends Settings> clazz) throws InstantiationException, IllegalAccessException,
      YmlException {
    Settings newInstance = (Settings) clazz.newInstance();
    newInstance = new YmlerImpl<Settings>().importXml(clazz, xmlFiles.getFileName());
    return new SettingsReader(newInstance);
  }

  private static boolean isValidQuery(final String attribute) {
    return attribute instanceof String && attribute.trim().equalsIgnoreCase(Constants.QUERY);
  }

}
