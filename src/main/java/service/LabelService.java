package service;

import dto.LabelDto;
import model.Label;
import repository.LabelRepository;
import repository.postgres.PostgresLabelRepository;

import java.util.List;

import static java.util.stream.Collectors.*;

public class LabelService {
    private static final LabelService INSTANCE = new LabelService();
    private final LabelRepository labelRepository =  PostgresLabelRepository.getInstance();

    private LabelService() {
    }

    public LabelDto createLabel(LabelDto labelDto) {
        var label = labelRepository.save(new Label(0, labelDto.getName()));
        return LabelDto.builder()
                .id(label.getId())
                .name(label.getName())
                .build();
    }

    public LabelDto updateLabel(LabelDto labelDto) {
        labelRepository.update(new Label(labelDto.getId(),labelDto.getName()));
        return labelDto;
    }

    public boolean deleteLabel(Integer id) {
        return labelRepository.deleteById(id);
    }

    public List<LabelDto> getAll() {
        return labelRepository.getAll().stream()
                .map(label -> LabelDto.builder()
                        .id(label.getId())
                        .name(label.getName())
                        .build())
                .collect(toList());
    }

    public LabelDto getById(Integer id) {
        var label = labelRepository.getById(id);
        return LabelDto.builder()
                .id(label.getId())
                .name(label.getName())
                .build();
    }


    public static LabelService getInstance() {
        return INSTANCE;
    }

    public List<Label> getLabelsByPostId(Integer postId) {
        return labelRepository.getLabelsByPostId(postId);
    }
}
