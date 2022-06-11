package dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import model.Writer;

@Value
@Builder
@EqualsAndHashCode
public class LoginWriterDto {
    Integer id;
    String firstName;
    String lastName;
    String email;
    String password;

    public static LoginWriterDto fromEntity(Writer writer) {
        return LoginWriterDto.builder()
                .id(writer.getId())
                .firstName(writer.getFirstName())
                .lastName(writer.getLastName())
                .email(writer.getEmail())
                .password(writer.getPassword())
                .build();
    }

    public Writer toEntity() {
        return new Writer(this.id, this.firstName, this.lastName, this.email, this.password, null);
    }
}
