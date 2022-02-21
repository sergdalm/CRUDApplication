package controller;

import model.Label;
import model.Writer;
import repository.LabelRepository;
import repository.gson.JsonLabelRepositoryImpl;

import java.util.List;

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

    public void deleteBuId(Integer id) {
        labelRepository.deleteById(id);
    }

}
