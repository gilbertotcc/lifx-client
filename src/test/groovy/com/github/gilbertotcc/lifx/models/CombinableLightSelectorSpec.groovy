package com.github.gilbertotcc.lifx.models

import spock.lang.Specification

class CombinableLightSelectorSpec extends Specification {

  def "combine light selectors should create a combined light selector"() {
    given:
    def selector1 = new Selectors.IdSelector("id1")
    def selector2 = new Selectors.IdSelector("id2")
    def selector3 = new Selectors.IdSelector("id3")

    when:
    CombinedLightSelector combinedSelector =
      selector1
        .and(selector2)
        .and(selector3)

    then:
    combinedSelector.identifier() == "id:id1,id:id2,id:id3"
  }
}
