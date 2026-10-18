---
name: lifx-operation
description: >-
  Add a new LIFX HTTP API endpoint to this lifx-client library, or change an existing operation's request, response
  or signature: the Retrofit method in LifxApi, the *Command/*Query class with its *Input/*Output records, the models,
  the LifxClient/LifxClientImpl wiring and the WireMock tests. Checks the endpoint against the LIFX OpenAPI reference
  at api.developer.lifx.com first.
when_to_use: >-
  The user asks to add, support, implement, update or fix a LIFX endpoint, effect or API call in this client, e.g.
  "add Effects Off", "support the Clean endpoint", "implement the Morph effect", "implement the Sunset effect from
  the LIFX API", "add List Scenes / Activate Scene", "the scenes response changed", "SetLightsStateCommand sends the
  wrong field". Missing endpoints include Move, Morph, Flame, Clouds, Sunrise and Sunset effects, Effects Off, List
  Scenes, Activate Scene and Clean. Invoke it before exploring the code or delegating to a subagent. Not for build,
  dependency, CI or README work.
argument-hint: "<endpoint title or reference slug>"
allowed-tools:
  - WebFetch(domain:api.developer.lifx.com)
  - Bash(./gradlew check*)
  - Bash(./gradlew test *)
---

# Add or change a LIFX operation

Paths below are relative to `src/main/java/com/github/gilbertotcc/lifx/` unless they start with `src/`.

## 1. Scope

1. The endpoint is `$ARGUMENTS`. If it is empty, ask which endpoint before doing anything else.
2. Decide the case: a **new** endpoint (no method in `api/LifxApi.java` hits its path) or a **change** to an existing
   operation's request, response or signature. Grep `LifxApi` for the path to tell them apart.

## 2. Spec first

1. Your first WebFetch must be `https://api.developer.lifx.com/llms.txt`. Take the page URL from that index; never
   build it from the endpoint name (Sunset is `sunrise-effect-copy.md`, Sunrise is `clouds-effect-copy.md`, Clouds is
   `flame-effect-copy.md`).
2. Follow [spec-lookup.md](spec-lookup.md) to extract method, path, parameters, request body and `2XX` schema from
   that page.
3. For a change, compare the spec with the current `LifxApi` method, its body/response models and the `*Input` record.
   Report every mismatch (missing field, wrong type or unit, wrong verb or path) to the user before editing.

## 3. New operation, in dependency order

Follow the [operations rule](../../rules/operations.md) for class layout, logging, Javadoc and naming.

1. **Models** in `models/`: the request body and any new response type. Reuse `ResultsDto<Result>` for writes that
   return `results[]`. Add custom (de)serializers in `models/serializer` or `models/deserializer` only when Jackson
   annotations can't express the wire format. A new package needs a `@NullMarked` `package-info.java`.
2. **`api/LifxApi`**: one method with the HTTP verb annotation, a `/v1/...` path, `@Path("selector") LightSelector` only
   when the path has `{selector}` (other path params, such as Activate Scene's `{scene_uuid}`, get their own `@Path`
   with a plain type like `UUID`), `@Query` or `@Body` parameters, and a `Call<...>` type matching the `2XX` schema.
3. **Operation** in `operations/`: the `*Input` record, the `*Command` (write) or `*Query` (read) implementing
   `Operation<I, O>`, and an `*Output` record only when `CommandOutput<T>` doesn't fit.
4. **`LifxClient`**: a method `Either<LifxCallException, O> name(NameInput input)`.
5. **`impl/LifxClientImpl`**: it uses a private Lombok `@RequiredArgsConstructor`, so constructor arguments follow
   field order.
   1. Add a `private final` field at the end of the `// Operations` block.
   2. Add `new NameCommand(lifxApi)` (or `new NameQuery(lifxApi)` for a read) at the same position in the
      `new LifxClientImpl(...)` call in `createNewClientFor(baseUrl, accessToken)`, so the field list and the argument
      list stay in the same order.
   3. Add an `@Override` method that delegates to `field.execute(input)`.
6. **Tests and fixtures** per [testing.md](testing.md).

## 4. Changing an existing operation

1. Change the model, the `LifxApi` method, the `*Input`/`*Output`, `LifxClient`/`LifxClientImpl` and the test in the
   same commit. Leave the other operations alone.
2. Update JSON fixtures under `src/test/resources/json/` when the wire shape changes. Never loosen or delete an
   assertion to make a test pass.
3. Renaming or removing a public type, method, accessor or record component, or changing a signature, is breaking.
   Use a `feat!:` or `refactor!:` commit with a `BREAKING CHANGE:` footer (see commit `1b7ace4`), and say so in your
   summary to the user.

## 5. Verify

1. Run the operation's test: `./gradlew test --tests 'com.github.gilbertotcc.lifx.operations.<Name>Test'`.
2. Run `./gradlew check` (Error Prone, NullAway, `-Werror`, Checkstyle, ArchUnit, JUnit and Spock). Fix every
   finding; suppress one only when unavoidable, with a comment giving the reason.
3. Don't run `./gradlew integrationTest` unless the user asks: it switches real lights.
4. Report the endpoint, the files changed, any spec mismatches found and whether the change is breaking.
