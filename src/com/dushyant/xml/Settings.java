package com.dushyant.xml;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Settings implements Serializable {
  private static final long serialVersionUID = 1L;
  String id;
  String udb;
  @XmlElement(name = "database", type = Database.class)
  List<Database> databases;
  @XmlElement(name = "property", type = Property.class)
  List<Property> properties;

  public String getUdb() {
    return udb;
  }

  public void setUdb(String udb) {
    this.udb = udb;
  }

  public void setDatabases(List<Database> databases) {
    this.databases = databases;
  }

  public String getUDB() {
    return this.udb;
  }

  public synchronized String getId() {
    return id;
  }

  public List<Database> getDatabases() {
    return this.databases;
  }

  public synchronized void setId(String id) {
    this.id = id;
  }

  public synchronized List<Property> getProperties() {
    return properties;
  }

  public synchronized void setProperties(List<Property> properties) {
    this.properties = properties;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((databases == null) ? 0 : databases.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((properties == null) ? 0 : properties.hashCode());
    result = prime * result + ((udb == null) ? 0 : udb.hashCode());
    return result;
  }

  @Override
  public String toString() {
    return String.format("Settings [id=%s, udb=%s, databases=%s, properties=%s]", id, udb,
        databases, properties);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    Settings other = (Settings) obj;
    if (databases == null) {
      if (other.databases != null) return false;
    } else if (!databases.equals(other.databases)) return false;
    if (id == null) {
      if (other.id != null) return false;
    } else if (!id.equals(other.id)) return false;
    if (properties == null) {
      if (other.properties != null) return false;
    } else if (!properties.equals(other.properties)) return false;
    if (udb == null) {
      if (other.udb != null) return false;
    } else if (!udb.equals(other.udb)) return false;
    return true;
  }

  public Settings(String id, String udb, List<Database> databases, List<Property> properties) {
    super();
    this.id = id;
    this.udb = udb;
    this.databases = databases;
    this.properties = properties;
  }

  public Settings() {}

}
