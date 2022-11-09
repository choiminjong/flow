package com.nexon.flow.domain.dto;

public enum Status {

    Activate("Activate"), Inactive("Inactive");

    private final String status;

    private Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

}



