# LIFX Client

Java client to consume [LIFX HTTP API](https://api.developer.lifx.com/).

## Usage

Library artifactory is published on Bintray. In order to use it, add the `lifx` Bintray repository to your `build
.settings` Gradle file and include the `lifx-client` dependency as shown below.

```groovy
repositories {
  maven {
    url  "https://dl.bintray.com/gilbertotcc/lifx" 
  }
}

dependencies {
  compile 'com.github.gilbertotcc.lifx:lifx-client:<VERSION>'
}
```

### Sample application

Following code provides a brief example that show how LIFX Client can be used to turn on all the lights in red color.

To generate the access token, visit [LIFX Cloud Settings page](https://cloud.lifx.com/settings).

```java
import static com.github.gilbertotcc.lifx.models.Selectors.$.All;

import com.github.gilbertotcc.lifx.models.Power;
import com.github.gilbertotcc.lifx.models.State;

public class RedLightOnSwitcher {

  public static void main(String[] args) {

    var client = LifxClient.newLifxClientFor("<YOUR_ACCESS_TOKEN>");

    client.setLightsState(
      All(),
      State.builder()
        .power(Power.ON)
        .color("red")
        .brightness(1.0)
        .build()
    );

    System.exit(0);
  }
}
```

In `src/test` directory can be found more examples that show how LIFX client can be used for the more advanced 
operations that LIFX HTTP API provides.
