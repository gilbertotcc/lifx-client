package com.github.gilbertotcc.lifx.models;

import static java.lang.String.format;

public interface LightsSelector {

  String getIdentifier();

  static LightsSelector all() {
    return () -> "all";
  }

  static LightsSelector byLabel(String label) {
    return () -> format("label:%s", label);
  }

  static LightsSelector byId(final String lightId) {
    return () -> format("id:%s", lightId);
  }

  static LightsSelector byGroupId(String groupId) {
    return () -> format("group_id:%s", groupId);
  }

  static LightsSelector byGroup(String group) {
    return () -> format("group:%s", group);
  }

  static LightsSelector byLocationId(String locationId) {
    return () -> format("location_id:%s", locationId);
  }

  static LightsSelector byLocation(String location) {
    return () -> format("location:%s", location);
  }

  static LightsSelector bySceneId(String sceneId) {
    return () -> format("scene_id:%s", sceneId);
  }
}
