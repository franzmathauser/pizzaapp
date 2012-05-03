package edu.hm.lip.pizza.domain.google;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleDurration {

    @JsonProperty("text")
    String description;

    @JsonProperty("value")
    String value;

    public String getDescription() {
        return description;
    }

    public String getValue() {
        return value;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "GoogleDurration [description=" + description + ", value="
                + value + "]";
    }

}
