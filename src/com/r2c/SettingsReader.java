package com.r2c;

import java.util.List;

import com.dushyant.xml.Database;
import com.dushyant.xml.Databases;
import com.dushyant.xml.Property;
import com.dushyant.xml.Settings;
import com.dushyant.yml.YmlException;

public class SettingsReader {
  Settings settings = null;
  public static Databases database = null;

  // public SettingsReader() throws YmlException {
  // try (final Yml<Settings> yml = new Yml<Settings>(new Settings());) {
  // this.settings = (Settings) yml.unmarshalFromFile("Settings.xml");
  // }
  // }

  public SettingsReader(Settings settings) throws YmlException {
    this.settings = settings;
  }

  protected Database getActiveDatabase() throws YmlException {
    if (!(settings instanceof Settings)) {
      throw new YmlException();
    }

    return getActiveDatabase(settings.getDatabases());
  }

  protected Database getActiveDatabase(final List<Database> databases) throws YmlException {
    String udb = settings.getUDB();
    Database database_ = database.get(udb);
    if (database_ == null) {
      throw new YmlException(String.format("Missing databsae config for - %s", udb));
    }
    return database_;
  }

  protected List<Property> getProperties() {
    return settings.getProperties();
  }

  protected String[] getAllQueries() {
    return null;
  }
}
