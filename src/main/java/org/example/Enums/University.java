package org.example.Enums;

public enum University {
  NONE("NONE"), SUT("SUT"), MTUSI("MTUSI");

  private final String value;

  University(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
