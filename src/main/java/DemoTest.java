import com.google.gson.Gson;

import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;

public class DemoTest {
    public static void main(String[] args) throws HeadlessException {
        LabelRepository labelRepository = new LabelRepository();

        Label label = new Label("Hi");
        labelRepository.save(label);

//        labelRepository.deleteById(0);

    }
}
