package controller.console;

import dto.LabelDto;
import service.LabelService;

import java.util.List;

import static java.util.stream.Collectors.*;

public class LabelController {
    private final LabelService labelService = LabelService.getInstance();

    public LabelDto saveLabel(String name) {
        return labelService.createLabel(LabelDto.builder()
                .name(name)
                .build());
    }

    public String getLabelName(Integer id) {
        return labelService.getById(id).getName();
    }

    public List<LabelDto> getAll() {
        return  labelService.getAll();
    }

    public String getAllLabelsSeparatedByComma() {
        List<LabelDto> labels = labelService.getAll();
        return labels.stream()
                .map(LabelDto::getName)
                .collect(joining(", "));
    }

    public boolean deleteById(Integer id) {
        return labelService.deleteLabel(id);
    }

    public void update(Integer id, String newName) {
       labelService.updateLabel(LabelDto.builder()
               .id(id)
               .name(newName)
               .build());

    }

    public List<LabelDto> getLabelsForPost(Integer postId) {
        return labelService.getLabelsByPostId(postId).stream()
                .map(label -> LabelDto.builder()
                        .id(label.getId())
                        .name(label.getName())
                        .build())
                .collect(toList());
    }

    public LabelDto getLabelById(Integer id) {
        return labelService.getById(id);


    }
}
