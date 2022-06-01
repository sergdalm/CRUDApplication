package dto;

import lombok.Builder;
import lombok.Value;
import model.Label;

@Value
@Builder
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
