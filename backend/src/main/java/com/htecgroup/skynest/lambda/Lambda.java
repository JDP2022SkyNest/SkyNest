package com.htecgroup.skynest.lambda;

public interface Lambda<T, U> {
  U doLambdaFunction(T objects);

  LambdaType getLambdaType();
}
