package controller;

import model.Label;
import model.Post;
import repository.PostRepository;
import repository.gson.JsonPostRepositoryImpl;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

public class PostController {
    PostRepository postRepository = new JsonPostRepositoryImpl();
     public PostController() {
     }

     public Post savePost(String content, List<Label> labels) {
         Post post = new Post(content, labels);
         return postRepository.save(post);
     }

     public Post getPostById(Integer id) {
         return postRepository.getById(id);
     }

     public String getPostContent(Integer id) {
         Post post = getPostById(id);
         return post.getContent();
     }

     public Post updatePost(Integer id, String content, List<Label> labels) {
         Post updatedPost = getPostById(id);
         updatedPost.setContent(content);
         updatedPost.setUpdated(Instant.now());
         updatedPost.setLabels(labels);
         return postRepository.update(updatedPost);
     }

     public String getAllPosts() {
         List<Post> posts = postRepository.getAll();
         StringBuilder result = new StringBuilder();
         for(Post post : posts) {
             result.append(post.toString());
             result.append("\n");
         }
         return result.toString();
     }

     public String getLabelsAsStringSeparatedByComma(Integer postId) {
         List<Label> labels = getPostById(postId).getLabels();
         String result = labels.stream()
                 .map(Label::getName)
                 .collect(Collectors.joining(", "));
         return result;
     }

    public String getWriterPost(List<Post> writerPosts) {
         List<Post> posts = postRepository.getAll();
         StringBuilder stringBuilder = new StringBuilder("");
         for(Post post : writerPosts) {
             Post pustResult = posts.stream()
                     .filter(p -> p.getId().equals(post.getId()))
                     .findFirst()
                     .orElse(null);

             stringBuilder.append(pustResult.toString());
         }
         return stringBuilder.toString();

    }
}
