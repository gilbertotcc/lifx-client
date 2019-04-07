package com.github.gilbertotcc.lifx.models

import spock.lang.Specification
import spock.lang.Unroll

import static com.github.gilbertotcc.lifx.models.Selectors.*

class SelectorsSpec extends Specification {

  @Unroll
  def "selector identifiers should be correctly generated"() {
    expect:
    selector.identifier() == identifier

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
