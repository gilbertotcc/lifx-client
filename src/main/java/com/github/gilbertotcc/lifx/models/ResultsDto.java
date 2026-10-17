package com.github.gilbertotcc.lifx.models;

import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResultsDto<T> {

  @JsonProperty("results")
  private List<T> results = Collections.emptyList();
}
