# AGENTS.md

This file provides guidance to AI coding agents working with code in this repository.

## Project

Java 17+ client library for the [LIFX HTTP API](https://api.developer.lifx.com/), built with Gradle (wrapper pins
Gradle 9.8.1). Published as `com.github.gilbertotcc.lifx:lifx-client`; the version lives in `gradle.properties`.
Compiled with a JDK 25 toolchain for `--release 17`.

Build logic, toolchains, CI, Dependabot and dependency pins: `.claude/rules/build-and-ci.md` (loads automatically when
you open build files).

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

`integrationTest` hits the real LIFX cloud with `LIFX_PAT` and switches a real light; never run it unless asked.
`check` (and CI) only compiles and checkstyles it.

Markdown lint and link checks: `.claude/rules/markdown.md`.

## Architecture

Request flow: `LifxClient` (public interface, factory `LifxClient.newLifxClientFor(token)`) → `impl.LifxClientImpl` →
one `operations.*Command`/`*Query` per endpoint → Retrofit `api.LifxApi` → wrapped by `impl.CallExecutor`.

- **Operations** (`operations/`): each endpoint is an `Operation<I, O>` with its own `*Input` / `*Output` (or
  the sealed `CommandOutput<T>`: `SomeOutput` / `EmptyOutput`) class. Naming: `*Query` for reads, `*Command` for
  writes. Adding an endpoint means: method in `LifxApi`, new Input/Command(+Output) in `operations`, wiring in
  `LifxClientImpl.createNewClientFor`, and a method on `LifxClient`. Full procedure:
  `.claude/skills/lifx-operation/SKILL.md` (plain Markdown, usable by any agent); file conventions:
  `.claude/rules/operations.md`.
- **Error handling**: no exceptions are thrown to callers — every client method returns vavr
  `Either<LifxCallException, T>`. `CallExecutor` turns non-2xx responses into a `LifxCallException` whose cause is a
  `LifxErrorException` (carrying the `LifxErrorType`), and I/O failures into `LifxCallException`. A successful
  `execute()` yields an `Option` that is empty for a body-less 2xx (commands map it to `EmptyOutput`);
  `executeExpectingBody()`, used by queries, turns that case into a `LifxCallException`.
- **HTTP setup**: `LifxClientImpl.createNewClientFor(baseUrl, token)` builds Retrofit with `LifxOkHttpClientFactory`
  (bearer auth + logging interceptor), a `StringConverterFactory` that renders `LightSelector` path params via
  `LightSelectorConverter`, and Jackson 3 using the shared `util.JacksonUtils.OBJECT_MAPPER`. Retrofit has no Jackson
  3 converter, so `impl.JacksonConverterFactory` is in-house; it wraps Jackson's unchecked `JacksonException` in
  `IOException` so `CallExecutor` still reports (de)serialization failures as `LifxCallException`, and converts an
  empty body to `null`, since Retrofit only skips the converter for 204/205. The `baseUrl`
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

## Agent workflow

- Run Gradle as `./gradlew <task> --console=plain`. When Claude Code runs it (env `CLAUDECODE` set), test-logger hides
  passed tests, so a green `check` prints about 1 KB and a failure shows the test, assertion and line. Don't add `-q`:
  it also hides the failing test's details.
- Run the targeted `--tests` first, then the full `./gradlew check --console=plain` once before finishing.
- Commits: Conventional Commits (`type(scope): summary`); breaking changes use `!` and a `BREAKING CHANGE:` footer. A
  `Co-Authored-By` trailer that the harness requires is kept: the third-party `caveman-commit` skill's "no AI
  attribution" rule gives way to it.

## Compact instructions

When compacting, keep: the endpoint or task in progress, spec mismatches found, files changed, failing test names with
their first error line, and decisions still open. Drop passing test output and file contents that can be re-read.
