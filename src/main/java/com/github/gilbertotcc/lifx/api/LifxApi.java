package com.github.gilbertotcc.lifx.api;

import java.util.List;

import com.github.gilbertotcc.lifx.models.BreatheEffect;
import com.github.gilbertotcc.lifx.models.Color;
import com.github.gilbertotcc.lifx.models.Cycle;
import com.github.gilbertotcc.lifx.models.LightSelector;
import com.github.gilbertotcc.lifx.models.PulseEffect;
import com.github.gilbertotcc.lifx.models.TogglePower;
import com.github.gilbertotcc.lifx.models.StateDelta;
import com.github.gilbertotcc.lifx.models.Light;
import com.github.gilbertotcc.lifx.models.LightsStates;
import com.github.gilbertotcc.lifx.models.OperationResult;
import com.github.gilbertotcc.lifx.models.Result;
import com.github.gilbertotcc.lifx.models.Results;
import com.github.gilbertotcc.lifx.models.State;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface LifxApi {

  @GET("/v1/lights/{selector}")
  Call<List<Light>> listLights(final @Path("selector") LightSelector lightSelector);

  @PUT("/v1/lights/{selector}/state")
  Call<Results<Result>> setLightsState(final @Path("selector") LightSelector lightSelector, final @Body State state);

  @PUT("/v1/lights/states")
  Call<Results<OperationResult>> setLightsStates(final @Body LightsStates lightsStates);

  @POST("/v1/lights/{selector}/state/delta")
  Call<Results<Result>> setLightsStateDelta(final @Path("selector") LightSelector lightSelector,
                                            final @Body StateDelta stateDelta);

  @POST("/v1/lights/{selector}/toggle")
  Call<Results<Result>> togglePower(final @Path("selector") LightSelector lightSelector,
                                    final @Body TogglePower togglePower);

  @POST("/v1/lights/{selector}/effects/breathe")
  Call<Results<Result>> breatheEffect(final @Path("selector") LightSelector lightSelector,
                                      final @Body BreatheEffect breatheEffect);

  @POST("/v1/lights/{selector}/effects/pulse")
  Call<Results<Result>> pulseEffect(final @Path("selector") LightSelector lightSelector,
                                    final @Body PulseEffect pulseEffect);

  @POST("/v1/lights/{selector}/cycle")
  Call<Results<Result>> cycle(final @Path("selector") LightSelector lightSelector, final @Body Cycle cycle);

  @GET("/v1/color")
  Call<Color> validateColor(final @Query("string") String colorString);
}
