# Testing an operation

Every operation gets a `<Name>Test` in `src/test/java/com/github/gilbertotcc/lifx/operations/`, run by `./gradlew test`
(JUnit 5, not Spock). HTTP is mocked with WireMock (`org.wiremock:wiremock`, 4.0.0 beta in
`gradle/libs.versions.toml`); bodies are compared with JSONAssert.

## Test class skeleton

Extend `OperationsTestFixtures`. It starts a `WireMockServer` on a dynamic port before each test, stops it after, and
provides `wireMockServer`, `VALID_ACCESS_TOKEN` and `authorizedClient` (a `LifxClient` pointed at the mock, created per
test because the port is only known once the server has started). Do not create your own server or client. Copy the
structure and imports of
`src/test/java/com/github/gilbertotcc/lifx/operations/ListLightsQueryTest.java` (read it). Match its style: 2-space
indent, `final var`, no star imports, one static import per line, braces always.

Rules:

- Stub the HTTP verb that matches the `LifxApi` method (`get`, `put`, `post`, `delete`, all from `WireMock`) with
  `urlEqualTo("/v1/...")`. Include the query string in the URL when the operation sends one
  (`/v1/color?string=red` in `ValidateColorQueryTest`).
- Always add `.withHeader("Authorization", equalTo("Bearer " + VALID_ACCESS_TOKEN))` so the test also proves bearer
  auth.
- Load response bodies with `TestUtils.loadJsonFromFile("/json/...")` (classpath path, leading slash; the method
  declares `IOException`, so the test needs `throws IOException`).
- Every client method returns `LifxResult<T>`. On success, unwrap with `someResultOf(...)` from
  `OperationsTestFixtures`, which asserts a `LifxResult.Success.Some` and returns its `result()`, and assert on the
  mapped values. On failure, `failureOf(...)` from the same class asserts a `LifxResult.Failure` and returns its
  `LifxCallException`.

## Required cases

1. **Success with mapped values.** Stub a fixture, call the client, unwrap it with `someResultOf` and assert real values
   (sizes, ids, statuses, numeric fields with a delta, as `ValidateColorQueryTest` does with `0.0001`). Do not stop at
   "not null".
2. **Failure paths.** Add the operation to the `operations()` table in `OperationFailuresTest`: its name, the client
   call and the URL path. That covers a 4xx (422 with the shared error fixture), a 5xx and an I/O failure, each asserted
   on the `LifxCallException` cause. Don't use 404 in failure stubs, because WireMock also answers 404 to an unmatched
   request. If the operation has failure behavior of its own, also add it to its `<Name>Test`, as
   `ListLightsQueryTest.listLightsShouldFailForMalformedResponseBody` does:

   ```java
   wireMockServer.stubFor(get(urlEqualTo("/v1/lights/all"))
     .withHeader("Authorization", equalTo("Bearer " + VALID_ACCESS_TOKEN))
     .willReturn(aResponse().withBody("not json")));

   final var result = authorizedClient.listLights(new ListLightsInput(all()));

   assertInstanceOf(IOException.class, failureOf(result).getCause());
   ```

3. **Request body check** for any operation that sends a body (`@Body`):
   - By default, check it on the wire: add `.withRequestBody(equalToJson(loadJsonFromFile("/json/request_body/<name>.json")))`
     to the success stub (static imports `WireMock.equalToJson`; `withRequestBody` is on `MappingBuilder`), and set every
     field of the input so the wire names are really checked. If the body is not matched, WireMock answers 404, the call
     fails and the test fails, so it needs no extra assertion. Keep it to one stub per test.
   - For a serialization detail of the model itself, also serialize it and compare strictly, as in
     `models/LightsStateTest` and `util/json/JacksonUtilsTest`:

     ```java
     JSONAssert.assertEquals(loadJsonFromFile("/json/request_body/lights_state.json"), serializeObject(body), true);
     ```

     `serializeObject` and `deserializeJson` are static helpers in `testutil/TestUtils`, using the shared
     `JacksonUtils.OBJECT_MAPPER`. The test method then declares `throws IOException, JSONException`
     (`loadJsonFromFile` throws `IOException`; JSONAssert's `assertEquals` throws `JSONException`). With an inline
     expected string instead of a fixture, `throws JSONException` suffices (`JacksonUtilsTest`). Strict mode (`true`)
     also catches unwanted `null` fields.

4. **Empty body**, if the operation adds handling of its own. `SetLightsStateCommandTest` covers commands
   (`aResponse().withStatus(202)` gives `LifxResult.Success.None`) and `ListLightsQueryTest` covers queries
   (a `Failure` with an `IOException` cause); a new endpoint reuses that behavior and needs no extra test.

5. **Null inputs.** Add the new `*Input`'s null components to `OperationInputsTest`, and the new client method to the
   null-input test in `LifxClientImplTest`.

Operations with no body (reads, `DELETE`) skip case 3. Add one test per behavior; for example, a selector variant
(`byId`, `byLabel`) that changes the URL is worth its own test.

## Fixtures

All fixtures live in `src/test/resources/json/` and are plain JSON, copied from the spec examples when possible (see
`spec-lookup.md`).

| Kind | Path | Existing names |
| - | - | - |
| Success response | `response_body/<snake_case_operation>_OK.json` | `list_lights_OK.json`, `set_state_OK.json`, `set_states_OK.json`, `validate_color_OK.json` |
| Shared results response | `response_body/results_OK.json` | reuse for operations that return `results[]` |
| Error response | `response_body/error_response.json` | shared by all failure tests; do not copy it |
| Request body | `request_body/<snake_case_body>.json` | `lights_state.json`, `state.json`, `lights_states.json`, `state_delta.json`, `toggle_power.json`, `breathe_effect.json`, `pulse_effect.json`, `cycle.json` |

Name a new success fixture after the operation, in snake_case, with the `_OK` suffix. Reuse `results_OK.json` or an
existing `set_*_OK.json` when the response has the same shape instead of adding a duplicate. Change a fixture only if
the wire shape changed, and never loosen an assertion to make a test pass.

## Run

```bash
./gradlew test --tests 'com.github.gilbertotcc.lifx.operations.<Name>Test' --console=plain
./gradlew check --console=plain
```

Do not touch `src/integrationTest` unless asked: `LifxClientIntegrationTest` drives real lights through the LIFX cloud
with `LIFX_PAT`, and `check` never runs it.
