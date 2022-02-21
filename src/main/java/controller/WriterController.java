package controller;

import model.Post;
import model.Writer;
import repository.PostRepository;
import repository.WriterRepository;
import repository.gson.JsonPostRepositoryImpl;
import repository.gson.JsonWriterRepositoryImpl;
import view.PostView;

import java.util.ArrayList;
import java.util.List;


public class WriterController {
    private final WriterRepository writerRepository;
    private final PostView postView;

    public WriterController() {
        writerRepository = new JsonWriterRepositoryImpl();
        postView = new PostView();
    }

    public Writer saveWriter(String firstName, String lastName) {
        Writer writer = new Writer(firstName, lastName, new ArrayList<>());
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

    public void addNewPost(Integer id, Post post) {
        Writer writer = getWriterById(id);
        List<Post> posts = writer.getPosts();
        posts.add(post);
        writer.setPosts(posts);
    }

    public Writer saveIfNotExisting(String firstName, String lastName) {
        if(writerRepository.isExisting(firstName, lastName)){
            return writerRepository.getWriterByName(firstName, lastName);
        }
        else  {
            return saveWriter(firstName, lastName);
        }
    }

    public String getAllPostsByWriterId(Integer id) {
        List<Post> writerPosts = writerRepository.getById(id).getPosts();
        postView.showWriterPost(writerPosts);
        return "";

    }
}
