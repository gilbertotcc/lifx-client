// CHECKSTYLE:OFF

package com.github.gilbertotcc.lifx.models;

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

  public record AllSelector()
    implements RandomizableLightSelector, MultiZoneEnabledLightSelector {

    @Override
    public String identifier() {
      return "all";
    }
  }

  public record LabelSelector(String label)
    implements CombinableLightSelector, MultiZoneEnabledLightSelector {

    /**
     * Renders {@code label:} followed by the supplied value without escaping or validation.
     */
    @Override
    public String identifier() {
      return "label:%s".formatted(label);
    }
  }

  public record IdSelector(String id)
    implements CombinableLightSelector, MultiZoneEnabledLightSelector {

    /**
     * Renders {@code id:} followed by the supplied value without escaping or validation.
     */
    @Override
    public String identifier() {
      return "id:%s".formatted(id);
    }
  }

  public record GroupIdSelector(String groupId)
    implements RandomizableLightSelector, CombinableLightSelector, MultiZoneEnabledLightSelector {

    /**
     * Renders {@code group_id:} followed by the supplied value without escaping or validation.
     */
    @Override
    public String identifier() {
      return "group_id:%s".formatted(groupId);
    }
  }

  public record GroupSelector(String group)
    implements RandomizableLightSelector, CombinableLightSelector, MultiZoneEnabledLightSelector {

    /**
     * Renders {@code group:} followed by the supplied value without escaping or validation.
     */
    @Override
    public String identifier() {
      return "group:%s".formatted(group);
    }
  }

  public record LocationIdSelector(String locationId)
    implements RandomizableLightSelector, CombinableLightSelector, MultiZoneEnabledLightSelector {

    /**
     * Renders {@code location_id:} followed by the supplied value without escaping or validation.
     */
    @Override
    public String identifier() {
      return "location_id:%s".formatted(locationId);
    }
  }

  public record LocationSelector(String location)
    implements RandomizableLightSelector, CombinableLightSelector, MultiZoneEnabledLightSelector {

    /**
     * Renders {@code location:} followed by the supplied value without escaping or validation.
     */
    @Override
    public String identifier() {
      return "location:%s".formatted(location);
    }
  }

  public record SceneIdSelector(String sceneId)
    implements RandomizableLightSelector, CombinableLightSelector, MultiZoneEnabledLightSelector {

    /**
     * Renders {@code scene_id:} followed by the supplied value without escaping or validation.
     */
    @Override
    public String identifier() {
      return "scene_id:%s".formatted(sceneId);
    }
  }
}
