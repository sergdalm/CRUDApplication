package view;

import controller.console.WriterController;
import dto.LoginWriterDto;
import dto.PostDto;
import dto.WriterDto;
import exceptions.LoginErrorException;


import java.util.List;

public class WriterView {
    private final WriterController writerController;
    private final InputManager inputManager;
    private final PostView postView;

    public WriterView() {
        writerController = new WriterController();
        postView = new PostView();
        inputManager = InputManager.getInstance();

    }

    public void showAllWriters() {
        System.out.println("Writers:");
        int input;
        do {
            List<WriterDto> writers = writerController.getAllWriters();
            int count = 1;
            for (WriterDto writer : writers) {
                System.out.println(count++ + ". " + writer.getFullName());
            }
            System.out.println("Enter writer's id to see writer's post (0 for back):");
            input = inputManager.getNumberFromUserBetweenMinAndMax(0, writers.size());
            if (input != 0) {
                Integer writerId = writers.get(input - 1).getId();
                postView.showPostsByWriterId(writerId);
            }
        } while(input != 0);
    }

    public LoginWriterDto createWriter() {
        System.out.println("Enter your first name: ");
        String firstName = inputManager.inputWithoutSpaces();

        System.out.println("Enter your last name: ");
        String lastName = inputManager.inputWithoutSpaces();

        System.out.println("Enter your email: ");
        String email = inputManager.inputWithoutSpaces();

        System.out.println("Enter your password: ");
        String password = inputManager.inputWithoutSpaces();

        writerController.saveWriter(firstName, lastName, email, password);


        System.out.println("Writer created: " + firstName + " " + lastName);

        return writerController.saveWriter(firstName, lastName, email, password);
    }


    public void changeWriter(Integer id) {
        String previousName = writerController.getWriterFullName(id);
        System.out.println("Enter new writer's first name: ");
        String firstName = inputManager.inputWithoutSpaces();
        System.out.println("Enter new writer's last name: ");
        String lastName = inputManager.inputWithoutSpaces();

        writerController.update(id, firstName, lastName);
        String newName = firstName + " " + lastName;
        System.out.print("Writer's name has been changed: ");
        System.out.println(previousName + " -> " + newName);
    }


    public void deleteWriter(Integer id) {
        String deletedWriterName = writerController.getWriterFullName(id);
        writerController.deleteWriterById(id);
        System.out.println("Writer " + deletedWriterName + " has been deleted.");
    }


    public LoginWriterDto login() {
        for (; ; ) {
            System.out.println("Enter your email:");
            String email = inputManager.inputWithoutSpaces().toLowerCase();
            System.out.println("Enter your password:");
            String password = inputManager.inputWithoutSpaces();
            try {
                return writerController.loginWriter(email, password);
            } catch (LoginErrorException e) {
                System.out.println(e.getLocalizedMessage());
                System.out.println("Do you want to create new account?");
                System.out.println("Type \"create\" to create a new account.");
                String maybeCreate = inputManager.inputWithoutSpaces();
                if (maybeCreate.equalsIgnoreCase("create")) {
                    return createWriter();
                }
            }
        }

    }


    public void showWriterName(Integer id){
        System.out.println(writerController.getWriterById(id).getFullName());
    }

    public void addWriterPost(Integer writerId, PostDto post) {

    }
}

