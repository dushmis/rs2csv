package com.dushyant.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "files")
public class Files implements java.io.Serializable {
  private static final long serialVersionUID = -2746076591550148645L;
  @XmlElement(name = "xmlfile", type = XMLFiles.class)
  public List<XMLFiles> files;

  public Files() {}

  public List<XMLFiles> getFiles() {
    return files;
  }

  public void setFiles(List<XMLFiles> files) {
    this.files = files;
  }


  @Override
  public String toString() {
    return String.format("Files [files=%s]", files);
  }
}
