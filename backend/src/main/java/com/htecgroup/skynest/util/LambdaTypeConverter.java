package com.htecgroup.skynest.util;

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
    return lambdaType.getCode();
  }

  @Override
  public LambdaType convertToEntityAttribute(String code) {
    if (code == null) {
      return null;
    }

    return Stream.of(LambdaType.values())
        .filter(c -> c.getCode().equals(code))
        .findFirst()
        .orElseThrow(IllegalArgumentException::new);
  }
}
