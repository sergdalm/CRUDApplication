package main.java.service;

import dto.LabelDto;
import model.Label;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.LabelRepository;
import service.LabelService;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class LabelServiceTest {
    @Mock
    private LabelRepository mockLabelRepository;
    @InjectMocks
    private LabelService testLabelService;
    private static final LabelDto LABEL_DTO = LabelDto.builder()
            .id(1)
            .name("label")
            .build();
    private static final Label LABEL_ENTITY = LABEL_DTO.toEntity();

    @Test
    public void shouldGetAll() {
        // when
        testLabelService.getAll();
        // then
        verify(mockLabelRepository).getAll();

    }

    @Test
    public void shouldSaveLabelAndReturnLabelDto() {
        // given
        doReturn(LABEL_ENTITY).when(mockLabelRepository).save(LABEL_ENTITY);

        // when
        LabelDto saveResult = testLabelService.createLabel(LABEL_DTO);

        // then
        ArgumentCaptor<Label> labelArgumentCaptor = ArgumentCaptor.forClass(Label.class);

        verify(mockLabelRepository).save(labelArgumentCaptor.capture());

        Label capturedLabel = labelArgumentCaptor.getValue();

        assertThat(capturedLabel).isEqualTo(LABEL_ENTITY);
        assertThat(saveResult).isEqualTo(LABEL_DTO);
    }

    @Test
    public void shouldUpdateLabelAndReturnUpdatedLabel() {
        // given
        doReturn(LABEL_ENTITY).when(mockLabelRepository).update(LABEL_ENTITY);

        // when
        LabelDto updateResult = testLabelService.updateLabel(LABEL_DTO);

        // then
        ArgumentCaptor<Label> labelArgumentCaptor = ArgumentCaptor.forClass(Label.class);

        verify(mockLabelRepository).update(labelArgumentCaptor.capture());

        Label capturedLabel = labelArgumentCaptor.getValue();

        assertThat(capturedLabel).isEqualTo(LABEL_ENTITY);
        assertThat(updateResult).isEqualTo(LABEL_DTO);
    }

    @Test
    public void shouldDeleteByIdAndReturnTrue() {
        // given
        int id = 1;

        doReturn(true).when(mockLabelRepository).deleteById(id);

        // when
        Boolean deletedResult = testLabelService.deleteLabel(id);

        // then
        ArgumentCaptor<Integer> idArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(mockLabelRepository).deleteById(idArgumentCaptor.capture());
        Integer capturedId = idArgumentCaptor.getValue();

        assertThat(capturedId).isEqualTo(id);
        assertThat(deletedResult).isTrue();
    }

    @Test
    public void shouldGetAllLabels() {
        // given
        List<Label> labelEntities = List.of(
                new Label(1, "label1"),
                new Label(2, "label2"));

        List<LabelDto> labelDtoList = labelEntities.stream()
                .map(LabelDto::fromEntity)
                .collect(Collectors.toList());

        doReturn(labelEntities).when(mockLabelRepository).getAll();

        // when
        List<LabelDto> allLabelsListResult = testLabelService.getAll();

        // then
        assertThat(allLabelsListResult).isEqualTo(labelDtoList);
    }

    @Test
    public void shouldGetLabelById() {
        // given
        doReturn(LABEL_ENTITY).when(mockLabelRepository).getById(LABEL_ENTITY.getId());

        // when
        LabelDto resultLabelDto = testLabelService.getById(LABEL_ENTITY.getId());

        // then
        ArgumentCaptor<Integer> idArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(mockLabelRepository).getById(idArgumentCaptor.capture());
        Integer capturedId = idArgumentCaptor.getValue();

        assertThat(capturedId).isEqualTo(LABEL_ENTITY.getId());
        assertThat(resultLabelDto).isEqualTo(LabelDto.fromEntity(LABEL_ENTITY));
    }

    @Test
    public void shouldGetLabelsByPostId() {
        // given
        int postId = 1;
        List<Label> labelEntities = List.of(
                new Label(1, "label1"),
                new Label(2, "label2"));
        List<LabelDto> labelDtoList = labelEntities.stream()
                .map(LabelDto::fromEntity)
                .collect(Collectors.toList());

        doReturn(labelEntities).when(mockLabelRepository).getLabelsByPostId(postId);

        // when
        List<LabelDto> labelsByPostIdResult = testLabelService.getLabelsByPostId(postId);

        // then
        ArgumentCaptor<Integer> postIdArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(mockLabelRepository).getLabelsByPostId(postIdArgumentCaptor.capture());
        Integer capturedPostId = postIdArgumentCaptor.getValue();

        assertThat(capturedPostId).isEqualTo(postId);
        Iterator<LabelDto> iteratorExpected = labelDtoList.iterator();
        for (LabelDto labelDto : labelsByPostIdResult) {
            assertThat(labelDto).isEqualTo(iteratorExpected.next());
        }
    }
}
