package model;// This class contains only getters and setters
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Writer {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<Post> posts;
}
