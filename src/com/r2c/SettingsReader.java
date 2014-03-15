package com.r2c;

import java.util.List;

import com.dushyant.xml.Database;
import com.dushyant.xml.Property;
import com.dushyant.xml.Settings;
import com.dushyant.yml.Yml;
import com.dushyant.yml.YmlException;

public class SettingsReader {
  Settings settings = null;

  public SettingsReader() throws YmlException {
    try (final Yml<Settings> yml = new Yml<Settings>(new Settings());) {
      this.settings = (Settings) yml.unmarshalFromFile("Settings.xml");
    }
  }

  protected Database getActiveDatabase() throws YmlException {
    if (!(settings instanceof Settings)) {
      throw new YmlException();
    }
    final List<Database> databases = settings.getDatabases();
    String useDatabase = settings.getUDB();
    if (databases instanceof List<?> && useDatabase instanceof String) {
      for (Database db : databases) {
        if (db.getName().equals(useDatabase)) {
          return db;
        }
      }
    }
    throw new YmlException("coudn't find database configuration...");
  }

  protected List<Property> getProperties() {
    return settings.getProperties();
  }

  protected String[] getAllQueries() {
    return null;
  }
}
