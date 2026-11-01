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
  (`ListLightsQuery`), otherwise the identifying input (`ValidateColorQuery` logs the color string). Then it calls one
  of the `OperationCalls` helpers with the `LifxApi` call: `executeCommand` for writes, `executeQuery` for reads,
  `executeListQuery` for reads that return a list. See `ListLightsQuery`, `ValidateColorQuery` and `SetLightsStateCommand`.
- Input: a record. A `{selector}` path param is a `LightSelector` (`TransitToNextStateInput`); other params use plain
  types (`ValidateColorInput` holds a `String`; a `{scene_uuid}` would be a `UUID`). Endpoints such as List Scenes
  have no selector at all. Exception: an input that is the wire body extends its model DTO
  (`SetLightsStatesInput extends LightsStatesDto`).
- Output: operations return `LifxResult<O>` and need no `*Output` record. Writes use `LifxResult<List<Result>>`, or
  `LifxResult<List<OperationResult>>` for `SetLightsStatesCommand`, through `OperationCalls.executeCommand`; a
  body-less 2xx (e.g. a `fast` request answered with 202) becomes `LifxResult.Success.None`. Reads return
  `LifxResult<T>` with `T` the `LifxApi` body type (`LifxResult<List<Light>>`, `LifxResult<Color>`), through
  `executeQuery`; they never yield `Success.None`, since a body-less 2xx becomes a `Failure`. `executeListQuery`
  also checks a list body for null elements (a `Failure` with an `IOException` cause) and wraps it with
  `List.copyOf`.
- Body models (`models/`): records with `@JsonProperty` and `@Nullable` for optional fields (`Cycle`, `Result`).
  Custom (de)serializers go in `models/serializer` and `models/deserializer` (`DirectionSerializer`,
  `ResultStatusDeserializer`), wired with `@JsonSerialize` / `@JsonDeserialize`. `ModelsDependencyTest` forbids
  Jackson classes in `models` itself, apart from annotations.
- `execute` Javadoc: one summary sentence starting "Synchronously ...", then `@return`. Writes document all three
  outcomes:

  ```java
  * @return {@link LifxResult.Success.Some} with the per-light results, {@link LifxResult.Success.None} if a
  *     successful response has no body, or {@link LifxResult.Failure} with a call exception for HTTP or I/O
  *     failures
  ```

  Reads word `@return` as "{@link LifxResult.Success.Some} with the parsed color, or {@link LifxResult.Failure} with
  a call exception for HTTP or I/O failures or a successful response without a body. Never
  {@link LifxResult.Success.None}" (`ValidateColorQuery`); `ListLightsQuery` adds "an unmodifiable copy of" and
  "a response list with a null element". Add `@throws` only when the mapping of the body can actually throw.
- `LifxClient` and `LifxClientImpl` change together: the interface method returns `LifxResult<O>`;
  the impl gets a field under `// Operations`, a delegating `@Override`, and a `new XxxCommand(lifxApi)` /
  `new XxxQuery(lifxApi)` argument in `createNewClientFor`. Field order must equal constructor argument order
  (private Lombok `@RequiredArgsConstructor`).
- For a whole new endpoint or a signature change, follow the `lifx-operation` skill
  (`.claude/skills/lifx-operation/SKILL.md`).
