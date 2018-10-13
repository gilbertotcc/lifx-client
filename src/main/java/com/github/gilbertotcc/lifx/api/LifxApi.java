package com.github.gilbertotcc.lifx.api;

import java.util.List;

import com.github.gilbertotcc.lifx.models.Light;
import com.github.gilbertotcc.lifx.models.Selector;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface LifxApi {

    @GET("/v1/lights/{selector}")
    Call<List<Light>> listLights(@Path("selector") Selector selector);
}
