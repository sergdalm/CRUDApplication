import repository.PostRepository;
import repository.postgres.PostgresPostRepository;
import service.WriterService;
import view.InputManager;
import view.TotalView;

public class DemoTest {
    public static void main(String[] args) {
        TotalView totalView = new TotalView();
        totalView.start();
    }
}