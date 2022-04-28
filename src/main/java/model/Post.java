package model;

import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {
    private Integer id;
    private String title;
    private String content;
    private LocalDateTime created;
    private LocalDateTime updated;
    private List<Label> labels;
}
