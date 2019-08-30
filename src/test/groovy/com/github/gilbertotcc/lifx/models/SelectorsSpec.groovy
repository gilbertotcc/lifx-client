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
    selector                   | identifier
    all()                      | "all"
    byLabel("label")           | "label:label"
    byId("id")                 | "id:id"
    byGroupId("groupId")       | "group_id:groupId"
    byGroup("group")           | "group:group"
    byLocationId("locationId") | "location_id:locationId"
    byLocation("location")     | "location:location"
    bySceneId("sceneId")       | "scene_id:sceneId"
  }

  @Unroll
  def "deprecated selector factory methods should behave like the new ones"() {
    expect:
    selector.identifier() == deprecatedSelector.identifier()

    where:
    selector                   | deprecatedSelector
    all()                      | All()
    byLabel("label")           | LabelSelector("label")
    byId("id")                 | IdSelector("id")
    byGroupId("groupId")       | GroupIdSelector("groupId")
    byGroup("group")           | GroupSelector("group")
    byLocationId("locationId") | LocationIdSelector("locationId")
    byLocation("location")     | LocationSelector("location")
    bySceneId("sceneId")       | SceneIdSelector("sceneId")
  }
}
