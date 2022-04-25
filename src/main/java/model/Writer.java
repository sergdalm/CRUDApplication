package model;// This class contains only getters and setters
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class Writer {
    private Integer id;
    private String firstName;
    private String lastName;
    private List<Post> posts;

    public Writer(String firstName, String lastName, List<Post> posts) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.posts = posts;
    }

    public String getFullName() {
        return String.join(" ", firstName, lastName);
    }

    public String getIdAndFullName() {
        return id + ". " + getFullName();
    }
}
