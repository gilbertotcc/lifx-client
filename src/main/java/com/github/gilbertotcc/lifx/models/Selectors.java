// CHECKSTYLE:OFF

package com.github.gilbertotcc.lifx.models;

import static java.lang.String.format;

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
      return format("label:%s", label);
    }
  }

  @AllArgsConstructor
  public static final class IdSelector
    implements LightSelector, CombinableLightSelector, MultiZoneEnabledLightSelector {

    private String id;

    @Override
    public String identifier() {
      return format("id:%s", id);
    }
  }

  @AllArgsConstructor
  public static final class GroupIdSelector
    implements LightSelector, RandomizableLightSelector, CombinableLightSelector, MultiZoneEnabledLightSelector {

    private String groupId;

    @Override
    public String identifier() {
      return format("group_id:%s", groupId);
    }
  }

  @AllArgsConstructor
  public static final class GroupSelector
    implements LightSelector, RandomizableLightSelector, CombinableLightSelector, MultiZoneEnabledLightSelector {

    private String group;

    @Override
    public String identifier() {
      return format("group:%s", group);
    }
  }

  @AllArgsConstructor
  public static final class LocationIdSelector
    implements LightSelector, RandomizableLightSelector, CombinableLightSelector, MultiZoneEnabledLightSelector {

    private String locationId;

    @Override
    public String identifier() {
      return format("location_id:%s", locationId);
    }
  }

  @AllArgsConstructor
  public static final class LocationSelector
    implements LightSelector, RandomizableLightSelector, CombinableLightSelector, MultiZoneEnabledLightSelector {

    private String location;

    @Override
    public String identifier() {
      return format("location:%s", location);
    }
  }

  @AllArgsConstructor
  public static final class SceneIdSelector
    implements LightSelector, RandomizableLightSelector, CombinableLightSelector, MultiZoneEnabledLightSelector {

    private String sceneId;

    @Override
    public String identifier() {
      return format("scene_id:%s", sceneId);
    }
  }
}
