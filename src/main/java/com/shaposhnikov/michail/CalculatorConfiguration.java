package com.shaposhnikov.michail;

import io.dropwizard.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CalculatorConfiguration extends Configuration {

    private int defaultValue = 0;

    @JsonProperty
    public Integer getDefaultValue() {
        return defaultValue;
    }

    @JsonProperty
    public void setDefaultValue(int value) { this.defaultValue = value; }
}