package com.github.gilbertotcc.lifx.impl;

import com.github.gilbertotcc.lifx.exception.LifxCallException;
import com.github.gilbertotcc.lifx.exception.LifxErrorException;
import com.github.gilbertotcc.lifx.exception.LifxErrorType;
import com.github.gilbertotcc.lifx.exception.LifxRateLimit;
import com.github.gilbertotcc.lifx.testutil.TestUtils;
import io.vavr.control.Option;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.Test;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
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

    assertEquals(Option.of("ok"), okResponse);
  }

  @Test
  @SuppressWarnings("unchecked")
  void executeShouldReturnNoneForSuccessfulResponseWithoutBody() throws IOException {
    final var callMock = mock(Call.class, RETURNS_DEEP_STUBS);
    Response<String> response = Response.success(null);
    when(callMock.execute()).thenReturn(response);

    var okResponse = new CallExecutor<String>(callMock).execute().get();

    assertEquals(Option.none(), okResponse);
  }

  @Test
  @SuppressWarnings("unchecked")
  void executeExpectingBodyShouldReturnBody() throws IOException {
    final var callMock = mock(Call.class, RETURNS_DEEP_STUBS);
    Response<String> response = Response.success("ok");
    when(callMock.execute()).thenReturn(response);

    var okResponse = new CallExecutor<String>(callMock).executeExpectingBody().get();

    assertEquals("ok", okResponse);
  }

  @Test
  @SuppressWarnings("unchecked")
  void executeExpectingBodyShouldFailForSuccessfulResponseWithoutBody() throws IOException {
    final var callMock = mock(Call.class, RETURNS_DEEP_STUBS);
    Request request = new Request.Builder().get().url("http://localhost:8080/test").build();
    Response<String> response = Response.success(null);
    when(callMock.request()).thenReturn(request);
    when(callMock.execute()).thenReturn(response);

    var exception = new CallExecutor<String>(callMock).executeExpectingBody().getLeft();

    assertInstanceOf(IOException.class, exception.getCause());
    assertEquals("Successful response has no body", exception.getCause().getMessage());
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
    assertEquals(404, cause.getStatusCode());
    assertEquals(errorResponseBody, cause.getRawBody());
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
    assertEquals(502, cause.getStatusCode());
    assertNull(cause.getErrorModel());
    assertEquals("<html>Bad Gateway</html>", cause.getRawBody());
    assertNull(cause.getRateLimit());
    assertEquals("Error SERVER_ERROR, HTTP 502 (Something went wrong on LIFX's end.)", cause.getMessage());
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

  @Test
  void executeShouldKeepRateLimitHeaders() throws IOException {
    var cause = errorFor(Headers.of(
      "X-RateLimit-Limit", "120",
      "X-RateLimit-Remaining", "0",
      "X-RateLimit-Reset", "1700000000"));

    assertEquals(new LifxRateLimit(120, 0, Instant.ofEpochSecond(1700000000L)), cause.getRateLimit());
  }

  @Test
  void executeShouldIgnoreMalformedRateLimitHeader() throws IOException {
    var cause = errorFor(Headers.of(
      "X-RateLimit-Limit", "many",
      "X-RateLimit-Remaining", "5",
      "X-RateLimit-Reset", "soon"));

    assertEquals(new LifxRateLimit(null, 5, null), cause.getRateLimit());
  }

  @Test
  void executeShouldHaveNoRateLimitIfAllHeadersAreMalformed() throws IOException {
    var cause = errorFor(Headers.of("X-RateLimit-Limit", "many"));

    assertNull(cause.getRateLimit());
  }

  @Test
  void executeShouldHaveNoRateLimitWithoutHeaders() throws IOException {
    var cause = errorFor(Headers.of());

    assertNull(cause.getRateLimit());
  }

  @Test
  @SuppressWarnings("unchecked")
  void executeShouldFailWithoutUrlIfRequestCannotBeBuilt() throws IOException {
    final var callMock = mock(Call.class);
    var failure = new IllegalArgumentException("bad selector");
    when(callMock.request()).thenThrow(failure);
    when(callMock.execute()).thenThrow(failure);

    var exception = new CallExecutor<String>(callMock).execute().getLeft();

    assertSame(failure, exception.getCause());
    assertEquals("Call <unknown request> failed with cause bad selector", exception.getMessage());
  }

  @Test
  @SuppressWarnings("unchecked")
  void executeExpectingBodyShouldFailWithoutUrlIfRequestCannotBeBuilt() throws IOException {
    final var callMock = mock(Call.class);
    Response<String> response = Response.success(null);
    when(callMock.request()).thenThrow(new IllegalStateException("no request"));
    when(callMock.execute()).thenReturn(response);

    var exception = new CallExecutor<String>(callMock).executeExpectingBody().getLeft();

    assertEquals("Call <unknown request> failed with cause Successful response has no body", exception.getMessage());
  }

  @SuppressWarnings("unchecked")
  private static LifxErrorException errorFor(final Headers headers) throws IOException {
    final var callMock = mock(Call.class);
    Request request = new Request.Builder().get().url("http://localhost:8080/test").build();
    okhttp3.Response rawResponse = new okhttp3.Response.Builder()
      .request(request)
      .protocol(Protocol.HTTP_1_1)
      .code(429)
      .message("Too Many Requests")
      .headers(headers)
      .build();
    Response<String> response = Response.error(ResponseBody.create("", MediaType.get("text/plain")), rawResponse);
    when(callMock.request()).thenReturn(request);
    when(callMock.execute()).thenReturn(response);

    var exception = new CallExecutor<String>(callMock).execute().getLeft();

    return assertInstanceOf(LifxErrorException.class, exception.getCause());
  }
}
