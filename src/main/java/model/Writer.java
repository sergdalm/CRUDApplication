package model;// This class contains only getters and setters
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class Writer {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    @EqualsAndHashCode.Exclude
    private List<Post> posts;
}
