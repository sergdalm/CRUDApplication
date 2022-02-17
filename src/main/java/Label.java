import java.io.Serializable;

public class Label implements Serializable {
    static private int idCount;
    private int id;
    private String name;

    public Label(String name) {
        this.name = name;
        id = idCount++;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
