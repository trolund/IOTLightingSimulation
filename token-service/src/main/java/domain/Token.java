package domain;

import java.io.Serializable;

// Are we 100% sure that we need to implement serializable here?
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