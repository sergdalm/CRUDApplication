package model;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@Setter
public class Post {
    private Integer id;
    private String content;
    private Instant created;
    private Instant updated;
    private List<Label> labels;

    public Post(String content, List<Label> labels) {
        this.content = content;
        this.created = Instant.now();
        this.labels = labels;
    }

    @Override
    public String toString() {
        String labelList = "";
        for(Label label: labels)
            labelList = String.join(", ", labelList, label.getName());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return "Post{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", created=" + created +
                (updated == null ? "" : (", updated=" + updated)) +
                ", labels: " + labelList +
                '}';
    }
}
