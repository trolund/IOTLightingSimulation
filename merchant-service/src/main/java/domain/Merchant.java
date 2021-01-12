package domain;

import java.io.Serializable;

// Are we 100% sure that we need to implement serializable here?
public class Merchant implements Serializable {

    private String id;
    private String msg;

    public Merchant(String id, String msg) {
        this.id = id;
        this.msg = msg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}