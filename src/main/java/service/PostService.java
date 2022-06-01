package service;

import dto.LabelDto;
import dto.PostDto;
import model.Label;
import model.Post;
import repository.PostRepository;
import repository.postgres.PostgresLabelRepository;
import repository.postgres.PostgresPostRepository;

import java.util.List;

import static java.util.stream.Collectors.*;

public class PostService {
    private final static PostService INSTANCE = new PostService();
    private final static PostRepository postRepository = PostgresPostRepository.getInstance();
    private final static LabelService labelService = new LabelService(PostgresLabelRepository.getInstance());

    private PostService() {
    }

    public List<PostDto> getAllPosts() {
        return postRepository.getAll().stream()
                .map(PostDto::fromEntity)
                .collect(toList());
    }

    public List<PostDto> getAllPostsByWriterId(Integer writerId) {
        return postRepository.getPostsByWriterId(writerId).stream()
                .map(PostDto::fromEntity)
                .collect(toList());
    }

    public PostDto save(PostDto postDto, Integer writerId) {
        var newPost = postRepository.save(postDto.toEntity());

        postRepository.matchPostWithWriter(newPost.getId(), writerId);
        postRepository.matchPostWithLabels(postDto.getId(), newPost.getLabels());
        return PostDto.fromEntity(newPost);
    }

    public boolean delete(Integer id) {
        return postRepository.deleteById(id);
    }

    public PostDto getById(Integer postId) {
        var post = postRepository.getById(postId);
        return PostDto.fromEntity(post);

    }

    public PostDto update(PostDto postDto) {
        var post = postRepository.update(new Post(postDto.getId(), postDto.getTitle(), postDto.getContent(), null, null,
                postDto.getLabels().stream()
                        .map(labelDto -> new Label(labelDto.getId(), labelDto.getName()))
                        .collect(toList())));
        return PostDto.fromEntity(post);
    }


    public static PostService getInstance() {
        return INSTANCE;
    }
}
