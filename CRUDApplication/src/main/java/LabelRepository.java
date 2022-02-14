
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class LabelRepository implements GenericRepository<Label, Integer> {
    private static int idCount = 0;
    private Map<Integer, Label> labels = new HashMap<>();

    @Override
    public Label getById(Integer id) {
        return labels.get(id);
    }

    @Override
    public Label save(Label label) {
        int id = idCount++;
        label.setId(id);
        labels.put(id, label);
        return label;
    }

    @Override
    public Label update(Integer id, Label updatedLabel) {
        if(!labels.containsValue(updatedLabel))
            return null;
        else
            return labels.replace(id, updatedLabel);
    }

    @Override
    public Map<Integer, Label> getAll() {
        return labels;
    }

    @Override
    public void deleteById(Integer id) {
        if(labels.remove(id) == null)
            throw new NoSuchElementException();
    }
}
