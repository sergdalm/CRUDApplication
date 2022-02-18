import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class JsonLabelRepositoryImpl implements LabelRepository, Serializable {
    private Map<Integer, Label> labels;
    private static int idCount;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    Path path = Path.of("src", "main", "resources", "labels.json");

    // Constructor saves data from labels.json into labels
    public JsonLabelRepositoryImpl() {
        try {
            String json = Files.readString(path);
            Type type = new TypeToken<Map<Integer, Label>>(){}.getType();
            Map<Integer, Label> jsonMap = gson.fromJson(json, type);
            if(jsonMap == null) {
                labels = new HashMap<>();
            }
            else {
                labels = jsonMap;
                idCount = labels.values().stream()
                        .mapToInt(Label::getId)
                        .summaryStatistics()
                        .getMax();
                idCount++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Label getById(Integer id) {
        return labels.get(id);
    }

    @Override
    public Label save(Label label) {
        label.setId(idCount++);
        labels.put(label.getId(), label);
        return label;
    }

    @Override
    public void update(Label updatedLabel) {
        labels.replace(updatedLabel.getId(), updatedLabel);
    }

    @Override
    public List<Label> getAll() {
        return new ArrayList<>(labels.values());
    }

    @Override
    public void deleteById(Integer id) {
        if(labels.remove(id) == null)
            System.out.println("There is no label with id " + id);

    }

    // Save map into labels.json
    @Override
    public void saveRepository() {
        String json = gson.toJson(labels);
        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(path.toFile()))) {
            fileWriter.append(json);
            fileWriter.append('\n');
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}