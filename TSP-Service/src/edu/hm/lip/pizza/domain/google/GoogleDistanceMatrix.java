package edu.hm.lip.pizza.domain.google;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleDistanceMatrix {

    @JsonProperty("destination_addresses")
    private List<String> destinationAddresses;

    @JsonProperty("origin_addresses")
    private List<String> originAddresses;

    @JsonProperty("rows")
    private List<GoogleDistanceRow> distanceRows;

    public List<String> getDestinationAddresses() {
        return destinationAddresses;
    }

    public void setDestinationAddresses(List<String> destinationAddresses) {
        this.destinationAddresses = destinationAddresses;
    }

    public List<String> getOriginAddresses() {
        return originAddresses;
    }

    public void setOriginAddresses(List<String> originAddresses) {
        this.originAddresses = originAddresses;
    }

    public List<GoogleDistanceRow> getDistanceRows() {
        return distanceRows;
    }

    public void setDistanceRows(List<GoogleDistanceRow> distanceRows) {
        this.distanceRows = distanceRows;
    }

    @Override
    public String toString() {
        return "GoogleDistanceMatrix [destinationAddresses="
                + destinationAddresses + ", originAddresses=" + originAddresses
                + ", distanceRows=" + distanceRows + "]";
    }

}
