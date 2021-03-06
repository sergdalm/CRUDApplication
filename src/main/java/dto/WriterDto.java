package dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import model.Writer;

@Value
@Builder
@EqualsAndHashCode
public class WriterDto {
    Integer id;
    String fullName;

    public static WriterDto fromEntity(Writer writer) {
        return WriterDto.builder()
                .id(writer.getId())
                .fullName(writer.getFirstName() + " " + writer.getLastName())
                .build();
    }
}
