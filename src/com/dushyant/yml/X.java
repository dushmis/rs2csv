package com.dushyant.yml;

import java.util.List;

import com.dushyant.xml.Files;
import com.dushyant.xml.XMLFiles;

public class X {
  public static void main(String[] args) {
    try {
      Files files = new Files();
      YmlerImpl<Files> impl = new YmlerImpl<Files>();
      files = impl.importXml(Files.class, "Files.xml");
      final List<XMLFiles> files2 = files.getFiles();
      for (XMLFiles xmlFiles : files2) {
        if (xmlFiles.exists()) {
          System.out.println(xmlFiles.getFileName() + " - file name exists");
          @SuppressWarnings("unchecked")
          Class<Object> clazz = (Class<Object>) Class.forName(xmlFiles.getClassName());
          Object newInstance = clazz.newInstance();
          newInstance = new YmlerImpl<Object>().importXml(clazz, xmlFiles.getFileName());
          System.out.println(newInstance.getClass());
        }
      }
    } catch (YmlException | ClassNotFoundException | InstantiationException
        | IllegalAccessException e) {
      e.printStackTrace();
    }

  }
}
