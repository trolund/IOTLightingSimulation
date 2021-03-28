package cucumber.models;

public class LampID {

    private int id;
    private String name;

    public LampID(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
