package edu.hm.lip.pizza.domain.google;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GoogleDistanceRow {

    @JsonProperty("elements")
    List<GoogleDistanceElement> elements;

    public List<GoogleDistanceElement> getElements() {
        return elements;
    }

    public void setElements(List<GoogleDistanceElement> elements) {
        this.elements = elements;
    }

    @Override
    public String toString() {
        return "GoogleDistanceRow [elements=" + elements + "]";
    }

}
