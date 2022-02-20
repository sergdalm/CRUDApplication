package controller;

import model.Label;
import repository.LabelRepository;
import repository.gson.JsonLabelRepositoryImpl;

public class LabelController {
    private final LabelRepository labelRepository = new JsonLabelRepositoryImpl();

//    public LabelController() {
//    }

    public Label saveLabel(String name) {
        Label label = new Label(name);
        return labelRepository.save(label);
    }

    public Label getLabelName(Integer id) {
        return labelRepository.getById(id);
    }
}
