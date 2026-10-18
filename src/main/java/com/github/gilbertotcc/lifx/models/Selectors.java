// CHECKSTYLE:OFF

package com.github.gilbertotcc.lifx.models;

import lombok.AllArgsConstructor;
import lombok.experimental.UtilityClass;

// The deprecated aliases are not annotated with @InlineMe: that would put error_prone_annotations on the API
@SuppressWarnings("InlineMeSuggester")
@UtilityClass
public class Selectors {

  public static AllSelector all() {
    return new AllSelector();
  }

  public static LabelSelector byLabel(String label) {
    return new LabelSelector(label);
  }

  public static IdSelector byId(String id) {
    return new IdSelector(id);
  }

  public static GroupIdSelector byGroupId(String groupId) {
    return new GroupIdSelector(groupId);
  }

  public static GroupSelector byGroup(String group) {
    return new GroupSelector(group);
  }

  public static LocationIdSelector byLocationId(String locationId) {
    return new LocationIdSelector(locationId);
  }

  public static LocationSelector byLocation(String location) {
    return new LocationSelector(location);
  }

  public static SceneIdSelector bySceneId(String sceneId) {
    return new SceneIdSelector(sceneId);
  }

  @Deprecated
  public static AllSelector All() {
    return all();
  }

  @Deprecated
  public static LabelSelector LabelSelector(String label) {
    return byLabel(label);
  }

  @Deprecated
  public static IdSelector IdSelector(String id) {
    return byId(id);
  }

  @Deprecated
  public static GroupIdSelector GroupIdSelector(String groupId) {
    return byGroupId(groupId);
  }

  @Deprecated
  public static GroupSelector GroupSelector(String group) {
    return byGroup(group);
  }

  @Deprecated
  public static LocationIdSelector LocationIdSelector(String locationId) {
    return byLocationId(locationId);
  }

  @Deprecated
  public static LocationSelector LocationSelector(String location) {
    return byLocation(location);
  }

  @Deprecated
  public static SceneIdSelector SceneIdSelector(String sceneId) {
    return bySceneId(sceneId);
  }

  @AllArgsConstructor
  public static final class AllSelector
    implements LightSelector, RandomizableLightSelector, MultiZoneEnabledLightSelector {

    @Override
    public String identifier() {
      return "all";
    }
  }

  @AllArgsConstructor
  public static final class LabelSelector
    implements LightSelector, CombinableLightSelector, MultiZoneEnabledLightSelector {

    private String label;

    @Override
    public String identifier() {
      return "label:%s".formatted(label);
    }
  }

  @AllArgsConstructor
  public static final class IdSelector
    implements LightSelector, CombinableLightSelector, MultiZoneEnabledLightSelector {

    private String id;

    @Override
    public String identifier() {
      return "id:%s".formatted(id);
    }
  }

  @AllArgsConstructor
  public static final class GroupIdSelector
    implements LightSelector, RandomizableLightSelector, CombinableLightSelector, MultiZoneEnabledLightSelector {

    private String groupId;

    @Override
    public String identifier() {
      return "group_id:%s".formatted(groupId);
    }
  }

  @AllArgsConstructor
  public static final class GroupSelector
    implements LightSelector, RandomizableLightSelector, CombinableLightSelector, MultiZoneEnabledLightSelector {

    private String group;

    @Override
    public String identifier() {
      return "group:%s".formatted(group);
    }
  }

  @AllArgsConstructor
  public static final class LocationIdSelector
    implements LightSelector, RandomizableLightSelector, CombinableLightSelector, MultiZoneEnabledLightSelector {

    private String locationId;

    @Override
    public String identifier() {
      return "location_id:%s".formatted(locationId);
    }
  }

  @AllArgsConstructor
  public static final class LocationSelector
    implements LightSelector, RandomizableLightSelector, CombinableLightSelector, MultiZoneEnabledLightSelector {

    private String location;

    @Override
    public String identifier() {
      return "location:%s".formatted(location);
    }
  }

  @AllArgsConstructor
  public static final class SceneIdSelector
    implements LightSelector, RandomizableLightSelector, CombinableLightSelector, MultiZoneEnabledLightSelector {

    private String sceneId;

    @Override
    public String identifier() {
      return "scene_id:%s".formatted(sceneId);
    }
  }
}
