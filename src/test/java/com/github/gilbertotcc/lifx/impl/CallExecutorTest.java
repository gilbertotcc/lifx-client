package com.github.gilbertotcc.lifx.impl;

import com.github.gilbertotcc.lifx.exception.LifxCallException;
import okhttp3.Request;
import org.junit.jupiter.api.Test;
import retrofit2.Call;

import java.io.IOException;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CallExecutorTest {

  @Test
  @SuppressWarnings("unchecked")
  void executeShouldReturnLeftEitherEncapsulatingIoException() throws IOException {
    var ioException = new IOException("error");
    var callMock = callMockThatFailsWith(ioException);

    var callExecutor = new CallExecutor<String>(callMock);
    var response = callExecutor.execute(Function.identity());

    assertTrue(response.isLeft());
  }

  @Test
  @SuppressWarnings("unchecked")
  void executeShouldReturnLeftEither() throws IOException {
    var lifxCallException = new LifxCallException(new RuntimeException());
    var callMock = callMockThatFailsWith(lifxCallException);

    var callExecutor = new CallExecutor<String>(callMock);
    var response = callExecutor.execute(Function.identity());

    assertTrue(response.isLeft());
    assertEquals(lifxCallException, response.getLeft());
  }

  @SuppressWarnings("unchecked")
  private Call callMockThatFailsWith(Throwable cause) throws IOException {
    var callMock = mock(Call.class, RETURNS_DEEP_STUBS);
    var request = new Request.Builder().get().url("http://localhost:8080/test").build();
    when(callMock.execute()).thenThrow(cause);
    when(callMock.request()).thenReturn(request);
    return callMock;
  }
}
