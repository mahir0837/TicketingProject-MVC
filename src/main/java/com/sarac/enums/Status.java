package com.sarac.enums;

public enum Status {

    OPEN("Open"),INPROGRES("In-progres"), COMPLETE("Completed");

    private String value;

    public String getValue() {
        return value;
    }

    Status(String value) {
        this.value = value;
    }


}
