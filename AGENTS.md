# AGENTS.md

This file provides guidance to AI coding agents working with code in this repository.

## Project

Java 14 client library for the [LIFX HTTP API](https://api.developer.lifx.com/), built with Gradle (wrapper pins
Gradle 8.14.3). Published as `com.github.gilbertotcc.lifx:lifx-client`; the version lives in `gradle.properties`.

Gradle itself can run on any JVM 8–24, but compilation and tests use a Java 14 toolchain (`java.toolchain` in
`build.gradle`); if no JDK 14 is installed, the foojay resolver plugin (`settings.gradle`) auto-provisions one into
`~/.gradle/jdks`. Checkstyle is pinned to 8.27 because newer versions reject `config/checkstyle/checkstyle.xml`.

## Commands

```bash
./gradlew build                 # compile, checkstyle, all tests
./gradlew check                 # checkstyle + JUnit 5 tests (`test`) + Spock specs (`spockTest`)
./gradlew test                  # JUnit 5 tests only (Java, src/test/java)
./gradlew spockTest             # Spock specs only (Groovy, src/test/groovy, run on the JUnit 4 runner)
./gradlew test --tests 'com.github.gilbertotcc.lifx.operations.ListLightsQueryTest'
./gradlew spockTest --tests 'com.github.gilbertotcc.lifx.models.SelectorsSpec'
./gradlew checkstyleMain checkstyleTest
./gradlew test spockTest        # also writes the JaCoCo report (HTML in build/reports/jacocoHtml)
```

CI runs on GitHub Actions: the `Build` job (`.github/workflows/ci.yml`) runs `./gradlew check --continue` on pull
requests to and pushes to `main`, and reports test results and JaCoCo coverage on pull requests. CircleCI
(`.circleci/`) still runs in parallel until it is retired. Workflows reference actions by version tag (`@vX.Y.Z`),
not commit SHA; Dependabot (`.github/dependabot.yml`) updates them weekly in one grouped pull request.

No remote publishing repository is configured; `maven-publish` provides `publishToMavenLocal`.

`*IntegrationTest*` classes are excluded from `test`. `LifxClientTestIntegrationTest` hits the real LIFX cloud and
needs `IT_ACCESS_TOKEN`; no Gradle task currently runs it.

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

- **Operations** (`operations/`): each endpoint is an `Operation<I, O, E>` with its own `*Input` / `*Output` (or
  generic `CommandOutput<T>`) class. Naming: `*Query` for reads, `*Command` for writes. Adding an endpoint means:
  method in `LifxApi`, new Input/Command(+Output) in `operations`, wiring in `LifxClientImpl.createNewClientFor`, and a
  method on `LifxClient`.
- **Error handling**: no exceptions are thrown to callers — every client method returns vavr
  `Either<LifxCallException, T>`. `CallExecutor` turns non-2xx responses into `LifxErrorException` (a
  `LifxCallException`) and I/O failures into `LifxCallException`.
- **HTTP setup**: `LifxClientImpl.createNewClientFor(baseUrl, token)` builds Retrofit with `LifxOkHttpClientFactory`
  (bearer auth + logging interceptor), a `StringConverterFactory` that renders `LightSelector` path params via
  `LightSelectorConverter`, and Jackson using the shared `util.JacksonUtils.OBJECT_MAPPER`. The `baseUrl` overload
  exists for tests (WireMock).
- **Models** (`models/`): Lombok-based DTOs plus the selector hierarchy (`LightSelector`, `CombinableLightSelector`,
  `RandomizableLightSelector`, multi-zone selectors), created via the `Selectors` factory. Custom Jackson
  (de)serializers live in `models/serializer` and `models/deserializer`.

## Enforced rules

ArchUnit tests in `src/test/java/.../arch` fail the build if violated:

- Classes in `..lifx.models` may only depend on `models` itself, `java.lang`, `java.time`, `java.util..`, `java.io`,
  and `io.vavr..` (not Jackson, Retrofit, Lombok runtime types, etc.). Note: `models.serializer`/`deserializer`/
  `converter` are subpackages, so they are not covered by this rule.
- No `System.out`/`System.err`, no generic exceptions thrown, no `java.util.logging` (use SLF4J via Lombok `@Slf4j`).

Style: 2-space indent, 120-char lines, no star imports, braces always required (Checkstyle config in
`config/checkstyle/`). `lombok.config` sets `accessors.fluent = false` (getters are `getX()`).

## Tests

JSON fixtures for request/response bodies are under `src/test/resources/json/`; operation tests share helpers in
`operations/OperationsTestFixtures` and `testutil/TestUtils`. HTTP is mocked with WireMock, JSON compared with
JSONAssert.
