package view;

import controller.LabelController;
import controller.PostController;
import model.Label;
import model.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PostView {
    private final PostController postController;
    private final Scanner scanner;

    public PostView() {
        postController = new PostController();
        scanner = new Scanner(System.in);
    }

    public Post createPost() {
        System.out.println("Write post and press \"Enter\":");
        String content = scanner.nextLine();
        System.out.println("Please set labels.");
        List<Label> labels = setLabels();
        return postController.savePost(content, labels);
    }


    private List<Label> setLabels() {
        String stopWord = "0";
        System.out.println("You can choose among existing labels or create new");
        LabelView labelView = new LabelView();
        labelView.showAllLabelsSeparatedByComma();
        LabelController labelController= new LabelController();

        System.out.println("To set labels write label and press\"Enter\" (press '" + stopWord + "' to stop)");
        List<Label> labels = new ArrayList<>();
        String input = "";
        while(!input.equals(stopWord)) {
            input = labelView.getNameFromUser("0");
            if(!input.equals(stopWord)) {
                labelController.saveLabel(input);
            }
        }
        return labels;
    }
    public void deletePost(Integer id) {
        String content = postController.getPostById(id).getContent();
        postController.getPostById(id);
        System.out.println("This post has been deleted:");
        System.out.println(content);
        System.out.println("Write new text and press \"Enter\":");
        String newContent = scanner.nextLine();
        System.out.println("Current post's labels: ");
        postController.getLabelsAsStringSeparatedByComma(id);
        System.out.println("Set new labels");
        List<Label> newLabels = setLabels();
        postController.updatePost(id, content, newLabels);
        System.out.println("Post has been changed:");
        showPost(id);
    }

    public void changePost(Integer id) {
        String content = postController.getPostById(id).getContent();
        System.out.println("Current post content: ");
        System.out.println(content);
    }

    public void showPost(Integer id) {
        System.out.println(postController.getPostById(id));
    }

    public void showAllPosts() {

    }

    public void showWriterPost(List<Post> writerPosts) {
        System.out.println(postController.getWriterPost(writerPosts));
    }
}
