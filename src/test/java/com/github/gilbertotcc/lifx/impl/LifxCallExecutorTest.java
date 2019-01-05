package com.github.gilbertotcc.lifx.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.io.IOException;

import com.github.gilbertotcc.lifx.exception.LifxRemoteException;
import com.github.gilbertotcc.lifx.testutil.TestUtils;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.ResponseBody;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import retrofit2.Call;
import retrofit2.Response;

@RunWith(MockitoJUnitRunner.class)
public class LifxCallExecutorTest {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private Call<String> callMock;

    @Test
    public void getResultShouldSuccess() throws IOException {
        Response<String> response = Response.success("ok");
        when(callMock.execute()).thenReturn(response);

        String okResponse = LifxCallExecutor.of(callMock).getResponse();

        assertEquals("ok", okResponse);
    }

    @Test(expected = LifxRemoteException.class)
    public void getResultShouldFailForIOException() throws IOException {
        Request request = new Request.Builder().get().url("http://localhost:8080/test").build();
        when(callMock.execute()).thenThrow(new IOException("error"));
        when(callMock.request()).thenReturn(request);
        LifxCallExecutor.of(callMock).getResponse();
    }

    @Test(expected = LifxRemoteException.class)
    public void getResultShouldFailForNotSuccessfulResponse() throws IOException {
        String errorResponseBody = TestUtils.loadJsonFromFile("/json/response_body/error_response.json");
        Response<String> response = Response.error(404, ResponseBody.create(MediaType.parse("application/json"), errorResponseBody));
        when(callMock.execute()).thenReturn(response);

        LifxCallExecutor.of(callMock).getResponse();
    }
}
