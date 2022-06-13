package controller.console;

import dto.LabelDto;
import dto.PostDto;
import repository.postgres.PostgresPostRepository;
import service.PostService;

import java.util.List;

public class PostController {
    private static final PostController INSTANCE = new PostController();

    private final PostService postService = new PostService(PostgresPostRepository.getInstance());

    private PostController() {
    }

    public List<PostDto> getAllPosts() {
        return postService.getAllPosts();
    }

    public List<PostDto> getPostsByWriterId(Integer writerId) {
        return postService.getAllPostsByWriterId(writerId);
    }

    public PostDto savePost(Integer writerId, String title, String content, List<LabelDto> labels) {
        PostDto post = PostDto.builder()
                .title(title)
                .content(content)
                .labels(labels)
                .build();
        postService.save(post, writerId);

        return null;
    }

    public PostDto getPostById(Integer id) {
        return postService.getById(id);
    }

    public PostDto updatePost(Integer postId, String title, String content, List<LabelDto> labels) {
        return postService.update(PostDto.builder()
                .id(postId)
                .title(title)
                .content(content)
                .labels(labels)
                .build());
    }

    public boolean deletePost(Integer id) {
        return postService.delete(id);
    }

    public static PostController getInstance() {
        return INSTANCE;
    }
}
