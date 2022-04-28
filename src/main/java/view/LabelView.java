package view;

import controller.console.LabelController;
import dto.LabelDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LabelView {
    private final LabelController labelController;
    private final InputManager inputManager = InputManager.getInstance();

    public LabelView() {
        labelController = new LabelController();
    }

    public LabelDto createLabel() {
        System.out.println("Enter name: ");
        String name = inputManager.inputWithoutSpaces();
        var labelDto = labelController.saveLabel(name);
        System.out.println("Created label: " + labelDto.getName());
        return labelDto;
    }

    public List<LabelDto> selectLabels() {
        var labels = labelController.getAll();
        showLabelsWithId(labels);
        System.out.println("Chose: (0 for no labels)");
        System.out.println("  1. Select from existing labels");
        System.out.println("  2. Create new label");
        int input = inputManager.getNumberFromUserBetweenMinAndMax(0, 2);
        boolean createNew = false;
        List<LabelDto> selectedLabels = new ArrayList<>();

        if (input == 1) {
            System.out.println("To set labels write label's id and press \"Enter\" (press '" + 0 + "' to stop)");
            for( ; ; ) {
                int result = inputManager.getNumberFromUserBetweenMinAndMax(0, labels.size() + 1);
                if(result == 0) {
                    break;
                }
                var label = labelController.getLabelById(labels.get(result - 1).getId());
                selectedLabels.add(label);
                System.out.println("Label " + label.getName() + " selected");
            }
            System.out.println("Dou you want to add new label? [y/n]");
            createNew = inputManager.getYesOrNo();
        }
        if(input == 2 || createNew) {
            do {
                var label = createLabel();
                selectedLabels.add(label);
                System.out.println("Label " + label.getName() + " selected");
                System.out.println("Dou you want to add new label? [y/n]");
                createNew = inputManager.getYesOrNo();
            } while(createNew);
        }
        return selectedLabels;
    }


    private void showLabelsWithId(List<LabelDto> labels) {
        int count = 1;
        for(LabelDto label : labels) {
            System.out.println(count++ + ". " + label.getName());
        }
    }

    public List<LabelDto> showAllLabelsWithId() {
        List<LabelDto> labels = labelController.getAll();

        for(LabelDto label : labels) {
            System.out.println(label.getId() + ". " + label.getName());
        }
        return labels;
    }

    public void showAllLabels() {
        var labels = labelController.getAll();
        showAllLabelsSeparatedByComma(labels);

    }

    public void showAllLabelsSeparatedByComma(List<LabelDto> labels) {
        String labelsSeparatedByComma = labels.stream()
                .map(LabelDto::getName)
                .collect(Collectors.joining(", "));
        System.out.println(labelsSeparatedByComma);
    }

    public void deleteLabel() {
        System.out.println("Enter label's number which you want to delete (0 for back)");
        var labels = showAllLabelsWithId();
        int input = inputManager.getNumberFromUserBetweenMinAndMax(0, labels.size());
        if(input == 0)
            return;
        String deletedLabel = labelController.getLabelName(input);
        labelController.deleteById(input);
        System.out.println("Label " + deletedLabel + " has been deleted.");
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
        String newName = inputManager.inputWithoutSpaces();
        labelController.update(id, newName);
        System.out.println("Label's name hase been changed: " + previousName + " -> " + newName);
    }

    public void showLabelsForPost(Integer postId) {
        List<LabelDto> labels = labelController.getLabelsForPost(postId);
        showAllLabelsSeparatedByComma(labels);
    }
}
