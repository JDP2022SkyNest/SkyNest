package com.htecgroup.skynest.lambda.converter;

import com.htecgroup.skynest.lambda.LambdaType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class LambdaTypeConverter implements AttributeConverter<LambdaType, String> {
  @Override
  public String convertToDatabaseColumn(LambdaType lambdaType) {
    if (lambdaType == null) {
      return null;
    }
    return lambdaType.getDatabaseCode();
  }

  @Override
  public LambdaType convertToEntityAttribute(String databaseCode) {
    if (databaseCode == null) {
      return null;
    }

    return Stream.of(LambdaType.values())
        .filter(c -> c.getDatabaseCode().equals(databaseCode))
        .findFirst()
        .orElseThrow(IllegalArgumentException::new);
  }
}
