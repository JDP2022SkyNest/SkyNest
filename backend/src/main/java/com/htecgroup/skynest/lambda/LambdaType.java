package com.htecgroup.skynest.lambda;

public enum LambdaType {
  UPLOAD_FILE_TO_EXTERNAL_SERVICE_LAMBDA("UFTDL");

  private String code;

  LambdaType(String code) {
    this.code = code;
  }

  public String getCode() {
    return code;
  }
}
