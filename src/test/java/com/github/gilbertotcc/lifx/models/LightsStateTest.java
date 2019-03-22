package com.github.gilbertotcc.lifx.models;

import static com.github.gilbertotcc.lifx.testutil.TestUtils.loadJsonFromFile;
import static com.github.gilbertotcc.lifx.testutil.TestUtils.serializeObject;

import java.io.IOException;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

class LightsStateTest {

  private static final String LIGHTS_STATE_JSON = "/json/request_body/lights_state.json";

  @Test
  void createLightsStateShouldSuccess() throws IOException, JSONException {
    String json = loadJsonFromFile(LIGHTS_STATE_JSON);

    State state = State.builder().power(Power.ON).build();
    LightsState lightsState = LightsState.of(LightsSelector.byId("1234"), state);
    String serializedObject = serializeObject(lightsState);

    JSONAssert.assertEquals(json, serializedObject, true);
  }
}
