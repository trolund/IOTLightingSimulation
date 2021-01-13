package com.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class CustomerTokens implements Serializable {

    private final List<Token> tokens;
    private String customerId;

    public CustomerTokens(String customerId) {
        this.customerId = customerId;
        this.tokens = new ArrayList<>();
    }

    public List<Token> getTokens() {
        return tokens;
    }

    public String getCustomerId() {
        return customerId;
    }

}