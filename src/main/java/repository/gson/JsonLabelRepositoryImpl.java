package repository.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.Label;
import repository.LabelRepository;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.*;
import java.util.*;


public class JsonLabelRepositoryImpl implements LabelRepository {
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final Path path = Path.of("src", "main", "resources", "labels.json");

    @Override
    public Label getById(Integer id) {
        return getAllLabels().stream()
                .filter(l -> l.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Label save(Label label) {
        List<Label> currentLabels = getAllLabels();
        Integer newId = generateMaxId(currentLabels);
        label.setId(newId);
        currentLabels.add(label);
        writeAllLabels(currentLabels);
        return label;
    }

    @Override
    public Label update(Label updatedLabel) {
        List<Label> currentLabels = getAllLabels();
        currentLabels.forEach(label -> {
            if(label.getId().equals(updatedLabel.getId())) {
                label.setName(updatedLabel.getName());
            }
        });
        writeAllLabels(currentLabels);
        return updatedLabel;
    }

    @Override
    public List<Label> getAll() {
        return getAllLabels();
    }


    @Override
    public void deleteById(Integer id) {
        List<Label> currentLabels = getAllLabels();
        currentLabels.removeIf(label -> label.getId().equals(id));
        writeAllLabels(currentLabels);
    }

    private List<Label> getAllLabels() {
        List<Label> labels;
        try {
            String json = Files.readString(path);
            Type type = new TypeToken<ArrayList<Label>>(){}.getType();
            labels = gson.fromJson(json, type);
        } catch (IOException exc) {
            labels = new ArrayList<>();
        }
        return Objects.isNull(labels) ? new ArrayList<>() : labels;
    }

    private void writeAllLabels(List<Label> labels) {
        String json = gson.toJson(labels);

        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(path.toFile()))) {
            fileWriter.append(json);
            fileWriter.append('\n');
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Integer generateMaxId(List<Label> labels) {
        if(Objects.isNull(labels)) {
            return 1;
        }
        else {
            Label labelsWithMaxId = labels.stream()
                    .max(Comparator.comparing(Label::getId))
                    .orElse(null);
            return Objects.nonNull(labelsWithMaxId) ? labelsWithMaxId.getId() + 1 : 1;
        }
    }


}