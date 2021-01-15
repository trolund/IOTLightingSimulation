package com.dto;

import java.io.Serializable;

public class Token implements Serializable {

    private String id;

    public Token(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}