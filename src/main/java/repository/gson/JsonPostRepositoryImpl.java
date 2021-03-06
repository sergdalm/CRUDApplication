package repository.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.Label;
import model.Post;
import repository.PostRepository;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class JsonPostRepositoryImpl implements PostRepository {
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final Path path = Path.of("src", "main", "resources", "posts.json");
    @Override
    public Post getById(Integer id) {
        return getAll().stream()
                .filter(l -> l.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Post save(Post post) {
        List<Post> currentPosts = getAll();
        Integer newId = generateMaxId(currentPosts);
        post.setId(newId);
        currentPosts.add(post);
        writeAllPosts(currentPosts);
        return post;
    }

    @Override
    public Post update(Post updatedPost) {
        List<Post> currentPosts = getAll();
        currentPosts.forEach(post -> {
            if(post.getId().equals(updatedPost.getId())) {
                post.setContent(updatedPost.getContent());
                post.setUpdated(LocalDateTime.now());
            }
        });
        writeAllPosts(currentPosts);
        return updatedPost;
    }

    @Override
    public List<Post> getAll() {
        List<Post> posts;
        try {
            String json = Files.readString(path);
            Type type = new TypeToken<ArrayList<Post>>(){}.getType();
            posts = gson.fromJson(json, type);
        } catch (IOException exc) {
            posts = new ArrayList<>();
        }
        return Objects.isNull(posts) ? new ArrayList<>() : posts;
    }

    @Override
    public boolean deleteById(Integer id) {
        List<Post> currentPosts = getAll();
        currentPosts.removeIf(post -> post.getId().equals(id));
        writeAllPosts(currentPosts);
        return true;
    }

    @Override
    public List<Post> getPostsByWriterId(Integer writerId) {
        return null;
    }

    private void writeAllPosts(List<Post> posts) {
        String json = gson.toJson(posts);

        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(path.toFile()))) {
            fileWriter.append(json);
            fileWriter.append('\n');
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void matchPostWithWriter(Integer postId, Integer writerId) {

    }

    @Override
    public void matchPostWithLabels(Integer postId, List<Label> labels) {

    }

    private Integer generateMaxId(List<Post> posts) {
        if(Objects.isNull(posts)) {
            return 1;
        }
        else {
            int maxId = posts.stream()
                    .mapToInt(Post::getId)
                    .max()
                    .orElse(0);
            return maxId + 1;
        }
    }
}
