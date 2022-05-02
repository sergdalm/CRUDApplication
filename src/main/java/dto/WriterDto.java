package dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class WriterDto {
    Integer id;
    String fullName;
}
