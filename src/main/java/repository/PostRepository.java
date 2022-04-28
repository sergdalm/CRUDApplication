package repository;

import model.Label;
import model.Post;

import java.util.List;

public interface PostRepository extends GenericRepository<Post, Integer> {
    List<Post> getPostsByWriterId(Integer writerId);
    void matchPostWithWriter(Integer postId, Integer writerId);
    void matchPostWithLabels(Integer postId, List<Label> labels);
}
