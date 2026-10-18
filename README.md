# LIFX Client

Java client to consume [LIFX HTTP API](https://api.developer.lifx.com/).

## Usage

The library requires Java 17 or later.

Build the library and install it into the local Maven repository with `./gradlew publishToMavenLocal`, then add
`mavenLocal()` to the repositories of your `build.gradle` file and include the `lifx-client` dependency as shown below.
Building needs a JDK 25 toolchain; Gradle detects an installed one or downloads it automatically.

```groovy
repositories {
  mavenLocal()
}

dependencies {
  implementation 'com.github.gilbertotcc.lifx:lifx-client:<VERSION>'
}
```

### Sample application

The following example shows how to use LIFX Client to turn on all the lights in red.

To generate the access token, visit [LIFX Cloud Settings page](https://cloud.lifx.com/settings).

```java
import static com.github.gilbertotcc.lifx.models.Selectors.all;

import com.github.gilbertotcc.lifx.LifxClient;
import com.github.gilbertotcc.lifx.models.Power;
import com.github.gilbertotcc.lifx.models.State;
import com.github.gilbertotcc.lifx.operations.SetLightsStateInput;

public class RedLightOnSwitcher {

  public static void main(String[] args) {

    LifxClient client = LifxClient.newLifxClientFor("<YOUR_ACCESS_TOKEN>");

    var state = State.builder()
      .power(Power.ON)
      .color("red")
      .brightness(1.0)
      .build();
    var input = new SetLightsStateInput(all(), state);

    client.setLightsState(input)
      .peekLeft(error -> System.err.println("LIFX call failed: " + error.getMessage()));
  }
}
```

Client methods never throw: each returns a vavr `Either` holding either the result or a `LifxCallException`.

`src/integrationTest` shows the client calling the real LIFX cloud, and the tests in `src/test` cover every operation
the LIFX HTTP API provides.

## Development

`./gradlew build` compiles the library and runs Checkstyle and the unit tests, which mock the LIFX API with WireMock.

### Integration test

`./gradlew integrationTest` runs `LifxClientIntegrationTest` against the real LIFX cloud. It is not part of `check` or
`build` (they only compile and lint its sources) and does not run in CI.

> [!WARNING]
> The test changes the state of your real lights: it switches the power of the first connected light twice, then
> restores its original power when it ends.

It needs a LIFX personal access token, generated on the [LIFX Cloud Settings page](https://cloud.lifx.com/settings),
in the `LIFX_PAT` environment variable; without it the test fails with `LIFX_PAT is not set`. Gradle does not read
`.env` files (`.env` is git-ignored, so it is a safe place to keep the token), so export the variable in your shell:

```bash
export LIFX_PAT='YOUR_ACCESS_TOKEN'
./gradlew integrationTest
```

Add `-PtestJavaVersion=17` to run it on another JVM (default 25).
