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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CallExecutorTest {

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
    when(callMock.execute()).thenThrow(new IOException("error"));
    when(callMock.request()).thenReturn(request);

    LifxCallException exception = new CallExecutor<String>(callMock).execute().getLeft();

    assertNotNull(exception);
  }

  @Test
  @SuppressWarnings("unchecked")
  void executeShouldFailForNotSuccessfulResponse() throws IOException {
    final var callMock = mock(Call.class, RETURNS_DEEP_STUBS);
    Request request = new Request.Builder().get().url("http://localhost:8080/test").build();
    String errorResponseBody = TestUtils.loadJsonFromFile("/json/response_body/error_response.json");
    Response<String> response = Response.error(
      404, ResponseBody.create(MediaType.parse("application/json"), errorResponseBody));
    when(callMock.request()).thenReturn(request);
    when(callMock.execute()).thenReturn(response);

    var exception = new CallExecutor<String>(callMock).execute().getLeft();
    LifxErrorException cause = (LifxErrorException) exception.getCause();

    assertEquals(LifxErrorType.NOT_FOUND, cause.getType());
    assertEquals("color is missing", cause.getError().getErrorMessage());
    assertEquals("color", cause.getError().getDetails().get(0).getField());
    assertEquals("is missing", cause.getError().getDetails().get(0).getMessages().get(0));
  }
}
