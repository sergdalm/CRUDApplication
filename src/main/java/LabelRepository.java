import java.util.List;

public interface LabelRepository extends GenericRepository<Label, Integer>{
    Label getById(Integer id);

    Label save(Label obj);

    void update(Label obj);

    List<Label> getAll();

    void deleteById(Integer id);

}
