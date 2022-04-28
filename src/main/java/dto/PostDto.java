package dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Value
@Builder
public class PostDto {
    Integer id;
    String title;
    String content;
    LocalDateTime created;
    LocalDateTime updated;
    List<LabelDto> labels;
}
