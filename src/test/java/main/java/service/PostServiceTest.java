package main.java.service;

import dto.LabelDto;
import dto.PostDto;
import model.Label;
import model.Post;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.PostRepository;
import service.PostService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {
    @Mock
    private PostRepository mockPostRepository;
    @InjectMocks
    private PostService testPostService;
    private static final List<Post> POST_ENTITIES = List.of(
            new Post(1, "Post1", "Content of this post", LocalDateTime.now(), null, null),
            new Post(2, "Post2", "Content of this post", LocalDateTime.now(), null, null),
            new Post(3, "Post3", "Content of this post", LocalDateTime.now(), null, null),
            new Post(4, "Post4", "Content of this post", LocalDateTime.now(), null, null)
    );

    @Test
    public void shouldGetAllPosts() {
        // given


        doReturn(POST_ENTITIES).when(mockPostRepository).getAll();

        // when
        List<PostDto> allPostsResult = testPostService.getAllPosts();

        // then
        for (int i = 0; i < POST_ENTITIES.size(); i++) {
            assertEquals(PostDto.fromEntity(POST_ENTITIES.get(i)), allPostsResult.get(i));
        }
    }

    @Test
    public void shouldGetAllPostsByWriterId() {
        // given
        int writerId = 1;
        doReturn(POST_ENTITIES).when(mockPostRepository).getPostsByWriterId(writerId);

        // when
        List<PostDto> allPostsByWriterIdResult = testPostService.getAllPostsByWriterId(writerId);

        // then
        ArgumentCaptor<Integer> writerIdArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(mockPostRepository).getPostsByWriterId(writerIdArgumentCaptor.capture());
        Integer capturedWriterId = writerIdArgumentCaptor.getValue();

        assertEquals(capturedWriterId, writerId);
        for (int i = 0; i < POST_ENTITIES.size(); i++) {
            assertEquals(PostDto.fromEntity(POST_ENTITIES.get(i)),
                    allPostsByWriterIdResult.get(i));
        }
    }

    @Test
    public void shouldSavePostAndReturnSavedPost() {
        // when
        PostDto newPostDto = PostDto.builder()
                .title("Post title")
                .content("Post content")
                .labels(List.of(LabelDto.builder()
                        .id(1)
                        .name("post")
                        .build()
                ))
                .build();
        int writerId = 1;

        Post newPost = new Post(1, newPostDto.getTitle(), newPostDto.getContent(),
                LocalDateTime.now(), null, List.of(new Label(1, "post")));

        doReturn(newPost).when(mockPostRepository).save(newPostDto.toEntity());

        // when
        PostDto saveResult = testPostService.save(newPostDto, writerId);

        // then
        ArgumentCaptor<Post> postArgumentCaptor = ArgumentCaptor.forClass(Post.class);
        verify(mockPostRepository).save(postArgumentCaptor.capture());
        Post capturedPost = postArgumentCaptor.getValue();

        ArgumentCaptor<Integer> matchPostWithWriterArgumentCaptor =
                ArgumentCaptor.forClass(Integer.class);
        verify(mockPostRepository).matchPostWithWriter(
                matchPostWithWriterArgumentCaptor.capture(),
                matchPostWithWriterArgumentCaptor.capture());
        List<Integer> capturedPostIdAndWriterId = matchPostWithWriterArgumentCaptor.getAllValues();

        assertEquals(newPostDto.toEntity(), capturedPost);
        assertEquals(newPost.getId(), capturedPostIdAndWriterId.get(0));
        assertEquals(writerId, capturedPostIdAndWriterId.get(1));
        assertEquals(PostDto.fromEntity(newPost), saveResult);
    }

    @Test
    public void shouldDeletePostAndReturnTrue() {
        // given
        int postId = 1;
        doReturn(true).when(mockPostRepository).deleteById(postId);

        // when
        boolean deleteResult = testPostService.delete(postId);

        // then
        ArgumentCaptor<Integer> postIdArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(mockPostRepository).deleteById(postIdArgumentCaptor.capture());
        Integer capturedPostId = postIdArgumentCaptor.getValue();

        assertEquals(postId, capturedPostId);
        assertTrue(deleteResult);
    }

    @Test
    public void shouldGetPostDtoByPostId() {
        // given
        Post post = new Post(1, "Post title", "Post content", LocalDateTime.now(),
                null, List.of(new Label(1, "post")));
        doReturn(post).when(mockPostRepository).getById(post.getId());

        // when
        PostDto postDtoResult = testPostService.getById(post.getId());

        // then
        ArgumentCaptor<Integer> postIdArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(mockPostRepository).getById(postIdArgumentCaptor.capture());
        Integer capturedPostId = postIdArgumentCaptor.getValue();

        assertEquals(post.getId(), capturedPostId);
        assertEquals(PostDto.fromEntity(post), postDtoResult);
    }

    @Test
    public void shouldUpdatePostAndReturnUpdatedPost() {
        // given
        PostDto updatedPostDto = PostDto.builder()
                .id(1)
                .title("Post title")
                .content("Post content")
                .labels(Collections.emptyList())
                .created(LocalDateTime.now())
                .build();

        Post updatedPost = new Post(
                updatedPostDto.getId(),
                updatedPostDto.getTitle(),
                updatedPostDto.getContent(),
                updatedPostDto.getCreated(),
                LocalDateTime.now(),
                Collections.emptyList()
        );

        doReturn(updatedPost).when(mockPostRepository).update(updatedPostDto.toEntity());

        // when
        PostDto updateResult = testPostService.update(updatedPostDto);

        // then
        ArgumentCaptor<Post> updatedPostArgumentCaptor = ArgumentCaptor.forClass(Post.class);
        verify(mockPostRepository).update(updatedPostArgumentCaptor.capture());
        Post capturedPost = updatedPostArgumentCaptor.getValue();

        assertEquals(updatedPostDto.toEntity(), capturedPost);
        assertEquals(PostDto.fromEntity(updatedPost), updateResult);
    }
}
