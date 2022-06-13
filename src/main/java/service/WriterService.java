package service;

import dto.LoginWriterDto;
import dto.WriterDto;
import exceptions.LoginErrorException;
import exceptions.ValidException;
import model.Writer;
import repository.WriterRepository;
import validator.CreateWriterValidator;
import validator.ValidationResult;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class WriterService {
    private final WriterRepository writerRepository;
    private final CreateWriterValidator createWriterValidator = CreateWriterValidator.getInstance();

    public WriterService(WriterRepository writerRepository) {
        this.writerRepository = writerRepository;
    }

    public WriterDto getWriterById(Integer id) {
        var writer = writerRepository.getById(id);
        return WriterDto.fromEntity(writer);

    }

    public LoginWriterDto createWriter(LoginWriterDto loginWriterDto) {
        ValidationResult validationResult = createWriterValidator.isValid(loginWriterDto);
        if (!validationResult.isValid()) {
            throw new ValidException(validationResult.getErrors());
        }

        Writer writer = writerRepository.save(loginWriterDto.toEntity());
        return LoginWriterDto.fromEntity(writer);
    }

    public List<WriterDto> getAllWriters() {
        return writerRepository.getAll().stream()
                .map(WriterDto::fromEntity)
                .collect(toList());
    }

    public LoginWriterDto loginWriter(String email, String password) throws LoginErrorException {
        var maybeWriter = writerRepository.getWriterByEmail(email);
        if (maybeWriter.isEmpty()) {
            throw new LoginErrorException("Email does not exist");
        } else {
            var writer = maybeWriter.get();
            if (writer.getPassword().equals(password)) {
                return LoginWriterDto.fromEntity(writer);
            } else {
                throw new LoginErrorException("Password is invalid");
            }
        }
    }

    public void update(LoginWriterDto loginWriterDto) {
        writerRepository.update(loginWriterDto.toEntity());
    }

    public void delete(Integer id) {
        writerRepository.deleteById(id);
    }

    public boolean checkPassword(String firstPassword, String secondPassword) {
        ValidationResult validationResult = createWriterValidator.checkPassword(firstPassword, secondPassword);
        if (!validationResult.isValid()) {
            throw new ValidException(validationResult.getErrors());
        }
        return true;
    }
}
