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

    private List<Post> getPostEntityList() {
        return List.of(
                new Post(1, "Post1", "Content of this post", LocalDateTime.now(), null, null),
                new Post(2, "Post2", "Content of this post", LocalDateTime.now(), null, null),
                new Post(3, "Post3", "Content of this post", LocalDateTime.now(), null, null),
                new Post(4, "Post4", "Content of this post", LocalDateTime.now(), null, null));
    }


    private Post getPostEntity() {
        return new Post(1, "Post title", "Post content", LocalDateTime.now(),
                null, List.of(new Label(1, "post")));
    }

    private Post getUpdatedPostEntity() {
        return new Post(
                getPostDto().getId(),
                getPostDto().getTitle(),
                getPostDto().getContent(),
                getPostDto().getCreated(),
                LocalDateTime.now(),
                List.of(new Label(1, "post")));
    }

    private PostDto getPostDto() {
        return PostDto.builder()
                .id(1)
                .title("Post title")
                .content("Post content")
                .labels(Collections.emptyList())
                .created(LocalDateTime.now())
                .build();
    }

    private PostDto getNewPostDto() {
        return PostDto.builder()
                .title("Post title")
                .content("Post content")
                .labels(List.of(LabelDto.builder()
                        .id(1)
                        .name("post")
                        .build()))
                .build();
    }

    private int getPostId() {
        return getPostDto().getId();
    }

    private int getWriterId() {
        return 1;
    }

    @Test
    public void shouldGetAllPosts() {
        List<Post> posts = getPostEntityList();
        doReturn(posts).when(mockPostRepository).getAll();

        List<PostDto> allPostsResult = testPostService.getAllPosts();

        for (int i = 0; i < posts.size(); i++) {
            assertEquals(PostDto.fromEntity(posts.get(i)), allPostsResult.get(i));
        }
    }

    @Test
    public void shouldGetAllPostsByWriterId() {
        List<Post> posts = getPostEntityList();
        doReturn(posts).when(mockPostRepository).getPostsByWriterId(getWriterId());

        List<PostDto> allPostsByWriterIdResult = testPostService.getAllPostsByWriterId(getWriterId());

        ArgumentCaptor<Integer> writerIdArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(mockPostRepository).getPostsByWriterId(writerIdArgumentCaptor.capture());
        Integer capturedWriterId = writerIdArgumentCaptor.getValue();

        assertEquals(capturedWriterId, getWriterId());
        for (int i = 0; i < posts.size(); i++) {
            assertEquals(PostDto.fromEntity(posts.get(i)),
                    allPostsByWriterIdResult.get(i));
        }
    }

    @Test
    public void shouldSavePostAndReturnSavedPost() {
        Post postEntity = getPostEntity();
        doReturn(postEntity).when(mockPostRepository).save(getNewPostDto().toEntity());

        PostDto saveResult = testPostService.save(getNewPostDto(), getWriterId());

        ArgumentCaptor<Post> postArgumentCaptor = ArgumentCaptor.forClass(Post.class);
        verify(mockPostRepository).save(postArgumentCaptor.capture());
        Post capturedPost = postArgumentCaptor.getValue();

        ArgumentCaptor<Integer> matchPostWithWriterArgumentCaptor =
                ArgumentCaptor.forClass(Integer.class);
        verify(mockPostRepository).matchPostWithWriter(
                matchPostWithWriterArgumentCaptor.capture(),
                matchPostWithWriterArgumentCaptor.capture());
        List<Integer> capturedPostIdAndWriterId = matchPostWithWriterArgumentCaptor.getAllValues();

        assertEquals(getNewPostDto().toEntity(), capturedPost);
        assertEquals(postEntity.getId(), capturedPostIdAndWriterId.get(0));
        assertEquals(getWriterId(), capturedPostIdAndWriterId.get(1));
        assertEquals(PostDto.fromEntity(postEntity), saveResult);
    }

    @Test
    public void shouldDeletePostAndReturnTrue() {
        doReturn(true).when(mockPostRepository).deleteById(getPostId());

        boolean deleteResult = testPostService.delete(getPostId());

        ArgumentCaptor<Integer> postIdArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(mockPostRepository).deleteById(postIdArgumentCaptor.capture());
        Integer capturedPostId = postIdArgumentCaptor.getValue();

        assertEquals(getPostId(), capturedPostId);
        assertTrue(deleteResult);
    }

    @Test
    public void shouldGetPostDtoByPostId() {
        Post postEntity = getPostEntity();
        doReturn(postEntity).when(mockPostRepository).getById(getPostEntity().getId());

        PostDto postDtoResult = testPostService.getById(getPostEntity().getId());

        ArgumentCaptor<Integer> postIdArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(mockPostRepository).getById(postIdArgumentCaptor.capture());
        Integer capturedPostId = postIdArgumentCaptor.getValue();

        assertEquals(postEntity.getId(), capturedPostId);
        assertEquals(PostDto.fromEntity(postEntity), postDtoResult);
    }

    @Test
    public void shouldUpdatePostAndReturnUpdatedPost() {
        PostDto postDto = getPostDto();
        Post postEntity = postDto.toEntity();
        Post updatedPostEntity = getUpdatedPostEntity();
        doReturn(updatedPostEntity).when(mockPostRepository).update(postEntity);

        PostDto updateResult = testPostService.update(postDto);

        ArgumentCaptor<Post> updatedPostArgumentCaptor = ArgumentCaptor.forClass(Post.class);
        verify(mockPostRepository).update(updatedPostArgumentCaptor.capture());
        Post capturedPost = updatedPostArgumentCaptor.getValue();

        assertEquals(postEntity, capturedPost);
        assertEquals(PostDto.fromEntity(updatedPostEntity), updateResult);
    }
}
