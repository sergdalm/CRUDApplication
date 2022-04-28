package service;

import dto.LoginWriterDto;
import dto.WriterDto;
import exceptions.LoginErrorException;
import model.Writer;
import repository.WriterRepository;
import repository.postgres.PostgresWriterRepository;

import java.util.List;
import java.util.stream.Collectors;

public class WriterService {
    private static final WriterService INSTANCE = new WriterService();
    private final WriterRepository writerRepository = PostgresWriterRepository.getInstance();

    private WriterService() {
    }

    public WriterDto getWriterById(Integer id) {
        return null;
    }

    public LoginWriterDto createWriter(LoginWriterDto createWriterDto) {
        return createWriterDto;
    }

    public List<WriterDto> getAllWriters() {
        return writerRepository.getAll().stream()
                .map(writer -> WriterDto.builder()
                        .id(writer.getId())
                        .firstName(writer.getFirstName())
                        .lastName(writer.getLastName())
                        .build())
                .collect(Collectors.toList());
    }

    public LoginWriterDto loginWriter(String email, String password) throws LoginErrorException {
        var maybeWriter = writerRepository.getWriterByEmail(email);
        if(maybeWriter.isEmpty()) {
            throw new LoginErrorException("Email does not exist");
        }
        else {
            var writer = maybeWriter.get();
            if(writer.getPassword().equals(password)) {
                return LoginWriterDto.builder()
                        .id(writer.getId())
                        .firstName(writer.getFirstName())
                        .lastName(writer.getLastName())
                        .email(writer.getEmail())
                        .password(writer.getPassword())
                        .build();
            }
            else {
                throw new LoginErrorException("Password is invalid");
            }
        }
    }

    public static WriterService getInstance() {
        return INSTANCE;
    }

    public void update(WriterDto writerDto) {
        writerRepository.update(new Writer(writerDto.getId(),
                writerDto.getFirstName(), writerDto.getLastName(), null, null, null));
    }

    public void delete(Integer id) {
        writerRepository.deleteById(id);
    }
}