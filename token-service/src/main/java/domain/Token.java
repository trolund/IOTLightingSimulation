package domain;

import java.io.Serializable;

public class Token implements Serializable {

    private final String id;

    public Token(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

}