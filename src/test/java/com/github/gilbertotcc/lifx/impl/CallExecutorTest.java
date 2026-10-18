package com.github.gilbertotcc.lifx.impl;

import com.github.gilbertotcc.lifx.exception.LifxCallException;
import com.github.gilbertotcc.lifx.exception.LifxErrorException;
import com.github.gilbertotcc.lifx.exception.LifxErrorType;
import com.github.gilbertotcc.lifx.testutil.TestUtils;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.Test;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CallExecutorTest {

  @Test
  @SuppressWarnings("unchecked")
  void executeShouldSuccess() throws IOException {
    final var callMock = mock(Call.class, RETURNS_DEEP_STUBS);
    Response<String> response = Response.success("ok");
    when(callMock.execute()).thenReturn(response);

    var okResponse = new CallExecutor<String>(callMock).execute().get();

    assertEquals("ok", okResponse);
  }

  @Test
  @SuppressWarnings("unchecked")
  void executeShouldFailForIoException() throws IOException {
    final var callMock = mock(Call.class, RETURNS_DEEP_STUBS);
    Request request = new Request.Builder().get().url("http://localhost:8080/test").build();
    IOException ioException = new IOException("error");
    when(callMock.execute()).thenThrow(ioException);
    when(callMock.request()).thenReturn(request);

    LifxCallException exception = new CallExecutor<String>(callMock).execute().getLeft();

    assertNotNull(exception);
    assertSame(ioException, exception.getCause());
  }

  @Test
  @SuppressWarnings("unchecked")
  void executeShouldFailForNotSuccessfulResponse() throws IOException {
    final var callMock = mock(Call.class, RETURNS_DEEP_STUBS);
    Request request = new Request.Builder().get().url("http://localhost:8080/test").build();
    String errorResponseBody = TestUtils.loadJsonFromFile("/json/response_body/error_response.json");
    Response<String> response = Response.error(
      404, ResponseBody.create(errorResponseBody, MediaType.get("application/json")));
    when(callMock.request()).thenReturn(request);
    when(callMock.execute()).thenReturn(response);

    var exception = new CallExecutor<String>(callMock).execute().getLeft();
    LifxErrorException cause = (LifxErrorException) exception.getCause();

    assertEquals(LifxErrorType.NOT_FOUND, cause.getType());
    assertEquals("color is missing", cause.getErrorModel().errorMessage());
    assertEquals("color", cause.getErrorModel().details().get(0).field());
    assertEquals("is missing", cause.getErrorModel().details().get(0).messages().get(0));
  }

  @Test
  @SuppressWarnings("unchecked")
  void executeShouldFailForNotJsonErrorBody() throws IOException {
    final var callMock = mock(Call.class, RETURNS_DEEP_STUBS);
    Request request = new Request.Builder().get().url("http://localhost:8080/test").build();
    Response<String> response = Response.error(
      502, ResponseBody.create("<html>Bad Gateway</html>", MediaType.get("text/html")));
    when(callMock.request()).thenReturn(request);
    when(callMock.execute()).thenReturn(response);

    var exception = new CallExecutor<String>(callMock).execute().getLeft();
    LifxErrorException cause = (LifxErrorException) exception.getCause();

    assertEquals(LifxErrorType.SERVER_ERROR, cause.getType());
    assertNull(cause.getErrorModel());
    assertEquals("Error SERVER_ERROR (Something went wrong on LIFX's end.)", cause.getMessage());
  }

  @Test
  @SuppressWarnings("unchecked")
  void executeShouldFailForEmptyErrorBody() throws IOException {
    final var callMock = mock(Call.class, RETURNS_DEEP_STUBS);
    Request request = new Request.Builder().get().url("http://localhost:8080/test").build();
    Response<String> response = Response.error(429, ResponseBody.create("", MediaType.get("application/json")));
    when(callMock.request()).thenReturn(request);
    when(callMock.execute()).thenReturn(response);

    var exception = new CallExecutor<String>(callMock).execute().getLeft();
    LifxErrorException cause = (LifxErrorException) exception.getCause();

    assertEquals(LifxErrorType.TOO_MANY_REQUESTS, cause.getType());
    assertNull(cause.getErrorModel());
  }
}
