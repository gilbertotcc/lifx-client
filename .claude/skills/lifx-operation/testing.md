# Testing an operation

Every operation gets a `<Name>Test` in `src/test/java/com/github/gilbertotcc/lifx/operations/`, run by `./gradlew test`
(JUnit 5, not Spock). HTTP is mocked with WireMock (`org.wiremock:wiremock`, 4.0.0 beta in
`gradle/libs.versions.toml`); bodies are compared with JSONAssert.

## Test class skeleton

Extend `OperationsTestFixtures`. It starts a `WireMockServer` on port 9999 before each test, stops it after, and
provides `wireMockServer`, `VALID_ACCESS_TOKEN` and `AUTHORIZED_CLIENT` (a `LifxClient` pointed at the mock). Do not
create your own server or client. Copy the structure and imports of
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
- Every client method returns `Either<LifxCallException, T>`. On success, unwrap with `.get()` and assert on the
  mapped values (`.lights()`, `.result()`, `.color()`). On failure, check `result.isLeft()` and `result.getLeft()`.

## Required cases

1. **Success with mapped values.** Stub a fixture, call the client, `.get()` the output and assert real values
   (sizes, ids, statuses, numeric fields with a delta, as `ValidateColorQueryTest` does with `0.0001`). Do not stop at
   "not null".
2. **One failure path** that returns a `LifxCallException` on the left. Pick one:
   - Malformed body, as in `ListLightsQueryTest.listLightsShouldFailForMalformedResponseBody`:

     ```java
     wireMockServer.stubFor(get(urlEqualTo("/v1/lights/all"))
       .withHeader("Authorization", equalTo("Bearer " + VALID_ACCESS_TOKEN))
       .willReturn(aResponse().withBody("not json")));

     final var result = AUTHORIZED_CLIENT.listLights(new ListLightsInput(all()));

     assertTrue(result.isLeft());
     assertInstanceOf(LifxCallException.class, result.getLeft());
     ```

   - Non-2xx with the shared error fixture: `aResponse().withStatus(422).withBody(loadJsonFromFile(
     "/json/response_body/error_response.json"))` (import `WireMock.aResponse`; `withStatus` is on the response
     builder). The left is still a `LifxCallException`; its cause is a `LifxErrorException` carrying the
     `LifxErrorType` (see `impl/CallExecutorTest`). Assert the type only if the operation adds behavior of its own.
3. **Request body check** for any operation that sends a body (`@Body`). Pick one:
   - On the wire: add `.withRequestBody(equalToJson(loadJsonFromFile("/json/request_body/<name>.json")))` to the stub
     (static imports `WireMock.equalToJson`; `withRequestBody` is on `MappingBuilder`). If the body is not matched,
     WireMock answers 404, the call fails and the test fails, so it needs no extra assertion. No existing operation
     test uses this yet; keep it to one stub per test.
   - On the model: serialize the body model and compare strictly, as in `models/LightsStateTest` and
     `util/JacksonUtilsTest`:

     ```java
     JSONAssert.assertEquals(loadJsonFromFile("/json/request_body/lights_state.json"), serializeObject(body), true);
     ```

     `serializeObject` and `deserializeJson` are static helpers in `testutil/TestUtils`, using the shared
     `JacksonUtils.OBJECT_MAPPER`. The test method then declares `throws IOException, JSONException`
     (`loadJsonFromFile` throws `IOException`; JSONAssert's `assertEquals` throws `JSONException`). With an inline
     expected string instead of a fixture, `throws JSONException` suffices (`JacksonUtilsTest`). Strict mode (`true`)
     also catches unwanted `null` fields.

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
| Request body | `request_body/<snake_case_body>.json` | `lights_state.json` |

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
