package dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import model.Label;

import java.util.Objects;

@Value
@Builder
@EqualsAndHashCode
public class LabelDto {
    Integer id;
    String name;

    public static LabelDto fromEntity(Label label) {
        return LabelDto.builder()
                .id(label.getId())
                .name(label.getName())
                .build();
    }

    public Label toEntity() {
        return new Label(this.id, this.name);
    }
}
