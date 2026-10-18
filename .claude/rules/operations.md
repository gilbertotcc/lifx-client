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
  `execute` calls `log.info` once (selector `identifier()` plus key inputs), then
  `new CallExecutor<>(lifxApi.x(...)).execute().map(...)`. See `ListLightsQuery` and `SetLightsStateCommand`.
- Input: a record, with `LightSelector` for selector params (`TransitToNextStateInput`). Exception: an input that is
  the wire body extends its model DTO (`SetLightsStatesInput extends LightsStatesDto`).
- Output: writes reuse `CommandOutput<List<Result>>`, or `CommandOutput<List<OperationResult>>` for
  `SetLightsStatesCommand`; unwrap `ResultsDto` with `response.results()`. Reads get their own record, with
  `List.copyOf` in the compact constructor for lists (`ListLightsOutput`).
- Body models (`models/`): records with `@JsonProperty` and `@Nullable` for optional fields (`Cycle`, `Result`).
  Custom (de)serializers go in `models/serializer` and `models/deserializer` (`DirectionSerializer`,
  `ResultStatusDeserializer`), wired with `@JsonSerialize` / `@JsonDeserialize`. `ModelsDependencyTest` forbids
  Jackson classes in `models` itself, apart from annotations.
- `execute` Javadoc: one summary sentence starting "Synchronously ...", then `@return`. Writes (and
  `ListLightsQuery`) document the Either and the NPE on a body-less response:

  ```java
  * @return per-light results on the right, or a call exception on the left for HTTP or I/O failures
  * @throws NullPointerException if a successful response has no body
  ```

  `ListLightsQuery` words `@return` as "the light list on the right, ..." and adds "or its list contains a null
  element" to the `@throws`. `ValidateColorQuery` has no `@throws`: its `.map(ValidateColorOutput::new)` accepts a null
  body, so `@return` says "the parsed color on the right, possibly null if a successful response has no body, or a
  call exception on the left ...". Match the wording to what the `.map` does with a null body.
- `LifxClient` and `LifxClientImpl` change together: the interface method returns `Either<LifxCallException, O>`;
  the impl gets a field under `// Operations`, a delegating `@Override`, and a `new XxxCommand(lifxApi)` argument in
  `createNewClientFor`. Field order must equal constructor argument order (private Lombok
  `@RequiredArgsConstructor`).
- For a whole new endpoint or a signature change, follow the `lifx-operation` skill
  (`.claude/skills/lifx-operation/SKILL.md`).
