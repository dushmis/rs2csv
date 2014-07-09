package com.dushyant.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Database implements java.io.Serializable {
  private static final long serialVersionUID = -2746076591550148645L;

  public void setName(String name) {
    this.name = name;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setDriver(String driver) {
    this.driver = driver;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  String name;
  String user;
  String password;
  String driver;
  String url;

  public String getName() {
    return this.name;
  }

  public String getUser() {
    return this.user;
  }

  public String getPassword() {
    return this.password;
  }

  public String getDriver() {
    return this.driver;
  }

  public String getUrl() {
    return this.url;
  }

  public Database() {

  }

  public void setUser(String user) {
    this.user = user;
  }

  public Database(String name, String user, String password, String driver, String url) {
    this.name = name;
    this.user = user;
    this.password = password;
    this.driver = driver;
    this.url = url;
  }

  @Override
  public String toString() {
    return String.format("Databases [name=%s, user=%s, password=%s, driver=%s, url=%s]", name, user, password, driver, url);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((driver == null) ? 0 : driver.hashCode());
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    result = prime * result + ((password == null) ? 0 : password.hashCode());
    result = prime * result + ((url == null) ? 0 : url.hashCode());
    result = prime * result + ((user == null) ? 0 : user.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    Database other = (Database) obj;
    if (driver == null) {
      if (other.driver != null) return false;
    } else if (!driver.equals(other.driver)) return false;
    if (name == null) {
      if (other.name != null) return false;
    } else if (!name.equals(other.name)) return false;
    if (password == null) {
      if (other.password != null) return false;
    } else if (!password.equals(other.password)) return false;
    if (url == null) {
      if (other.url != null) return false;
    } else if (!url.equals(other.url)) return false;
    if (user == null) {
      if (other.user != null) return false;
    } else if (!user.equals(other.user)) return false;
    return true;
  }

}
