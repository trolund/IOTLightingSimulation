package dto;

import java.io.Serializable;

public class SuccsesObject implements Serializable {

    private String msg;

    public SuccsesObject() {
    }

    public SuccsesObject(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
