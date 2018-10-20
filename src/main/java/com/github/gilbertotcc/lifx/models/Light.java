package com.github.gilbertotcc.lifx.models;

import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.github.gilbertotcc.lifx.models.deserializer.PowerDeserializer;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Light {

    public static class Color {

        @JsonProperty("hue")
        private double hue;

        @JsonProperty("saturation")
        private double saturation;

        @JsonProperty("kelvin")
        private int kelvin;

        private Color() {}

        public double getHue() {
            return hue;
        }

        public double getSaturation() {
            return saturation;
        }

        public int getKelvin() {
            return kelvin;
        }
    }

    public static class Group {

        @JsonProperty("id")
        private String id;

        @JsonProperty("name")
        private String name;

        private Group() {}

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

    public static class Location {

        @JsonProperty("id")
        private String id;

        @JsonProperty("name")
        private String name;

        private Location() {}

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

    public static class Product {

        public static class Capabilities {

            @JsonProperty("has_color")
            private boolean hasColor;

            @JsonProperty("has_variable_color_temp")
            private boolean hasVariableColorTemperature;

            @JsonProperty("min_kelvin")
            private int minKelvin;

            @JsonProperty("max_kelvin")
            private int maxKelvin;

            @JsonProperty("has_ir")
            private boolean hasIr;

            @JsonProperty("has_chain")
            private boolean hasChain;

            @JsonProperty("has_multizone")
            private boolean hasMultizone;

            private Capabilities() {}

            public boolean hasColor() {
                return hasColor;
            }

            public boolean hasVariableColorTemperature() {
                return hasVariableColorTemperature;
            }

            public int getMinKelvin() {
                return minKelvin;
            }

            public int getMaxKelvin() {
                return maxKelvin;
            }

            public boolean hasIr() {
                return hasIr;
            }

            public boolean hasChain() {
                return hasChain;
            }

            public boolean hasMultizone() {
                return hasMultizone;
            }
        }

        @JsonProperty("name")
        private String name;

        @JsonProperty("company")
        private String company;

        @JsonProperty("identifier")
        private String identifier;

        @JsonProperty("capabilities")
        private Capabilities capabilities;

        private Product() {}

        public String getName() {
            return name;
        }

        public String getCompany() {
            return company;
        }

        public String getIdentifier() {
            return identifier;
        }

        public Capabilities getCapabilities() {
            return capabilities;
        }
    }

    @JsonProperty("id")
    private String id;

    @JsonProperty("uuid")
    private String uuid;

    @JsonProperty("label")
    private String label;

    @JsonProperty("connected")
    private boolean connected;

    @JsonProperty("power")
    @JsonDeserialize(using = PowerDeserializer.class)
    private Power power;

    @JsonProperty("color")
    private Color color;

    @JsonProperty("infrared")
    private String infrared; // FIXME According https://api.developer.lifx.com/docs/list-lights is a string

    @JsonProperty("brightness")
    private double brightness;

    @JsonProperty("group")
    private Group group;

    @JsonProperty("location")
    private Location location;

    @JsonProperty("last_seen")
    private ZonedDateTime lastSeen;

    @JsonProperty("seconds_since_seen")
    private int secondsSinceSeen;

    @JsonProperty("product")
    private Product product;

    private Light() {}

    public String getId() {
        return id;
    }

    public String getUuid() {
        return uuid;
    }

    public String getLabel() {
        return label;
    }

    public boolean isConnected() {
        return connected;
    }

    public Power getPower() {
        return power;
    }

    public Color getColor() {
        return color;
    }

    public String getInfrared() {
        return infrared;
    }

    public double getBrightness() {
        return brightness;
    }

    public Group getGroup() {
        return group;
    }

    public Location getLocation() {
        return location;
    }

    public ZonedDateTime getLastSeen() {
        return lastSeen;
    }

    public int getSecondsSinceSeen() {
        return secondsSinceSeen;
    }

    public Product getProduct() {
        return product;
    }
}
