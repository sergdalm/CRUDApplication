package repository.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.Writer;
import repository.WriterRepository;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonWriterRepositoryImpl implements WriterRepository {
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final Path path = Path.of("src", "main", "resources", "writers.json");
    private Map<Integer, Writer> writers;
    private int idCount;

    public JsonWriterRepositoryImpl() {
        try {
            String json = Files.readString(path);
            Type type = new TypeToken<Map<Integer, Writer>>(){}.getType();
            Map<Integer, Writer> jsonMap = gson.fromJson(json, type);
            if(jsonMap == null) {
                writers = new HashMap<>();
            }
            else {
                writers = jsonMap;
                idCount = writers.values().stream()
                        .mapToInt(Writer::getId)
                        .summaryStatistics()
                        .getMax();
                idCount++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public Writer getById(Integer id) {
        return writers.get(id);
    }

    @Override
    public Writer save(Writer writer) {
        writer.setId(idCount++);
        return writers.put(writer.getId(), writer);
    }

    @Override
    public Writer update(Writer obj) {
        return null;
    }

    @Override
    public List<Writer> getAll() {
        return null;
    }

    @Override
    public void deleteById(Integer aLong) {

    }

    private void saveRepository() {

    }
}
