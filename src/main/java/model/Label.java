package model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Label {
    private Integer id;
    private String name;

    public Label() {
    }

    public Label(String name) {
        this.name = name;
    }

}
