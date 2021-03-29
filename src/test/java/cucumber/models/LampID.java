package cucumber.models;

public class LampID {

    private int id;
    private String name;
    private int new_intensity;

    public LampID(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public LampID(int id, String name, int new_intensity) {
        this.id = id;
        this.name = name;
        this.new_intensity = new_intensity;
    }

    public int getNew_intensity() {
        return new_intensity;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNew_intensity(int new_intensity) {
        this.new_intensity = new_intensity;
    }
}
