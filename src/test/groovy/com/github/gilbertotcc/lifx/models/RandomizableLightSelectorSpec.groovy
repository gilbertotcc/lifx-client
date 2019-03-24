package com.github.gilbertotcc.lifx.models

import spock.lang.Specification

import static com.github.gilbertotcc.lifx.models.Selectors.All

class RandomizableLightSelectorSpec extends Specification {

  def "random light selectors should create a random light selector"() {
    given:
    def all = All()

    when:
    def random = all.random()

    then:
    random.identifier() == "all:random"
  }
}
