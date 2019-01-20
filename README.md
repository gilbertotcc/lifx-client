# LIFX Client

Java client to consume [LIFX HTTP API](https://api.developer.lifx.com/).

## Usage with Maven

Library artifactories are published on Bintray. In order to use them, add the
_lifx_ Bintray repository to your POM file and include the client dependency.

```xml
<repositories>
    <repository>
        <id>bintray-gilbertotcc-lifx</id>
        <name>bintray</name>
        <url>https://dl.bintray.com/gilbertotcc/lifx</url>
    </repository>
</repositories>
```

```xml
<dependencies>
    <dependency>
        <groupId>com.github.gilbertotcc.lifx</groupId>
        <artifactId>lifx-client</artifactId>
        <version>${lifx-client.version}</version>
    </dependency>
</dependencies>
```

Then take a look at examples in `test` directory to understand how a LIFX client
can be created and used.