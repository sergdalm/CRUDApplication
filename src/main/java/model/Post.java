package model;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.List;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Instant getCreated() {

        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Instant getUpdated() {
        return updated;
    }

    public void setUpdated(Instant updated) {
        this.updated = updated;
    }

    public List<Label> getLabels() {
        return labels;
    }

    public void setLabels(List<Label> labels) {
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
