package view;

import controller.LabelController;
import model.Label;

import java.util.Objects;
import java.util.Scanner;

public class LabelView {
    private final Scanner scanner;
    private final LabelController labelController;
    private final int LABELS_COUNT;

    public LabelView() {
        this.scanner = new Scanner(System.in);
        labelController = new LabelController();
        LABELS_COUNT = labelController.getLabelCount();
    }

    public void createLabel() {
        System.out.println("Enter name: ");
        String name = getNameFromUser();

        labelController.saveLabel(name);
        System.out.println("Created label: " + name);
    }

    public Label createLabelIfNotExist() {
        System.out.println("Enter label: ");
        String name = getNameFromUser();
        if(isLabelExists(name))
            return labelController.getLabelByName(name);
        else {
            return labelController.saveLabel(name);
        }
    }

    public void showAllLabelWithId() {
        System.out.println(labelController.getAllLabelsIdAndName());
    }
    public void showAllLabelsSeparatedByComma() {
        System.out.println(labelController.getAllLabelsSeparatedByComma());
    }

    public void deleteLabel() {
        System.out.println("Enter label's number which you want to delete (0 for back)");
        showAllLabelWithId();
        int input = getNumberFromUser(0, LABELS_COUNT);
        if(input == 0)
            return;
        String deletedLabel = labelController.getLabelName(input);
        labelController.deleteById(input);
        System.out.println("Label " + deletedLabel + " has been deleted.");
    }

    public boolean isLabelExists(String name) {
        return labelController.isExisting(name);
    }

    public void deleteLabel(Integer id) {
        labelController.deleteById(id);
        String deletedLabelName = labelController.getLabelName(id);
        System.out.println("Label " + deletedLabelName + " has been deleted.");
    }

    public void changeLabel(Integer id) {
        String previousName = labelController.getLabelName(id);
        System.out.println("Changing label " + previousName);
        System.out.println("Enter new label's name: ");
        String newName = getNameFromUser();
        labelController.update(id, newName);
        System.out.println("Label's name hase been changed: " + previousName + " -> " + newName);
    }

    private String getNameFromUser() {
        String name;
        boolean nameIsOk;
        do {
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
        return name;
    }

    public String getNameFromUser(String stopWord) {
        String name;
        boolean nameIsOk;
        do {
            name = scanner.nextLine();

            if(Objects.equals(name, stopWord)) {
                return stopWord;
            }
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
        return name;
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
