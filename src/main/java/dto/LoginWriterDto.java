package dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class LoginWriterDto {
    Integer id;
    String firstName;
    String lastName;
    String email;
    String password;
}
