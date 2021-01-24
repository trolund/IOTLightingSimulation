package messaging;

import lamp.LampInfo;

public class InfoEvent extends Event{

    private static String EventName = "Info";

    private LampInfo info;

    public InfoEvent(LampInfo info) {
        super(EventName, new Object[]{});
        this.info = info;
    }

    public InfoEvent(Object[] arguments, LampInfo info) {
        super(EventName, arguments);
        this.info = info;
    }

    public LampInfo getInfo() {
        return info;
    }

    public void setInfo(LampInfo info) {
        this.info = info;
    }
}
