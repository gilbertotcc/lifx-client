package com.github.gilbertotcc.lifx.impl.operations;

public interface OperationResult<T> {

  static <T> OperationResult<T> success(T result) {
    return (Success<T>) () -> result;
  }

  static <T> OperationResult<T> failure(Throwable error) {
    return (Failure<T>) () -> error;
  }

  T getResult();

  boolean isSuccess();

  Throwable getError();

  interface Success<T> extends OperationResult<T> {

    default boolean isSuccess() {
      return true;
    }

    default Throwable getError() {
      throw new IllegalStateException("Operation is successful");
    }
  }

  interface Failure<T extends Object> extends OperationResult<T> {

    default T getResult() {
      throw new IllegalStateException("Operation is failed");
    }

    default boolean isSuccess() {
      return false;
    }
  }
}
