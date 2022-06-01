package main.java.service;

import dto.LabelDto;
import model.Label;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.LabelRepository;
import service.LabelService;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class LabelServiceTest {
    @Mock
    private LabelRepository mockLabelRepository;
    private LabelService testLabelService;

    @BeforeEach
    void setUp() {
        mockLabelRepository = Mockito.mock(LabelRepository.class);
        testLabelService = new LabelService(mockLabelRepository);
    }

    @Test
    public void shouldGetAll() {
        // when
        testLabelService.getAll();
        // then
        verify(mockLabelRepository).getAll();

    }

    // amigoscode
    // Throws java.lang.NullPointerException
    @Test
    public void shouldSaveLabel() {
        // given
        LabelDto labelDto = LabelDto.builder()
                .id(1)
                .name("label")
                .build();

        Label labelEntity = labelDto.toEntity();

        // when
        testLabelService.createLabel(labelDto);

        // then
        ArgumentCaptor<Label> labelArgumentCaptor = ArgumentCaptor.forClass(Label.class);

        verify(mockLabelRepository).save(labelArgumentCaptor.capture());

        Label capturedLabel = labelArgumentCaptor.capture();

        assertThat(capturedLabel).isEqualTo(labelEntity);
    }

    // dmdev
    // Throws org.mockito.exceptions.misusing.PotentialStubbingProblem:
    @Test
    public void shouldSaveLabelAndReturnLabelDto() {
        LabelDto labelDto = LabelDto.builder()
                .id(1)
                .name("label")
                .build();

        Label labelEntity = labelDto.toEntity();

        Mockito.doReturn(labelEntity).when(mockLabelRepository).save(labelEntity);

        LabelDto saveResult = testLabelService.createLabel(labelDto);

        assertThat(saveResult).isEqualTo(labelDto);

    }
}
