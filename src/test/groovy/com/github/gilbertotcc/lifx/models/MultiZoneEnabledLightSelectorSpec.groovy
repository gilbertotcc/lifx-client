package com.github.gilbertotcc.lifx.models

import spock.lang.Specification

import static com.github.gilbertotcc.lifx.models.Selectors.all

class MultiZoneEnabledLightSelectorSpec extends Specification {

  def "create selector for multi zone should correctly create the selector"() {
    given:
    def all = all()

    when:
    def multiZoneSelector = all.onZones(1, 2, 5)

    then:
    multiZoneSelector.identifier() == "all|1|2|5"
  }

  def "create selector for multi zone range should correctly create the selector"() {
    given:
    def all = all()

    when:
    def multiZoneSelector = all.onZonesRange(1, 5)

    then:
    multiZoneSelector.identifier() == "all|1-5"
  }
}
