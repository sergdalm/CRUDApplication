package view;

import controller.LabelController;

import java.util.Scanner;

public class LabelView {
    private final Scanner scanner;
    LabelController labelController;
    private final int ACTION_ON_LABEL = 1;
    private final int LABELS_COUNT;

    public LabelView() {
        this.scanner = new Scanner(System.in);
        labelController = new LabelController();
        LABELS_COUNT = labelController.getLabelCount();
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

    public void showAllLabel() {
        System.out.println(labelController.getAllLabelsIdAndName());

    }

    public void deleteLabel() {
        System.out.println("Enter label's number which you want to delete (0 for back)");
        showAllLabel();
        int input = getNumberFromUser(0, LABELS_COUNT);
        if(input == 0)
            return;
        String deletedLabel = labelController.getLabelName(input);
        labelController.deleteBuId(input);
        System.out.println("Label " + deletedLabel + " has been deleted.");
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

    public void changeLabel() {

    }

    public void menu() {
    }



}
