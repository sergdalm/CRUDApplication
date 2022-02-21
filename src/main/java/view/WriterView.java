package view;

import controller.WriterController;
import java.util.Scanner;

public class WriterView {
    private final WriterController writerController;
    private final Scanner scanner;
    private final int ACTIONS_ON_WRITER = 4;

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

        System.out.println("Enter new writer's name: ");
        String firstName = inputFirstName();
        System.out.println("Enter new writer's last name: ");
        String lastName = inputLastName();

        writerController.update(input, firstName, lastName);
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
        writerController.deleteWriterById(input);
        System.out.println("Writer " + deletedWriterName + " has been deleted.");
    }

    public void menu() {
        int input = -1;
        while(input != 0){
            System.out.println("Chose action (enter number, 0 for back): ");
            System.out.println("   1. Create new writer");
            System.out.println("   2. Change existing writer");
            System.out.println("   3. Delete writer");
            System.out.println("   4. Show all writers");
            input = getNumberFromUser(0, ACTIONS_ON_WRITER);
            int writersCount = writerController.writersCount();
            switch (input) {
                case 0:
                    return;
                case 1:
                    createWriter();
                    break;
                case 2:
                    if(writersCount == 0)
                        System.out.println("There is no writers to change.");
                    else
                        changeWriter();
                    break;
                case 3:
                    if(writersCount == 0)
                    System.out.println("There is no writers to delete.");
                    else
                        deleteWriter();
                    break;
                case 4:
                    if(writersCount == 0)
                        System.out.println("There is no exiting writers.");
                    else
                        showAllWriters();
                    break;
            }
        }
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
