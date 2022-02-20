package view;

import controller.LabelController;

import java.util.Scanner;

public class LabelView {
    private final Scanner scanner;
    LabelController labelController;

    public LabelView() {
        this.scanner = new Scanner(System.in);
        labelController = new LabelController();
    }

    public void createLabel() {
        String name;
        boolean nameIsOk;
        do {
            System.out.println("Enter name: ");
            name = scanner.nextLine();

            // Check if name is empty or contains spaces
            if(name.equals("")) {
                nameIsOk = false;
                System.out.println("Name can't be empty!");
            }
            else if(name.contains(" ")) {
                System.out.println("Name shouldn't contain spaces!");
                nameIsOk = false;
            }
            else {
                nameIsOk = true;
            }
        } while (!nameIsOk);

        labelController.saveLabel(name);
        System.out.println("Created label: " + name);
    }
}
