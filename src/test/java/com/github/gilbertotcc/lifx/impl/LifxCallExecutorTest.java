package com.github.gilbertotcc.lifx.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.io.IOException;

import com.github.gilbertotcc.lifx.exception.LifxRemoteException;
import com.github.gilbertotcc.lifx.testutil.JacksonTestUtils;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import retrofit2.Call;
import retrofit2.Response;

@RunWith(MockitoJUnitRunner.class)
public class LifxCallExecutorTest {

    @Mock
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
        when(callMock.execute()).thenThrow(new IOException("error"));
        LifxCallExecutor.of(callMock).getResponse();
    }

    @Test(expected = LifxRemoteException.class)
    public void getResultShouldFailForNotSuccessfulResponse() throws IOException {
        String errorResponseBody = JacksonTestUtils.loadJsonFromFile("/json/response_body/error_response.json");
        Response<String> response = Response.error(404, ResponseBody.create(MediaType.parse("application/json"), errorResponseBody));
        when(callMock.execute()).thenReturn(response);

        LifxCallExecutor.of(callMock).getResponse();
    }
}
