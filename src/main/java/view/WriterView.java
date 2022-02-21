package view;

import controller.WriterController;
import model.Post;


import java.util.List;
import java.util.Scanner;

public class WriterView {
    private final WriterController writerController;
    private final Scanner scanner;

    public WriterView() {
        writerController = new WriterController();
        scanner = new Scanner(System.in);
    }

    public void showAllWriters() {
        String allWriterIdAndNames = writerController.getAllWriterIdAndNames();
        System.out.println(allWriterIdAndNames);
    }

    public void createWriter() {
        System.out.println("Enter writer's first name: ");
        String firstName = inputFirstName();

        System.out.println("Enter writer's last name: ");
        String lastName = inputLastName();

        writerController.saveWriter(firstName, lastName);

        System.out.println("Writer created: " + firstName + " " + lastName);
    }

    public void changeWriter() {
        Integer writersCount = writerController.writersCount();

        System.out.println("Enter writer's number who you want to change: (0 for back)");
        showAllWriters();

        int input = getNumberFromUser(0, writersCount);

        if(input == 0)
            return;

        String previousName = writerController.getWriterFullName(input);

        System.out.println("You chose writer " + previousName);
        changeWriter(input);
    }

    public void changeWriter(Integer id) {
        String previousName = writerController.getWriterFullName(id);
        System.out.println("Enter new writer's first name: ");
        String firstName = inputFirstName();
        System.out.println("Enter new writer's last name: ");
        String lastName = inputLastName();

        writerController.update(id, firstName, lastName);
        String newName = firstName + " " + lastName;
        System.out.print("Writer's name has been changed: ");
        System.out.println(previousName + " -> " + newName);
    }

    private String inputFirstName() {
        String firstName;
        do {
            firstName = scanner.nextLine();
        } while(firstName.equals(""));
        return firstName;
    }

    private String inputLastName() {
        String lastName;
        do {
            lastName = scanner.nextLine();
        } while(lastName.equals(""));
        return lastName;
    }

    public void deleteWriter() {
        System.out.println("Enter writer's number who you want to delete (0 for back)");
        showAllWriters();
        Integer writersCount = writerController.writersCount();
        int input = getNumberFromUser(0, writersCount);
        if(input == 0)
            return;
        String deletedWriterName = writerController.getWriterFullName(input);
        deleteWriter(input);
    }

    public void deleteWriter(Integer id) {
        String deletedWriterName = writerController.getWriterFullName(id);
        writerController.deleteWriterById(id);
        System.out.println("Writer " + deletedWriterName + " has been deleted.");
    }

    public void showWriterToSeePosts() {
        showAllWriters();

        System.out.println("Enter writer's id to see writer's post (0 for back):");
        int writersCount = writerController.writersCount();
        int input = getNumberFromUser(0, writersCount);
        if(input == 0)
            return;
        writerController.getWriterById(input);
        writerController.getAllPostsByWriterId(input);
    }

    public void changeWriterPost(Integer writerId) {
        showWriterPosts(writerId);
        System.out.println("Enter post's to edit: ");
    }

    private int getNumberFromUser(int min, int max) {
        int result = min - 1;
        do {
            try{
                result = scanner.nextInt();
            } catch (Exception exc) {
                scanner.next();
            }
        } while(result < min || result > max);
        return result;
    }

    public void showWriterPosts(Integer id) {
        List<Post> posts = writerController.getWriterById(id).getPosts();
        if(posts.size() == 0)
            System.out.println("There is no posts yet.");
        else {
            for(Post post : posts)
                System.out.println(post);
        }
    }

    public void addNewPost(Integer id, Post post) {
        writerController.addNewPost(id, post);
    }

    public Integer createWriterIfNotExisting(String firstName, String lastName) {
        return writerController.saveIfNotExisting(firstName, lastName).getId();
    }

    public void showWriterName(Integer id) {
        System.out.println(writerController.getWriterById(id).getFirstName()
                + writerController.getWriterById(id).getLastName());
    }
}
