package view;

import controller.console.LabelController;
import controller.console.PostController;
import dto.LabelDto;
import dto.PostDto;
import model.Label;
import model.Post;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PostView {
    private final PostController postController;
    private final InputManager inputManager;
    private final LabelView labelView;
    private final String dateTimeFormat = "dd.MM.yyyy hh:mm:ss";

    public PostView() {
        postController = PostController.getInstance();
        inputManager = InputManager.getInstance();
        labelView = new LabelView();
    }

    public void userPostsMenu(Integer writerId) {
        int input;
        do {
            System.out.println("Enter number (0 for back)");
            System.out.println("  1. See your posts");
            System.out.println("  2. Write new post");
            input = inputManager.getNumberFromUserBetweenMinAndMax(0, 2);
            switch (input) {
                case(1) :
                    var postId = showPostsByWriterId(writerId);
                    if(postId > 0)
                        postMenu(postId);
                    break;
                case(2) :
                    createPost(writerId);
                    break;
            }
        } while(input != 0);
    }

    public PostDto createPost(Integer writerId) {
        String title = inputPostTitle();
        String content = inputPostContent();
        List<LabelDto> labels = setLabels();
        return postController.savePost(writerId, title, content, labels);
    }

    public Integer showPostsByWriterId(Integer writerId) {
        var posts = postController.getPostsByWriterId(writerId);
        if(posts.size() == 0) {
            System.out.println("There are no posts yet.");
            return 0;
        } else {
            showPostsTitles(posts);
            System.out.println("Enter post's number to see its content: (0 for back)");
            int postsAmount = posts.size();
            int postNumber = inputManager.getNumberFromUserBetweenMinAndMax(0, postsAmount + 1);
            if(postNumber == 0) {
                return 0;
            }
            Integer postId = posts.get(postNumber - 1).getId();
            showPost(postId);
            return postId;
        }
    }

    private void postMenu(Integer postId) {
        int input;
        do {
            System.out.println("\nChose number from menu: (0 for back)");
            System.out.println("1. Change post");
            System.out.println("2. Delete post");
            input = inputManager.getNumberFromUserBetweenMinAndMax(0, 2);
            switch (input) {
                case(1):
                    updatePost(postId);
                    break;
                case(2):
                    deletePost(postId);
                    break;
            }
        } while(input != 0);
    }

    public void deletePost(Integer id) {
         if(postController.deletePost(id)) {
             System.out.println("Post has been deleted.");
        }
    }

    public void showPost(Integer id) {
        var post = postController.getPostById(id);
        System.out.println(post.getTitle());
        System.out.println(post.getContent());
        System.out.println("Post created: " + post.getCreated().format(DateTimeFormatter.ofPattern(dateTimeFormat)));
        if(post.getUpdated() != null) {
            System.out.println("Post updated: " + post.getUpdated().format(DateTimeFormatter.ofPattern(dateTimeFormat)));
        }
        if(post.getLabels() != null && post.getLabels().size() > 0) {
            System.out.print("Post labels: ");
            labelView.showLabelsForPost(post.getId());
        }
        System.out.println();
    }

    public void showAllPosts() {
        System.out.println("Chose a post you want to read: (0 for back)");
        var allPosts = postController.getAllPosts();

        showPostsTitles(allPosts);
        int input;
        do {
            input = inputManager.getNumberFromUserBetweenMinAndMax(0, allPosts.size());
            if(input != 0) {
                showPost(allPosts.get(input - 1).getId());
            }
        } while(input != 0);

    }

    public void showPostsTitles(List<PostDto> posts) {
        int count = 1;
        for (PostDto post : posts) {
            System.out.println(count++ + ". " + post.getTitle());
        }
    }

    private void updatePost(Integer postId) {
        String newTitle = inputPostTitle();
        String newContent = inputPostContent();
        List<LabelDto> labels = setLabels();
        postController.updatePost(postId, newTitle, newContent, labels);
        System.out.println("Post has been updated.");
        showPost(postId);
    }

    private String inputPostTitle() {
        System.out.println("Write post's title and press \"Enter\":");
        return inputManager.inputText();
    }

    private String inputPostContent() {
        System.out.println("Write post and press \"Enter\":");
        return inputManager.inputText();
    }

    private List<LabelDto> setLabels() {
        System.out.println("Please set labels.");
        return labelView.selectLabels();
    }
}
