
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.util.ISO8601Utils;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
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

        // Save into labels.json
        String json = gson.toJson(label, Label.class);
        try(OutputStream fout = new BufferedOutputStream(
                Files.newOutputStream(path))) {
            fout.write(json.getBytes());
        } catch (InvalidPathException e) {
            System.out.println("Path Error " + e);
        } catch (IOException e) {
            System.out.println("I/O Error: " + e);
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
//        if(labels.remove(id) == null)
//            throw new NoSuchElementException();
//        StringBuilder stringBuilder = new StringBuilder();
//        inc count = 0;
//        try(FileReader fr = new FileReader("src/main/resources/labels.json")) {
//            int c;
//            while((c = fr.read()) != -1)
//                stringBuilder.insert(count++, c)
//        } catch (IOException e) {
//            System.out.println("I/O Error: " + e);
//        }
//        String json =
//        Label read = gson.fromJson(path, Label.class);
    }
}