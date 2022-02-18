
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.util.ISO8601Utils;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class LabelRepository implements GenericRepository<Label, Integer>, Serializable {
    private Map<Integer, Label> labels = new HashMap<>();
    //private Gson gson = new Gson();
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    Path path = Path.of("src", "main", "resources", "labels.json");

    @Override
    public Label getById(Integer id) {
        return labels.get(id);
    }

    @Override
    public Label save(Label label) {
        labels.put(label.getId(), label);
        String json = gson.toJson(labels);

        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(path.toFile()))) {
            fileWriter.append(json);
            fileWriter.append('\n');
        } catch (IOException e) {
            e.printStackTrace();
        }


        return label;
    }

    @Override
    public Label update(Label updatedLabel) {
        if(!labels.containsKey(updatedLabel.getId()))
            return null;
        else
            return labels.replace(updatedLabel.getId(), updatedLabel);
    }

    @Override
    public Map<Integer, Label> getAll() {
        return labels;
    }

    @Override
    public void deleteById(Integer id) {

        try{
            String json = Files.readString(path);
            Label read = gson.fromJson(json, Label.class);
            System.out.println(read);
            System.out.println(read);
        } catch (IOException exc) {
            exc.printStackTrace();
        }

    }
}