package service;

import dto.LabelDto;
import dto.PostDto;
import model.Label;
import model.Post;
import repository.PostRepository;
import repository.postgres.PostgresPostRepository;

import java.util.List;

import static java.util.stream.Collectors.*;

public class PostService {
    private final static PostService INSTANCE = new PostService();
    private final static PostRepository postRepository = PostgresPostRepository.getInstance();
    private final static LabelService labelService = LabelService.getInstance();

    private PostService() {
    }

    public List<PostDto> getAllPosts() {
        return postRepository.getAll().stream()
                .map(this::buildPostFromPostDto)
                .collect(toList());
    }

    public List<PostDto> getAllPostsByWriterId(Integer writerId) {
        return postRepository.getPostsByWriterId(writerId).stream()
                .map(this::buildPostFromPostDto)
                .collect(toList());
    }

    public PostDto save(PostDto postDto, Integer writerId) {
        var newPost = postRepository.save(new Post(0, postDto.getTitle(), postDto.getContent(),
                null, null, postDto.getLabels().stream()
                .map(labelDto -> new Label(labelDto.getId(), labelDto.getName()))
                .collect(toList())));

        postRepository.matchPostWithWriter(newPost.getId(), writerId);
        postRepository.matchPostWithLabels(postDto.getId(), newPost.getLabels());
        return buildPostFromPostDto(newPost);
    }

    public boolean delete(Integer id) {
        return postRepository.deleteById(id);
    }

    public PostDto getById(Integer postId) {
        var post = postRepository.getById(postId);
        return buildPostFromPostDto(post);

    }

    public PostDto update(PostDto postDto) {
        var post = postRepository.update(new Post(postDto.getId(), postDto.getTitle(), postDto.getContent(), null, null,
                postDto.getLabels().stream()
                        .map(labelDto -> new Label(labelDto.getId(), labelDto.getName()))
                        .collect(toList())));
        return buildPostFromPostDto(post);
    }


    public static PostService getInstance() {
        return INSTANCE;
    }

    private PostDto buildPostFromPostDto(Post post) {
        return PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .created(post.getCreated())
                .updated(post.getUpdated())
                .labels(post.getLabels().stream()
                        .map(label -> LabelDto.builder()
                                .id(label.getId())
                                .name(label.getName())
                                .build())
                        .collect(toList()))
                .build();
    }
}
