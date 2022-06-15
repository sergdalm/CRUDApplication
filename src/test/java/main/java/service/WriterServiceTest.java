package main.java.service;

import dto.LoginWriterDto;
import dto.WriterDto;
import exceptions.LoginErrorException;
import exceptions.ValidException;
import model.Writer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.WriterRepository;
import service.WriterService;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class WriterServiceTest {
    @Mock
    private WriterRepository mockWriterRepository;
    @InjectMocks
    private WriterService testWriterService;

    private Writer getWriterEntity() {
        return new Writer(1, "Petr", "Petrov", "petrov@mail.com", "1234", null);
    }

    private LoginWriterDto getLoginWriterDto() {
        return LoginWriterDto.fromEntity(getWriterEntity());
    }

    private List<Writer> getWriterEntityList() {
        return List.of(
                new Writer(1, "Petr", "Petrov", "petrov@mail.com", "1234", null),
                new Writer(1, "Ivan", "Ivanov", "ivanov@mail.com", "5678", null)
        );
    }

    private String getWrongEmail() {
        return "ivanov11@mail.com";
    }

    private String getWrongPassword() {
        return "234";
    }

    private String getCorrectEmail() {
        return getWriterEntity().getEmail();
    }

    private String getCorrectPassword() {
        return getWriterEntity().getPassword();
    }

    private int gerWriterId() {
        return 1;
    }

    @Test
    public void shouldGetWriterById() {
        doReturn(getWriterEntity()).when(mockWriterRepository).getById(getWriterEntity().getId());

        WriterDto writerResult = testWriterService.getWriterById(getWriterEntity().getId());

        ArgumentCaptor<Integer> writerArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(mockWriterRepository).getById(writerArgumentCaptor.capture());
        int capturedWriterId = writerArgumentCaptor.getValue();

        assertEquals(getWriterEntity().getId(), capturedWriterId);
        assertEquals(WriterDto.fromEntity(getWriterEntity()), writerResult);
    }

    @Test
    public void shouldCreateWriterAndReturnWriterDto() {
        doReturn(getWriterEntity()).when(mockWriterRepository).save(getWriterEntity());

        LoginWriterDto newWriterResult = testWriterService.createWriter(getLoginWriterDto());

        ArgumentCaptor<Writer> writerArgumentCaptor = ArgumentCaptor.forClass(Writer.class);
        verify(mockWriterRepository).save(writerArgumentCaptor.capture());
        Writer capturedWriter = writerArgumentCaptor.getValue();

        assertEquals(getLoginWriterDto().toEntity(), capturedWriter);
        assertEquals(LoginWriterDto.fromEntity(getWriterEntity()), newWriterResult);
    }

    @Test
    public void shouldGetAllWriters() {
        doReturn(getWriterEntityList()).when(mockWriterRepository).getAll();

        List<WriterDto> allWritersResult = testWriterService.getAllWriters();

        for (int i = 0; i < getWriterEntityList().size(); i++) {
            assertEquals(WriterDto.fromEntity(getWriterEntityList().get(i)), allWritersResult.get(i));
        }
    }

    @Test
    public void shouldReturnLoginWriterDtoIfLoginSuccess() throws LoginErrorException {
        Optional<Writer> maybeWriter = Optional.of(getWriterEntity());
        String email = getWriterEntity().getEmail();
        String password = getWriterEntity().getPassword();

        doReturn(maybeWriter).when(mockWriterRepository).getWriterByEmail(email);

        LoginWriterDto loginWriterResult = testWriterService.loginWriter(email, password);

        ArgumentCaptor<String> emailArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(mockWriterRepository).getWriterByEmail(emailArgumentCaptor.capture());
        String capturedEmail = emailArgumentCaptor.getValue();

        assertEquals(email, capturedEmail);
        assertEquals(LoginWriterDto.fromEntity(getWriterEntity()), loginWriterResult);

    }

    @Test
    public void shouldThrowExceptionIfEmailDoesNotExist() {
        String password = getWriterEntity().getPassword();
        Optional<Writer> maybeWriter = Optional.empty();
        doReturn(maybeWriter).when(mockWriterRepository).getWriterByEmail(getWrongEmail());

        assertAll(
                () -> {
                    var exception = assertThrows(LoginErrorException.class,
                            () -> testWriterService.loginWriter(getWrongEmail(), password));
                    assertThat(exception.getMessage()).isEqualTo("Email does not exist");
                }
        );

    }

    @Test
    public void shouldThrowExceptionIfPasswordDoesIsInvalid() {
        Optional<Writer> maybeWriter = Optional.of(getWriterEntity());
        doReturn(maybeWriter).when(mockWriterRepository).getWriterByEmail(getCorrectEmail());

        assertAll(
                () -> {
                    var exception = assertThrows(LoginErrorException.class,
                            () -> testWriterService.loginWriter(getCorrectEmail(), getWrongPassword()));
                    assertThat(exception.getMessage()).isEqualTo("Password is invalid");
                }
        );
    }

    @Test
    public void shouldUpdateWriter() {
        testWriterService.update(getLoginWriterDto());

        ArgumentCaptor<Writer> writerArgumentCaptor = ArgumentCaptor.forClass(Writer.class);
        verify(mockWriterRepository).update(writerArgumentCaptor.capture());
        Writer capturedWriter = writerArgumentCaptor.getValue();

        assertEquals(getLoginWriterDto().toEntity(), capturedWriter);
    }

    @Test
    public void shouldDeleteWriterById() {
        testWriterService.delete(gerWriterId());

        ArgumentCaptor<Integer> writerIdArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(mockWriterRepository).deleteById(writerIdArgumentCaptor.capture());
        Integer capturedWriterId = writerIdArgumentCaptor.getValue();

        assertEquals(gerWriterId(), capturedWriterId);
    }

    @Test
    public void shouldReturnTrueIfPasswordsAreSame() {
        boolean result = testWriterService.checkPassword(getCorrectPassword(), getCorrectPassword());
        assertTrue(result);
    }

    @Test
    public void shouldThrowExceptionIfPasswordIsInvalid() {
        assertAll(
                () -> {
                    var exception = assertThrows(ValidException.class,
                            () -> testWriterService.checkPassword(getCorrectPassword(), getWrongPassword()));
                    assertThat(exception.getErrors().get(0).getMassage()).isEqualTo("Passwords are not same");
                }
        );
    }
}
