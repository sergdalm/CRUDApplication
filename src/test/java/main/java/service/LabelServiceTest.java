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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class LabelServiceTest {
    @Mock
    private LabelRepository mockLabelRepository;
    @InjectMocks
    private LabelService testLabelService;

    private LabelDto getLabelDto() {
        return LabelDto.builder()
                .id(1)
                .name("label")
                .build();
    }

    private Label getLabelEntity() {
        return getLabelDto().toEntity();
    }

    private int getLabelId() {
        return getLabelEntity().getId();
    }

    private List<Label> getLabelEntityList() {
        return List.of(
                new Label(1, "label1"),
                new Label(2, "label2"));
    }

    private List<LabelDto> getLabelDtoList() {
        return getLabelEntityList().stream()
                .map(LabelDto::fromEntity)
                .collect(Collectors.toList());
    }

    private int getPostId() {
        return 1;
    }

    @Test
    public void shouldGetAll() {
        testLabelService.getAll();
        verify(mockLabelRepository).getAll();

    }

    @Test
    public void shouldSaveLabelAndReturnLabelDto() {
        doReturn(getLabelEntity()).when(mockLabelRepository).save(getLabelEntity());

        LabelDto saveResult = testLabelService.createLabel(getLabelDto());

        ArgumentCaptor<Label> labelArgumentCaptor = ArgumentCaptor.forClass(Label.class);
        verify(mockLabelRepository).save(labelArgumentCaptor.capture());
        Label capturedLabel = labelArgumentCaptor.getValue();

        assertEquals(getLabelEntity(), capturedLabel);
        assertEquals(getLabelDto(), saveResult);
    }

    @Test
    public void shouldUpdateLabelAndReturnUpdatedLabel() {
        doReturn(getLabelEntity()).when(mockLabelRepository).update(getLabelEntity());

        LabelDto updateResult = testLabelService.updateLabel(getLabelDto());

        ArgumentCaptor<Label> labelArgumentCaptor = ArgumentCaptor.forClass(Label.class);
        verify(mockLabelRepository).update(labelArgumentCaptor.capture());
        Label capturedLabel = labelArgumentCaptor.getValue();

        assertEquals(getLabelEntity(), capturedLabel);
        assertEquals(getLabelDto(), updateResult);
    }

    @Test
    public void shouldDeleteByIdAndReturnTrue() {
        doReturn(true).when(mockLabelRepository).deleteById(getLabelId());

        boolean deletedResult = testLabelService.deleteLabel(getLabelId());

        ArgumentCaptor<Integer> idArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(mockLabelRepository).deleteById(idArgumentCaptor.capture());
        Integer capturedId = idArgumentCaptor.getValue();

        assertEquals(getLabelId(), capturedId);
        assertTrue(deletedResult);
    }

    @Test
    public void shouldGetAllLabels() {
        doReturn(getLabelEntityList()).when(mockLabelRepository).getAll();

        List<LabelDto> allLabelsListResult = testLabelService.getAll();

        assertEquals(getLabelDtoList(), allLabelsListResult);
    }

    @Test
    public void shouldGetLabelById() {
        doReturn(getLabelEntity()).when(mockLabelRepository).getById(getLabelEntity().getId());

        LabelDto resultLabelDto = testLabelService.getById(getLabelEntity().getId());

        ArgumentCaptor<Integer> idArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(mockLabelRepository).getById(idArgumentCaptor.capture());
        Integer capturedId = idArgumentCaptor.getValue();

        assertEquals(getLabelEntity().getId(), capturedId);
        assertEquals(LabelDto.fromEntity(getLabelEntity()), resultLabelDto);
    }

    @Test
    public void shouldGetLabelsByPostId() {
        doReturn(getLabelEntityList()).when(mockLabelRepository).getLabelsByPostId(getPostId());

        List<LabelDto> labelsByPostIdResult = testLabelService.getLabelsByPostId(getPostId());

        ArgumentCaptor<Integer> postIdArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(mockLabelRepository).getLabelsByPostId(postIdArgumentCaptor.capture());
        Integer capturedPostId = postIdArgumentCaptor.getValue();

        assertEquals(getPostId(), capturedPostId);
        Iterator<LabelDto> iteratorExpected = getLabelDtoList().iterator();
        for (LabelDto labelDto : labelsByPostIdResult) {
            assertEquals(iteratorExpected.next(), labelDto);
        }
    }
}
