import model.Label;
import repository.JsonLabelRepositoryImpl;

public class DemoTest {
    public static void main(String[] args) {
        JsonLabelRepositoryImpl labelRepository = new JsonLabelRepositoryImpl();

//        model.Label label = new model.Label("Hi");
//        model.Label label2 = new model.Label("Bye");
//        labelRepository.save(label);
//        labelRepository.save(label2);

        Label label1 = labelRepository.getById(1);
        label1.setName("Hello");
        labelRepository.update(label1);
//        labelRepository.deleteById(0);

        labelRepository.saveRepository();
    }
}
