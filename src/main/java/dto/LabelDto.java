package dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class LabelDto {
    Integer id;
    String name;
}
