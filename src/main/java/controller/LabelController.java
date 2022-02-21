package controller;

import model.Label;
import repository.LabelRepository;
import repository.gson.JsonLabelRepositoryImpl;

import java.util.List;
import java.util.stream.Collectors;

public class LabelController {
    private final LabelRepository labelRepository = new JsonLabelRepositoryImpl();

//    public LabelController() {
//    }

    public Label saveLabel(String name) {
        Label label = new Label(name);
        return labelRepository.save(label);
    }

    public String getLabelName(Integer id) {
        return labelRepository.getById(id).getName();
    }

    public String getAllLabelsIdAndName() {
        List<Label> labels = labelRepository.getAll();
        StringBuilder idAndNames = new StringBuilder();
        for(Label label : labels) {
            idAndNames.append(label.getId());
            idAndNames.append(". ");
            idAndNames.append(label.getName());
            idAndNames.append("\n");
        }
        return idAndNames.toString();
    }

    public Integer getLabelCount() {
        List<Label> labels = labelRepository.getAll();
        return labels.size();
    }

    public String getAllLabelsSeparatedByComma() {
        List<Label> labels = labelRepository.getAll();
        String result = labels.stream()
                .map(Label::getName)
                .collect(Collectors.joining(", "));
        return result;
    }

    public void deleteById(Integer id) {
        labelRepository.deleteById(id);
    }

    public void update(Integer id, String newName) {
       labelRepository.getById(id).setName(newName);

    }

    public boolean isExisting(String name) {
        return getLabelByName(name) != null;
    }

    public Label getLabelByName(String name) {
        return labelRepository.getAll().stream()
                .filter(label -> label.getName().equals(name))
                .findFirst()
                .orElse(null);
    }
}
