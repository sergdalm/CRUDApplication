package service;

import dto.LabelDto;
import repository.LabelRepository;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class LabelService {
    private final LabelRepository labelRepository;

    public LabelService(LabelRepository labelRepository) {
        this.labelRepository = labelRepository;
    }

    public LabelDto createLabel(LabelDto labelDto) {
        var label = labelRepository.save(labelDto.toEntity());
        return LabelDto.fromEntity(label);
    }

    public LabelDto updateLabel(LabelDto labelDto) {
        labelRepository.update(labelDto.toEntity());
        return labelDto;
    }

    public boolean deleteLabel(Integer id) {
        return labelRepository.deleteById(id);
    }

    public List<LabelDto> getAll() {
        return labelRepository.getAll().stream()
                .map(LabelDto::fromEntity)
                .collect(toList());
    }

    public LabelDto getById(Integer id) {
        var label = labelRepository.getById(id);
        return LabelDto.fromEntity(label);
    }

    public List<LabelDto> getLabelsByPostId(Integer postId) {
        return labelRepository.getLabelsByPostId(postId).stream()
                .map(LabelDto::fromEntity)
                .collect(toList());
    }
}
