package lamp;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LampInfo {

    private int id;
    private String name;
    private float intensity;
    private Color color;
    private boolean isOn = false;
    private Set<String> groups = new HashSet<>();

    public LampInfo() {
        this.id = (int) Math.floor(Math.random() * Math.floor(1000));
        this.name = "Unknown device";
        this.intensity = 100;
        this.color = new Color(255,255,255);
    }

    public LampInfo(int id, String name, float intensity, Color color) {
        this.id = id;
        this.name = name;
        this.intensity = intensity;
        this.color = color;
    }

    public LampInfo(int id, String name, float intensity, Color color, boolean isOn) {
        this.id = id;
        this.name = name;
        this.intensity = intensity;
        this.color = color;
        this.isOn = isOn;
    }

    public LampInfo(int id, String name, float intensity, Color color, boolean isOn, Set<String> groups) {
        this.id = id;
        this.name = name;
        this.intensity = intensity;
        this.color = color;
        this.isOn = isOn;
        this.groups = groups;
    }

    public LampInfo(String name, float intensity, Color color) {
        this.id = (int) Math.floor(Math.random() * Math.floor(100));
        this.name = name;
        this.intensity = intensity;
        this.color = color;
    }

    public boolean isOn() {
        return isOn;
    }

    public void setOn(boolean on) {
        isOn = on;
    }

    public void addToGroup(String groupName){
        groups.add(groupName);
    }

    public void removeToGroup(String groupName){
        groups.remove(groupName);
    }

    public Set<String> getGroups() {
        return groups;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getIntensity() {
        return intensity;
    }

    public void setIntensity(float intensity) {
        this.intensity = intensity;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "id: " + id + ", name: " + name +
                ", isOn: " + (isOn ? "ON" : "OFF") +
                ", intensity: " + intensity +
                ", color: " + color +
                ", groups:" + groups;
    }


}
