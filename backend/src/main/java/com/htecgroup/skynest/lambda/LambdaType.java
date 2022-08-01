package com.htecgroup.skynest.lambda;

import lombok.Getter;

import java.util.stream.Stream;

@Getter
public enum LambdaType {
  // The code is used to represent the lambdas in the database and as such should not be changed.
  UPLOAD_FILE_TO_EXTERNAL_SERVICE_LAMBDA("U_F_T_E_S_L", "Upload file to dropbox lambda"),

  SEND_BUCKET_STATS_TO_EMAIL_LAMBDA("S_B_S_T_E_L", "Send bucket stats to e-mail");

  private String databaseCode;

  private String name;

  LambdaType(String code, String name) {
    this.databaseCode = code;
    this.name = name;
  }

  public static LambdaType getLambda(String databaseCode) {
    return Stream.of(LambdaType.values())
        .filter(c -> c.getDatabaseCode().equals(databaseCode))
        .findFirst()
        .orElseThrow(IllegalArgumentException::new);
  }
}
