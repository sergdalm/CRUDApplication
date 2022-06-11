package main.java.service;

import dto.LabelDto;
import model.Label;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.LabelRepository;
import service.LabelService;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class LabelServiceTest {
    @Mock
    private LabelRepository mockLabelRepository;
    @InjectMocks
    private LabelService testLabelService;

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
        LabelDto labelDto = LabelDto.builder()
                .id(1)
                .name("label")
                .build();

        Label labelEntity = labelDto.toEntity();

        Mockito.doReturn(labelEntity).when(mockLabelRepository).save(labelEntity);

        // when
        LabelDto saveResult = testLabelService.createLabel(labelDto);

        // then
        ArgumentCaptor<Label> labelArgumentCaptor = ArgumentCaptor.forClass(Label.class);

        verify(mockLabelRepository).save(labelArgumentCaptor.capture());

        Label capturedLabel = labelArgumentCaptor.getValue();

        assertThat(capturedLabel).isEqualTo(labelEntity);
        assertThat(saveResult).isEqualTo(labelDto);
    }
}
