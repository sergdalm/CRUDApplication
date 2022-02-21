package view;

import java.util.Scanner;

public class TotalView {
    private final Scanner scanner;
    private final WriterView writerView;
    private final PostView postView;
    private final LabelView labelView;
    private final Integer writerId;
    private final Integer EXIT_NUMBER = 0;
    private final Integer MENU_OPTIONS = 3;

    public void start() {
        Integer input = EXIT_NUMBER - 1;
        do {
            showMenu();
            input = getNumberFromUser(0,MENU_OPTIONS);
            switch (input) {
                case(1) :
                    userPostsMenu();
                    break;
                case(2) :
                    writersMenu();
                    break;
                case(3) :
                    postView.showAllPosts();
                    break;
            }

        } while(!input.equals(EXIT_NUMBER));

    }

    public TotalView() {
        scanner = new Scanner(System.in);
        writerView = new WriterView();
        postView = new PostView();
        labelView = new LabelView();
        writerId = login();
    }

    private void userPostsMenu() {
        System.out.println("Enter number (0 for back)");
        System.out.println("1. See your posts");
        System.out.println("2. Write new post");
        System.out.println("3. Edit post");
        int input = getNumberFromUser(0, 3);
        switch (input) {
            case(1) :
                writerView.showWriterPosts(writerId);
                break;
            case(2) :
                writerView.addNewPost(writerId, postView.createPost());
                break;
            case(3) :
                //writerView.showWriterPosts(writerId);
                System.out.println("Here will be option to edit posts...");
        }
    }

    private void writersMenu() {
        writerView.showAllWriters();
        System.out.println("Enter writer's id to see writer's post  (0 for back):");
        writerView.showWriterToSeePosts();
    }

    private Integer login() {
        System.out.println("Enter first name:");
        String firstName = inputWithoutSpaces("First name");
        System.out.println("Enter last name:");
        String lastName = inputWithoutSpaces("Last name");
        return writerView.createWriterIfNotExisting(firstName, lastName);
    }

    private void showMenu() {
        System.out.println("Enter number from menu");
        System.out.print("Your name is: ");
        writerView.showWriterName(writerId);
        System.out.println("  1. Your posts");
        System.out.println("  2. Other writers");
        System.out.println("  3. All posts");
        System.out.println("For exit enter \"" + EXIT_NUMBER + "\"");
    }


    private String inputWithoutSpaces(String fieldName) {
        String input;
        boolean inputIsOk;
        do {
            input = scanner.nextLine();

            // Check if name is empty or contains spaces
            if(input.equals("")) {
                inputIsOk = false;
                System.out.println(fieldName + " can't be empty!");
            }
            else if(input.contains(" ")) {
                System.out.println(fieldName + " shouldn't contain spaces!");
                inputIsOk = false;
            }
            else {
                inputIsOk = true;
            }
        } while (!inputIsOk);
        return input;
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
}
