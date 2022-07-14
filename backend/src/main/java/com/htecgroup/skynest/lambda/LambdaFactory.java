package com.htecgroup.skynest.lambda;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class LambdaFactory implements BaseLambdaFactory {
  private final List<Lambda> lambdas;
  private Map<LambdaType, Lambda> map;

  @PostConstruct
  public void setUp() {
    map = lambdas.stream().collect(Collectors.toMap(Lambda::getLambdaType, Function.identity()));
  }

  @Override
  public Lambda createLambda(LambdaType type) {
    return map.get(type);
  }
}
