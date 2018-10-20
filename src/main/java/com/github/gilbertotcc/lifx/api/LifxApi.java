package com.github.gilbertotcc.lifx.api;

import java.util.List;

import com.github.gilbertotcc.lifx.models.Light;
import com.github.gilbertotcc.lifx.models.Results;
import com.github.gilbertotcc.lifx.models.LightsSelector;
import com.github.gilbertotcc.lifx.models.State;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface LifxApi {

    @GET("/v1/lights/{selector}")
    Call<List<Light>> listLights(final @Path("selector") LightsSelector lightsSelector);

    @PUT("/v1/lights/{selector}/state")
    Call<Results> setLightsState(final @Path("selector") LightsSelector lightsSelector, @Body State state);

}
