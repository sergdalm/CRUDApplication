package model;

import java.io.Serializable;

public class Label implements Serializable {
    static private int idCount;
    private int id;
    private String name;

    public Label(String name) {
        this.name = name;
        id = idCount++;
    }

    @Override
    public String toString() {
        return "model.Label{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
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

}
