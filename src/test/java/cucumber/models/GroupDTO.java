package cucumber.models;

import lamp.LampInfo;

public class GroupDTO {
    private String groupName;
    private LampInfo lampInfo;

    public GroupDTO(String groupName, LampInfo lampInfo) {
        this.groupName = groupName;
        this.lampInfo = lampInfo;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public LampInfo getLampInfo() {
        return lampInfo;
    }

    public void setLampInfo(LampInfo lampInfo) {
        this.lampInfo = lampInfo;
    }
}
