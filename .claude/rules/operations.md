---
paths:
  - "src/main/java/com/github/gilbertotcc/lifx/operations/**"
  - "src/main/java/com/github/gilbertotcc/lifx/api/LifxApi.java"
  - "src/main/java/com/github/gilbertotcc/lifx/impl/LifxClientImpl.java"
  - "src/main/java/com/github/gilbertotcc/lifx/LifxClient.java"
  - "src/test/java/com/github/gilbertotcc/lifx/operations/**"
---

# Operation conventions

- Naming: `*Query` for reads, `*Command` for writes, with `*Input` / `*Output` beside them.
- Class shape: `@Slf4j @RequiredArgsConstructor`, `private final LifxApi lifxApi`, `implements Operation<I, O>`.
  `execute` calls `log.info` once with the key inputs: the selector's `identifier()` when the endpoint has one
  (`ListLightsQuery`), otherwise the identifying input (`ValidateColorQuery` logs the color string). Then it runs
  `new CallExecutor<>(lifxApi.x(...)).execute().map(...)` for writes, or `.executeExpectingBody().map(...)` for reads.
  See `ListLightsQuery` and `SetLightsStateCommand`.
- Input: a record. A `{selector}` path param is a `LightSelector` (`TransitToNextStateInput`); other params use plain
  types (`ValidateColorInput` holds a `String`; a `{scene_uuid}` would be a `UUID`). Endpoints such as List Scenes
  have no selector at all. Exception: an input that is the wire body extends its model DTO
  (`SetLightsStatesInput extends LightsStatesDto`).
- Output: writes reuse the sealed `CommandOutput<List<Result>>`, or `CommandOutput<List<OperationResult>>` for
  `SetLightsStatesCommand`. `execute()` yields an `Option` (empty for a body-less 2xx, e.g. a `fast` request answered
  with 202), mapped with
  `.map(optionalResponse -> optionalResponse.fold(CommandOutput.EmptyOutput::new, response -> new
  CommandOutput.SomeOutput<>(response.results())))`. Reads get their own record, with `List.copyOf` in the compact
  constructor for lists (`ListLightsOutput`); `executeExpectingBody()` turns a body-less 2xx into a
  `LifxCallException`.
- Body models (`models/`): records with `@JsonProperty` and `@Nullable` for optional fields (`Cycle`, `Result`).
  Custom (de)serializers go in `models/serializer` and `models/deserializer` (`DirectionSerializer`,
  `ResultStatusDeserializer`), wired with `@JsonSerialize` / `@JsonDeserialize`. `ModelsDependencyTest` forbids
  Jackson classes in `models` itself, apart from annotations.
- `execute` Javadoc: one summary sentence starting "Synchronously ...", then `@return`. Writes document the Either
  and the `EmptyOutput` on a body-less response:

  ```java
  * @return per-light results on the right (an {@code EmptyOutput} if a successful response has no body),
  *     or a call exception on the left for HTTP or I/O failures
  ```

  Reads word `@return` as "the light list on the right, or a call exception on the left for HTTP or I/O failures or
  a successful response without a body" (`ValidateColorQuery`: "the parsed color ..."). `ListLightsQuery` also has
  `@throws NullPointerException if the response list contains a null element`, from `List.copyOf`. Match the
  wording to what the output constructor does with the body.
- `LifxClient` and `LifxClientImpl` change together: the interface method returns `Either<LifxCallException, O>`;
  the impl gets a field under `// Operations`, a delegating `@Override`, and a `new XxxCommand(lifxApi)` /
  `new XxxQuery(lifxApi)` argument in `createNewClientFor`. Field order must equal constructor argument order
  (private Lombok `@RequiredArgsConstructor`).
- For a whole new endpoint or a signature change, follow the `lifx-operation` skill
  (`.claude/skills/lifx-operation/SKILL.md`).
