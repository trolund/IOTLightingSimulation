package dto;

import java.io.Serializable;

/**
 * @author Troels (s161791)
 * SuccessObject.
 */
public class SuccessObject implements Serializable {

    private String msg;

    public SuccessObject() {

    }

    public SuccessObject(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}