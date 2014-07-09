package com.r2c;

import java.io.Serializable;

public class RObjectHeader implements Serializable {
  private static final long serialVersionUID = 2172708572261604929L;
  private String header;

  public String getHeader() {
    return header;
  }

  public void setHeader(String header) {
    this.header = header;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((header == null) ? 0 : header.hashCode());
    return result;
  }

  public RObjectHeader(String header) {
    super();
    this.header = header;
  }

  @Override
  public String toString() {
    return "RObjectHeader [header=" + header + "]";
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    RObjectHeader other = (RObjectHeader) obj;
    if (header == null) {
      if (other.header != null) return false;
    } else if (!header.equals(other.header)) return false;
    return true;
  }

}
