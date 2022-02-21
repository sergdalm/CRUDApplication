package controller;

import model.Writer;
import repository.WriterRepository;
import repository.gson.JsonWriterRepositoryImpl;

import java.util.List;

// ADD METHODS WITH POSTS!!!
public class WriterController {
    private final WriterRepository writerRepository = new JsonWriterRepositoryImpl();

    public WriterController() {
    }

    public Writer saveWriter(String firstName, String lastName) {
        Writer writer = new Writer();
        writer.setFirstName(firstName);
        writer.setLastName(lastName);
        return writerRepository.save(writer);
    }

    public Writer getWriterById(Integer id) {
        return writerRepository.getById(id);
    }

    public Writer update(Writer writer) {
        return writerRepository.update(writer);
    }

    public Writer update(Integer id, String firstName, String lastName) {
        Writer writer = writerRepository.getById(id);
        writer.setFirstName(firstName);
        writer.setLastName(lastName);
        return writerRepository.update(writer);
    }

    public String getWriterFullName(Integer id) {
        Writer writer = writerRepository.getById(id);
        return writer.getFullName();
    }

    public String getAllWriterIdAndNames() {
        List<Writer> writers = writerRepository.getAll();
        StringBuilder idAndNames = new StringBuilder();
        for(Writer writer : writers) {
            idAndNames.append(writer.getIdAndFullName());
            idAndNames.append("\n");
        }
        return idAndNames.toString();
    }

    public Integer writersCount() {
        return writerRepository.getAll().size();
    }

    public void deleteWriterById(Integer id) {
        writerRepository.deleteById(id);
    }
}
