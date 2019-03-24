package com.github.gilbertotcc.lifx.models

import spock.lang.Specification

import static com.github.gilbertotcc.lifx.models.Selectors.$.*

class SelectorsSpec extends Specification {

  def "selector identifiers should be correctly generated"() {
    expect:
    selector.getIdentifier() == identifier

    where:
    selector                         | identifier
    All()                            | "all"
    LabelSelector("label")           | "label:label"
    IdSelector("id")                 | "id:id"
    GroupIdSelector("groupId")       | "group_id:groupId"
    GroupSelector("group")           | "group:group"
    LocationIdSelector("locationId") | "location_id:locationId"
    LocationSelector("location")     | "location:location"
    SceneIdSelector("sceneId")       | "scene_id:sceneId"
  }
}
