# AGENTS.md

This file provides guidance to AI coding agents working with code in this repository.

## Project

Java 17+ client library for the [LIFX HTTP API](https://api.developer.lifx.com/), built with Gradle (wrapper pins
Gradle 9.8.1). Published as `com.github.gilbertotcc.lifx:lifx-client`; the version lives in `gradle.properties`.

Gradle itself needs a JVM 17 or later. Compilation and tests use a JDK 25 toolchain (`java.toolchain` in
`build.gradle`), while main and test classes are compiled with `--release 17` (`minJava` in `build.gradle`), so the
published library runs on Java 17+. Gradle auto-detects installed JDKs, SDKMAN ones included; if none matches, the
foojay resolver plugin (`settings.gradle`) auto-provisions it into `~/.gradle/jdks`.

Dependency and plugin versions live in the version catalog `gradle/libs.versions.toml` (except the foojay plugin in
`settings.gradle` and the Checkstyle/JaCoCo `toolVersion`s in `build.gradle`). `gradle.properties` enables the
configuration cache and the build cache, so build logic must stay configuration-cache compatible (lazy
`tasks.register`/`tasks.named`, no `Project` access at execution time).

`.sdkmanrc` pins the JDK for [SDKMAN](https://sdkman.io/) (Temurin, as in CI): `sdk env install` installs it,
`sdk env` switches the current shell to it and `sdk env clear` reverts. Set `sdkman_auto_env=true` in
`~/.sdkman/etc/config` to switch automatically on `cd`.

## Commands

```bash
./gradlew build                 # compile, checkstyle, all tests
./gradlew check                 # checkstyle + JUnit 5 tests (`test`) + Spock specs (`spockTest`)
./gradlew test                  # JUnit 5 tests only (Java, src/test/java)
./gradlew spockTest             # Spock specs only (Groovy, src/test/groovy, run on the JUnit Platform)
./gradlew test --tests 'com.github.gilbertotcc.lifx.operations.ListLightsQueryTest'
./gradlew spockTest --tests 'com.github.gilbertotcc.lifx.models.SelectorsSpec'
./gradlew checkstyleMain checkstyleTest checkstyleIntegrationTest
./gradlew test spockTest        # also writes the JaCoCo report (HTML in build/reports/jacocoHtml)
./gradlew check -PtestJavaVersion=17  # run the tests on another JVM (default 25)
./gradlew integrationTest       # live LIFX cloud test (src/integrationTest), needs LIFX_PAT; not part of check
```

`test` runs only the `junit-jupiter` and `archunit` engines, `spockTest` only the `spock` engine (Spock 2.5 on
Groovy 5.0). `-PtestJavaVersion=<N>` chooses the JVM both tasks run on, while compilation stays on the toolchain.
Mockito is loaded as a `-javaagent` through the `mockitoAgent` configuration.

CI runs on GitHub Actions: the `Build` job (`.github/workflows/ci.yml`) is a matrix over `test-java: [17, 25]` that
installs that JDK plus 25 (Gradle runs on 25) and runs `./gradlew check --continue -PtestJavaVersion=<N>` on pull
requests to and pushes to `main`. It reports test results on pull requests, and JaCoCo coverage from the 25 leg only.
Workflows reference actions by version tag (`@vX.Y.Z`), not commit SHA; Dependabot (`.github/dependabot.yml`) updates
them weekly in one grouped pull request.

Dependabot also updates Gradle dependencies, plugins and the wrapper weekly, after a 7-day cooldown: minor and patch
updates in one grouped pull request, majors separately (grouped per family, such as Jackson or JUnit). Upgrades that
need a migration (Spock/Groovy line changes) are ignored; each ignore rule in
`.github/dependabot.yml` states its reason and when to remove it.

WireMock is on a 4.0.0 beta on purpose: 3.13.2 pulls a vulnerable handlebars and Jetty 11. Move to 4.x GA once
released (see the comment in `gradle/libs.versions.toml`).

No remote publishing repository is configured; `maven-publish` provides `publishToMavenLocal`.

`LifxClientIntegrationTest` lives in its own JVM test suite (`src/integrationTest`) and runs only through
`integrationTest`: it hits the real LIFX cloud with the token in `LIFX_PAT` and changes the power of a real light,
restoring it at the end. `check` (and so CI) compiles and checkstyles it but never runs it. The suite gets neither
the Mockito agent nor JaCoCo.

### Markdown checks

```bash
npx markdownlint-cli2           # lint Markdown files (config: .markdownlint-cli2.yaml)
lychee .                        # check URLs in Markdown files (config: lychee.toml, ignore list: .lycheeignore)
```

GitHub Actions (`.github/workflows/check-markdown.yml`) runs both on pull requests that touch Markdown files or their
configuration. Both tools skip `.claude/` (third-party skills managed through `skills-lock.json`) and git-ignored
files. Every `.lycheeignore` entry needs a dated comment explaining why the URL is excluded.

## Architecture

Request flow: `LifxClient` (public interface, factory `LifxClient.newLifxClientFor(token)`) → `impl.LifxClientImpl` →
one `operations.*Command`/`*Query` per endpoint → Retrofit `api.LifxApi` → wrapped by `impl.CallExecutor`.

- **Operations** (`operations/`): each endpoint is an `Operation<I, O>` with its own `*Input` / `*Output` (or
  generic `CommandOutput<T>`) class. Naming: `*Query` for reads, `*Command` for writes. Adding an endpoint means:
  method in `LifxApi`, new Input/Command(+Output) in `operations`, wiring in `LifxClientImpl.createNewClientFor`, and a
  method on `LifxClient`.
- **Error handling**: no exceptions are thrown to callers — every client method returns vavr
  `Either<LifxCallException, T>`. `CallExecutor` turns non-2xx responses into a `LifxCallException` whose cause is a
  `LifxErrorException` (carrying the `LifxErrorType`), and I/O failures into `LifxCallException`.
- **HTTP setup**: `LifxClientImpl.createNewClientFor(baseUrl, token)` builds Retrofit with `LifxOkHttpClientFactory`
  (bearer auth + logging interceptor), a `StringConverterFactory` that renders `LightSelector` path params via
  `LightSelectorConverter`, and Jackson 3 using the shared `util.JacksonUtils.OBJECT_MAPPER`. Retrofit has no Jackson
  3 converter, so `impl.JacksonConverterFactory` is in-house; it wraps Jackson's unchecked `JacksonException` in
  `IOException` so `CallExecutor` still reports (de)serialization failures as `LifxCallException`. The `baseUrl`
  overload exists for tests (WireMock).
- **Models** (`models/`): DTOs, mostly records (the `State` and effect hierarchies stay Lombok classes, since records
  can't extend), plus the selector hierarchy (`LightSelector`, `CombinableLightSelector`, `RandomizableLightSelector`,
  multi-zone selectors), created via the `Selectors` factory. Custom Jackson (de)serializers live in
  `models/serializer` and `models/deserializer`.

## Enforced rules

ArchUnit tests in `src/test/java/.../arch` fail the build if violated:

- Classes in `..lifx.models` may only depend on `models` itself, `java.lang`, `java.time`, `java.util..`, `java.io`,
  and `io.vavr..` (not Jackson, Retrofit, Lombok runtime types, etc.); dependencies that come from annotations are
  ignored, since ArchUnit 1.x counts them but the rule targets code. Note: `models.serializer`/`deserializer`/
  `converter` are subpackages, so they are not covered by this rule.
- No `System.out`/`System.err`, no generic exceptions thrown, no `java.util.logging` (use SLF4J via Lombok `@Slf4j`).

The compiler also fails the build on:

- Any `javac` lint warning (`-Xlint:all -Werror`; only `processing` is excluded) and any Error Prone finding, in main
  and test code.
- NullAway errors in main code. Every main package has a `package-info.java` annotated with JSpecify `@NullMarked`
  (add one to new packages), so types are non-null unless annotated `@Nullable`. Lombok copies the annotations to
  generated code (`lombok.addNullAnnotations = jspecify`).

Suppress a check only when unavoidable, with a comment giving the reason.

Style: 2-space indent, 120-char lines, no star imports, braces always required (Checkstyle config in
`config/checkstyle/`). Immutable data carriers are records (accessors are `x()`); `lombok.config` sets
`accessors.fluent = false`, so the remaining Lombok getters are `getX()`.

## Tests

JSON fixtures for request/response bodies are under `src/test/resources/json/`; operation tests share helpers in
`operations/OperationsTestFixtures` and `testutil/TestUtils`. HTTP is mocked with WireMock, JSON compared with
JSONAssert. The live test in `src/integrationTest` shares nothing with `src/test`: it has its own classpath (the
library, JUnit Jupiter, Logback) and its own `logback-test.xml`.
