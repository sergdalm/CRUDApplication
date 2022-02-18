import java.time.Instant;
import java.util.List;

public class Post {
    private int id;
    private String content;
    private Instant created;
    private Instant updated;
    private List<Label> labels;
}
