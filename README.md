LIFX Client
===========

Java client to consume LIFX [HTTP API](https://api.developer.lifx.com/).

Usage with Maven
----------------

Add the Bintray repository and the client dependency in the POM file.

```xml
<repositories>
    <repository>
        <id>bintray-gilbertotcc-lifx</id>
        <name>bintray</name>
        <url>https://dl.bintray.com/gilbertotcc/lifx</url>
    </repository>
</repositories>

[...]

<dependencies>
    <dependency>
        <groupId>com.github.gilbertotcc.lifx</groupId>
        <artifactId>lifx-client</artifactId>
        <version>1.0.0</version>
    </dependency>
</dependencies>
```

Then take a look at examples in `test` directory to understand how a LIFX client can be created and used.