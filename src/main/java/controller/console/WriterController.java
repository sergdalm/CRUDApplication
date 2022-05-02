package controller.console;

import dto.LoginWriterDto;
import dto.WriterDto;
import exceptions.LoginErrorException;
import model.Post;
import model.Writer;
import repository.WriterRepository;
import repository.gson.JsonWriterRepositoryImpl;
import service.WriterService;
import view.PostView;

import java.util.List;


public class WriterController {
    WriterService writerService = WriterService.getInstance();


    public LoginWriterDto saveWriter(String firstName, String lastName, String email, String password) {
        LoginWriterDto writer = LoginWriterDto.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .password(password)
                .build();

        return  writerService.createWriter(writer);
    }

    public WriterDto getWriterById(Integer id) {
        return writerService.getWriterById(id);
    }

    public LoginWriterDto loginWriter(String email, String password) throws LoginErrorException {
        return writerService.loginWriter(email, password);
    }

    public String getWriterFullName(Integer id) {
        var writer = writerService.getWriterById(id);
        return writer.getFullName();
    }

    public List<WriterDto> getAllWriters() {
        return writerService.getAllWriters();
    }

    public void update(Integer id, String firstName, String lastName) {
        writerService.update(LoginWriterDto.builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .build());
    }

    public void deleteWriterById(Integer id) {
        writerService.delete(id);
    }
}
