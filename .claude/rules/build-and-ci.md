---
paths:
  - "build.gradle"
  - "settings.gradle"
  - "gradle.properties"
  - "gradle/**"
  - "gradlew*"
  - ".github/**"
  - ".sdkmanrc"
  - "config/**"
  - "lombok.config"
---

# Build, CI and dependencies

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
