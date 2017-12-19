package com.example.andrus.projectnam.models;

import java.util.List;

public class MapsResponse {
    public List<String> destinationAddresses;
    public List<String> originAddresses;
    public List<Row> rows;
    public String status;


    private Row getFirstRow() {
        return rows.get(0);
    }

    private Element getFirstElementFromFirstRow() {
        return getFirstRow().elements.get(0);
    }

    private Duration getDurationFromFirstElement() {
        return getFirstElementFromFirstRow().duration;
    }

    private String getDurationString() {
        return getDurationFromFirstElement().text;
    }

    private boolean hasResults(String text) {
        return !text.contains("ZERO_RESULTS");
    }

    public String getCertifiedDuration() {
        boolean certifiedResponseStatus = hasResults(status);
        boolean certifiedElementStatus = hasResults(getFirstElementFromFirstRow().status);

        if (certifiedResponseStatus && certifiedElementStatus) {
            return getDurationString();
        }

        return "There's seems to be a problem!";
    }
}
