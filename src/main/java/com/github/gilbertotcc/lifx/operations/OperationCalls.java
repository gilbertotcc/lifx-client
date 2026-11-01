package com.github.gilbertotcc.lifx.operations;

import com.github.gilbertotcc.lifx.LifxResult;
import com.github.gilbertotcc.lifx.models.ResultsDto;
import com.github.gilbertotcc.lifx.exception.LifxCallException;
import com.github.gilbertotcc.lifx.impl.CallExecutor;
import io.vavr.control.Either;
import retrofit2.Call;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * Shared mapping from {@link CallExecutor} outcomes to {@link LifxResult}, so each operation only builds its call.
 */
final class OperationCalls {

  private OperationCalls() {
  }

  /**
   * Executes a command call whose body, when present, carries per-item results.
   *
   * @return {@link LifxResult.Success.Some} with the results, {@link LifxResult.Success.None} for a successful
   *     response without a body, or {@link LifxResult.Failure} for HTTP or I/O failures
   */
  static <R> LifxResult<List<R>> executeCommand(Call<ResultsDto<R>> call) {
    return new CallExecutor<>(call).execute()
      .<LifxResult<List<R>>>map(body -> body.fold(
        LifxResult.Success.None::new,
        response -> new LifxResult.Success.Some<>(response.results())))
      .getOrElseGet(LifxResult.Failure::new);
  }

  /**
   * Executes a query call that must return a body.
   *
   * @return {@link LifxResult.Success.Some} with the body, or {@link LifxResult.Failure} for HTTP or I/O failures
   *     or a successful response without a body
   */
  static <T> LifxResult<T> executeQuery(Call<T> call) {
    return toLifxResult(new CallExecutor<>(call).executeExpectingBody());
  }

  /**
   * Executes a query call that must return a list body.
   *
   * @return {@link LifxResult.Success.Some} with an unmodifiable copy of the list, or {@link LifxResult.Failure} for
   *     HTTP or I/O failures, a successful response without a body, or a response list with a null element
   */
  static <E> LifxResult<List<E>> executeListQuery(Call<List<E>> call) {
    return toLifxResult(new CallExecutor<>(call).executeExpectingBody()
      .flatMap(list -> list.stream().anyMatch(Objects::isNull)
        ? Either.<LifxCallException, List<E>>left(
            LifxCallException.with(call, new IOException("Response list contains a null element")))
        : Either.right(List.copyOf(list))));
  }

  private static <T> LifxResult<T> toLifxResult(Either<LifxCallException, T> result) {
    return result.<LifxResult<T>>map(LifxResult.Success.Some::new).getOrElseGet(LifxResult.Failure::new);
  }
}
