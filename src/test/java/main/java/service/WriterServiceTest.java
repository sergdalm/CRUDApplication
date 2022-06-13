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
    private static final Writer WRITER_ENTITY = new Writer(1, "Petr", "Petrov", "petrov@mail.com", "1234", null);
    private static final WriterDto WRITER_DTO = WriterDto.fromEntity(WRITER_ENTITY);
    private static final LoginWriterDto LOGIN_WRITER_DTO = LoginWriterDto.fromEntity(WRITER_ENTITY);

    @Test
    public void shouldGetWriterById() {
        // given
        doReturn(WRITER_ENTITY).when(mockWriterRepository).getById(WRITER_ENTITY.getId());

        // when
        WriterDto writerResult = testWriterService.getWriterById(WRITER_ENTITY.getId());

        // then
        ArgumentCaptor<Integer> writerArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(mockWriterRepository).getById(writerArgumentCaptor.capture());
        int capturedWriterId = writerArgumentCaptor.getValue();

        assertEquals(WRITER_ENTITY.getId(), capturedWriterId);
        assertEquals(WriterDto.fromEntity(WRITER_ENTITY), writerResult);
    }

    @Test
    public void shouldCreateWriterAndReturnWriterDto() {
        // given
        Writer newWriter = new Writer(1, LOGIN_WRITER_DTO.getFirstName(), LOGIN_WRITER_DTO.getLastName(),
                LOGIN_WRITER_DTO.getEmail(), LOGIN_WRITER_DTO.getPassword(), null);

        doReturn(newWriter).when(mockWriterRepository).save(LOGIN_WRITER_DTO.toEntity());

        // when
        LoginWriterDto newWriterResult = testWriterService.createWriter(LOGIN_WRITER_DTO);

        // then
        ArgumentCaptor<Writer> writerArgumentCaptor = ArgumentCaptor.forClass(Writer.class);
        verify(mockWriterRepository).save(writerArgumentCaptor.capture());
        Writer capturedWriter = writerArgumentCaptor.getValue();

        assertEquals(LOGIN_WRITER_DTO.toEntity(), capturedWriter);
        assertEquals(LoginWriterDto.fromEntity(newWriter), newWriterResult);
    }

    @Test
    public void shouldGetAllWriters() {
        // given
        List<Writer> writers = List.of(
                new Writer(1, "Petr", "Petrov", "petrov@mail.com", "1234", null),
                new Writer(1, "Ivan", "Ivanov", "ivanov@mail.com", "5678", null)
        );

        doReturn(writers).when(mockWriterRepository).getAll();

        // when
        List<WriterDto> allWritersResult = testWriterService.getAllWriters();

        // then
        for (int i = 0; i < writers.size(); i++) {
            assertEquals(WriterDto.fromEntity(writers.get(i)), allWritersResult.get(i));
        }
    }

    @Test
    public void shouldReturnLoginWriterDtoIfLoginSuccess() throws LoginErrorException {
        // given
        Optional<Writer> maybeWriter = Optional.of(WRITER_ENTITY);
        String email = WRITER_ENTITY.getEmail();
        String password = WRITER_ENTITY.getPassword();

        doReturn(maybeWriter).when(mockWriterRepository).getWriterByEmail(email);

        // when
        LoginWriterDto loginWriterResult = testWriterService.loginWriter(email, password);

        // then
        ArgumentCaptor<String> emailArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(mockWriterRepository).getWriterByEmail(emailArgumentCaptor.capture());
        String capturedEmail = emailArgumentCaptor.getValue();

        assertEquals(email, capturedEmail);
        assertEquals(LoginWriterDto.fromEntity(WRITER_ENTITY), loginWriterResult);

    }

    @Test
    public void shouldThrowExceptionIfEmailDoesNotExist() {
        // given
        String wrongEmail = "ivanov11@mail.com";
        String password = "5678";
        Optional<Writer> maybeWriter = Optional.empty();
        doReturn(maybeWriter).when(mockWriterRepository).getWriterByEmail(wrongEmail);

        assertAll(
                () -> {
                    var exception = assertThrows(LoginErrorException.class,
                            () -> testWriterService.loginWriter(wrongEmail, password));
                    assertThat(exception.getMessage()).isEqualTo("Email does not exist");
                }
        );

    }

    @Test
    public void shouldThrowExceptionIfPasswordDoesIsInvalid() {
        // given
        Optional<Writer> maybeWriter = Optional.of(WRITER_ENTITY);
        String email = WRITER_ENTITY.getEmail();
        String wrongPassword = "234";
        doReturn(maybeWriter).when(mockWriterRepository).getWriterByEmail(email);

        assertAll(
                () -> {
                    var exception = assertThrows(LoginErrorException.class,
                            () -> testWriterService.loginWriter(email, wrongPassword));
                    assertThat(exception.getMessage()).isEqualTo("Password is invalid");
                }
        );
    }

    @Test
    public void shouldUpdateWriter() {
        // when
        testWriterService.update(LOGIN_WRITER_DTO);

        // then
        ArgumentCaptor<Writer> writerArgumentCaptor = ArgumentCaptor.forClass(Writer.class);
        verify(mockWriterRepository).update(writerArgumentCaptor.capture());
        Writer capturedWriter = writerArgumentCaptor.getValue();

        assertEquals(LOGIN_WRITER_DTO.toEntity(), capturedWriter);
    }

    @Test
    public void shouldDeleteWriterById() {
        // given
        int writerId = 1;

        // when
        testWriterService.delete(writerId);

        // then
        ArgumentCaptor<Integer> writerIdArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(mockWriterRepository).deleteById(writerIdArgumentCaptor.capture());
        Integer capturedWriterId = writerIdArgumentCaptor.getValue();

        assertEquals(writerId, capturedWriterId);
    }

    @Test
    public void shouldReturnTrueIfPasswordsAreSame() {
        // given
        String password = "1234";

        // when
        boolean result = testWriterService.checkPassword(password, password);

        // then
        assertTrue(result);
    }

    @Test
    public void shouldThrowExceptionIfPasswordIsInvalid() {
        // given
        String password1 = "1234";
        String password2 = "12345";

        assertAll(
                () -> {
                    var exception = assertThrows(ValidException.class,
                            () -> testWriterService.checkPassword(password1, password2));
                    assertThat(exception.getErrors().get(0).getMassage()).isEqualTo("Passwords are not same");
                }
        );
    }
}
