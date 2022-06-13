package dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import model.Label;
import model.Post;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Value
@Builder
@EqualsAndHashCode
public class PostDto {
    Integer id;
    String title;
    String content;
    LocalDateTime created;
    LocalDateTime updated;
    @EqualsAndHashCode.Exclude
    List<LabelDto> labels;

    public static PostDto fromEntity(Post post) {
        return PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .created(post.getCreated())
                .updated(post.getUpdated())
                .build();
    }

    public Post toEntity() {
        return new Post(this.id, this.title, this.content, this.created, this.updated,
                this.getLabels().stream()
                .map(labelDto -> new Label(labelDto.getId(), labelDto.getName()))
                .collect(toList()));
    }
}
