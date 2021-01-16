package domain;

import java.io.Serializable;

// Are we 100% sure that we need to implement serializable here?
public class ExampleObj implements Serializable {

    private Integer id;
    private String msg;

    public ExampleObj(Integer id, String msg) {
        this.id = id;
        this.msg = msg;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}