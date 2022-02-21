package repository.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.Writer;
import repository.WriterRepository;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class JsonWriterRepositoryImpl implements WriterRepository {
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final Path path = Path.of("src", "main", "resources", "writers.json");

    public JsonWriterRepositoryImpl() {
    }

    @Override
    public Writer getById(Integer id) {
        List<Writer> currentWriters = getAllWriters();
        return currentWriters.stream()
                .filter(writer -> writer.getId().equals(id))
                .findFirst()
                .orElse(null);

    }

    @Override
    public Writer save(Writer writer) {
        List<Writer> currentWriters = getAllWriters();
        Integer newId = generateMaxId(currentWriters);
        writer.setId(newId);
        currentWriters.add(writer);
        writeAllWriters(currentWriters);
        return writer;
    }

    @Override
    public Writer update(Writer updatedWriter) {
        List<Writer> currentWriters = getAllWriters();
        currentWriters.forEach(w -> {
            if(w.getId().equals(updatedWriter.getId())) {
                w.setLastName(updatedWriter.getFirstName());
                w.setLastName(updatedWriter.getLastName());
            }
        });
        writeAllWriters(currentWriters);
        return null;
    }

    @Override
    public List<Writer> getAll() {
        return getAllWriters();
    }

    @Override
    public void deleteById(Integer id) {
        List<Writer> currentWriters = getAllWriters();
        currentWriters.removeIf(writer -> writer.getId().equals(id));
        writeAllWriters(currentWriters);
    }

    private List<Writer> getAllWriters() {
        List<Writer> writers;
        try {
            String json = Files.readString(path);
            Type type = new TypeToken<ArrayList<Writer>>(){}.getType();
            writers = gson.fromJson(json, type);
        } catch (IOException exc) {
            writers = new ArrayList<>();
        }
        return Objects.isNull(writers) ? new ArrayList<>() : writers;
    }

    private void writeAllWriters(List<Writer> writers) {
        String json = gson.toJson(writers);

        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(path.toFile()))) {
            fileWriter.append(json);
            fileWriter.append('\n');
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Integer generateMaxId(List<Writer> writers) {
        if(Objects.isNull(writers)) {
            return 1;
        }
        else {
            int maxId = writers.stream()
                    .mapToInt(Writer::getId)
                    .max()
                    .orElse(0);
            return maxId + 1;
        }
    }


}
