package edu.hm.lip.pizza.domain.google;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleDistanceElement {

    GoogleDistance distance;
    GoogleDurration duration;
    String status;

    public GoogleDistance getDistance() {
        return distance;
    }

    public GoogleDurration getDuration() {
        return duration;
    }

    public String getStatus() {
        return status;
    }

    public void setDistance(GoogleDistance distance) {
        this.distance = distance;
    }

    public void setDuration(GoogleDurration duration) {
        this.duration = duration;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "GoogleDistanceRow [distance=" + distance + ", duration="
                + duration + ", status=" + status + "]";
    }

}
