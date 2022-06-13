package service;

import dto.PostDto;
import repository.PostRepository;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
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
        var post = postRepository.update(postDto.toEntity());
        return PostDto.fromEntity(post);
    }
}
